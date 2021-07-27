package com.dandelion.login.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.dandelion.login.UserInfoManager;
import com.dandelion.login.ui.LoginActivity;
import com.dandelion.utils.BitUtils;

/**
 * 登录拦截器
 * Created by lin.wang on 2021.07.24
 */
@Interceptor(priority = 2, name = "loginInterceptor")
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        /**
         * extras值在@Route注解中赋值，然后再这里可以取到该值，确定要做何种拦截操作。
         * extras默认值为int的最小值，最小为负数用反码表示的，那么低8位都是0。
         * extras为int型，4个字节，那么可以利用这4个字节的每一位做为一个标志位，总共就有32个标志位，完全够用。
         * 然后利用位与运算提取对应位置的标志位，使用位运算工具类获取即可。
         * 通过extras字段就可以实现本页面的配置在本页面配置，不希望页面的配置逃出页面，配置到像Manifest的其他地方。
         * 也就是高内聚低耦合的思想。
         *
         * 规定：
         * 第一个bit位0x00000001: 是否需要登录标志；0不需要，1需要
         * 第二个bit位0x00000002：
         * 第三个bit位0x00000004：
         * 第四个bit位0x00000008：
         * 第五个bit位0x00000010：
         * .....
         */
        int extras = postcard.getExtra();
        boolean needLogin = BitUtils.getBitValue((byte) extras, 0) == 1; //这里只取低8位的值
        if (needLogin) {
            if (!UserInfoManager.isLogin()) {
                callback.onInterrupt(null);
                LoginActivity.start(postcard.getPath(), postcard.getExtras());
            } else {
                callback.onContinue(postcard);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}

