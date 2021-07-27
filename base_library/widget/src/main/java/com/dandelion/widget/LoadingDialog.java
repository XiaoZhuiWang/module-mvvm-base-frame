package com.dandelion.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 加载进度
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;

        private CharSequence mMessage;
        private boolean isCancelable;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public LoadingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.widget_dialog_loading, null);
         /*   TextView mMessageView = layout.findViewById(R.id.message);
            if (!TextUtils.isEmpty(mMessage)) {
                mMessageView.setText(mMessage);
            }else {
                mMessageView.setVisibility(View.GONE);
            }*/

            final LoadingDialog dialog = new LoadingDialog(context, R.style.WidgetLoadProgressDialog);

            Window window = dialog.getWindow();
            dialog.requestWindowFeature(Window. FEATURE_NO_TITLE);  //去掉dialog的title
            window.addFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN);  //全屏的flag
            window.setBackgroundDrawableResource(android.R.color.transparent); //设置window背景透明

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.alpha = 1.0f;
            window.setAttributes(params);

            dialog.setContentView(layout);
            dialog.setCancelable(isCancelable);

            return dialog;
        }
    }
}
