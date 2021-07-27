package com.dandelion.network.interceptor;


import android.util.Log;

import com.dandelion.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 基于com.squareup.okhttp3:logging-interceptor:3.8.1修改
 * 什么都没做，只是把Logger替换成KLog，读者可自行替换日志框架
 */
public final class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private volatile Level level = Level.NONE;

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;


        /*********************请求打印***********************/
        RequestBody requestBody = request.body();
        String requestBodyStr = "";
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
//        LogUtil.d(LogUtil.HTTP_REQUEST_TAG, requestStartMessage);

        if (logHeaders) {
            //屏蔽掉请求头的打印，防止影响观看日志
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
//                LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
//                LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    //对数据进行解码
                    try {
                        requestBodyStr = buffer.readString(charset);
                        requestBodyStr = URLDecoder.decode(requestBodyStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, requestBodyStr);

//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "--> END " + request.method()
//                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
//                    LogUtil.d(LogUtil.HTTP_REQUEST_TAG, "--> END " + request.method() + " (binary "
//                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }


        /*********************回复打印***********************/
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.d(LogUtil.HTTP_EXCEPTION_TAG, "<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
//        LogUtil.d(LogUtil.HTTP_RESPOND_TAG, "<-- " + response.code() + ' ' + response.message() + ' '
//                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
//                + bodySize + " body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
//                LogUtil.d(LogUtil.HTTP_RESPOND_TAG, headers.name(i) + ": " + headers.value(i));
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
//                LogUtil.d(LogUtil.HTTP_RESPOND_TAG, "<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
//                LogUtil.d(LogUtil.HTTP_RESPOND_TAG, "<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
//                    LogUtil.d(LogUtil.HTTP_RESPOND_TAG, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    String responseMsg = buffer.clone().readString(charset);

                    //如果返回信息是json格式，则以json格式打印。参考的Logger库打印json
                    try {
                        requestBodyStr = requestBodyStr.replace("&", "\n");
                        responseMsg = responseMsg.trim();
                        if (responseMsg.startsWith("{")) {
                            JSONObject jsonObject = new JSONObject(responseMsg);
                            String message = jsonObject.toString(2);
                            LogUtil.d(LogUtil.HTTP_RESPOND_TAG,
                                    "——————请求行——————\n" + requestStartMessage
                                            + "\n\n——————请求内容——————\n" + requestBodyStr
                                            + "\n\n——————回复内容——————\n" + message);
                        } else if (responseMsg.startsWith("[")) {
                            JSONArray jsonArray = new JSONArray(responseMsg);
                            String message = jsonArray.toString(2);
                            LogUtil.d(LogUtil.HTTP_RESPOND_TAG,
                                    "——————请求行——————\n\n" + requestStartMessage
                                            + "\n\n——————请求内容——————\n" + requestBodyStr
                                            + "\n\n——————回复内容——————\n" + message);
                        } else {
                            LogUtil.d(LogUtil.HTTP_RESPOND_TAG,
                                    "——————请求行——————\n" + requestStartMessage
                                            + "\n\n——————请求内容——————\n" + requestBodyStr
                                            + "\n\n——————回复内容——————\n" + responseMsg);
                        }
                    } catch (JSONException e) {
                        LogUtil.d(LogUtil.HTTP_RESPOND_TAG,
                                "——————请求行——————\n" + requestStartMessage
                                        + "\n\n——————请求内容——————\n" + requestBodyStr
                                        + "\n\n——————回复内容——————\n" + responseMsg);
                    }


                }

//                LogUtil.d(LogUtil.HTTP_RESPOND_TAG, "<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }

    public static void printJson(String tag, String msg, String headString) {
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        printLine(tag, false);
    }

}
