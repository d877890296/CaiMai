package com.xfkc.caimai.customview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.xprogressdialog.HeartProgressView;
import com.xfkc.caimai.R;

/**
 * Created by chimchim on 2017/9/20.
 */

public class MyProgressDialog extends AlertDialog{
    // theme类型
    public static final int THEME_HORIZONTAL_SPOT = 1;
    public static final int THEME_CIRCLE_PROGRESS = 2;
    public static final int THEME_HEART_PROGRESS = 3;

    protected View progressBar;
    protected TextView message;
    protected String messageText = "正在加载中...";
    protected int theme = THEME_HORIZONTAL_SPOT;

    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, String message) {
        super(context);
        this.messageText = message;
    }

    public MyProgressDialog(Context context, int theme) {
        super(context);
        this.theme = theme;
    }

    public MyProgressDialog(Context context, String message, int theme) {
        super(context);
        this.messageText = message;
        this.theme = theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (theme) {
            case THEME_CIRCLE_PROGRESS:
                setContentView(R.layout.myprogressdialog);
                break;
            case THEME_HEART_PROGRESS:
                setContentView(com.apkfuns.xprogressdialog.R.layout.view_xprogressdialog_heart_progress);
                break;
            default:
                setContentView(com.apkfuns.xprogressdialog.R.layout.view_xprogressdialog_spot);
                break;
        }
        message = (TextView) findViewById(R.id.view_more_tv);
        progressBar = findViewById(R.id.view_more_progressbar);
        if (message != null && !TextUtils.isEmpty(messageText)) {
            message.setText(messageText);
        }
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void setMessage(String message) {
        this.messageText = message;
    }

    /**
     * 获取显示文本控件
     *
     * @return
     */
    protected TextView getMessageView() {
        return message;
    }

    /**
     * 获取显示进度的控件
     *
     * @return
     */
    protected View getProgressView() {
        return progressBar;
    }

    @Override
    public void show() {
        super.show();
        if (progressBar instanceof HeartProgressView) {
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    ((HeartProgressView) progressBar).start();
                }
            });
        }
    }
}
