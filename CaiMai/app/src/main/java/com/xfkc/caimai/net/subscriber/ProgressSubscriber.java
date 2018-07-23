package com.xfkc.caimai.net.subscriber;

import android.content.Context;
import android.view.View;

import com.hyf.tdlibrary.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.xfkc.caimai.customview.MyProgressDialog;
import com.xfkc.caimai.dialog.CommonDialog;
import com.xfkc.caimai.net.ApiException;
import com.xfkc.caimai.util.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by LK on 2017/4/18 11:21.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> {
    private WeakReference<Context> mActivity;
    //  private XProgressDialog pd;
    private MyProgressDialog pd;

    public ProgressSubscriber(Context context) {
        this.mActivity = new WeakReference<>(context);
        initDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Utils.debug(e.getMessage());
        if (e instanceof HttpException || e instanceof IOException) {
            showTipsDialog(mActivity.get(), "请检查网络是否连接");
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            showTipsDialog(mActivity.get(), e.getMessage());
        } else {
            Logger.e("---------error------", e.getMessage());
            ToastUtil.showToast("出错啦！！！");
        }

    }

    private void initDialog() {
        Context context = mActivity.get();
        if (null == pd && null != context) {
            pd = new MyProgressDialog(context, "正在加载..", MyProgressDialog.THEME_CIRCLE_PROGRESS);
            pd.setCancelable(false);
        }
    }

    private void showProgressDialog() {
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    public void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    private void showTipsDialog(Context context, String msg) {
        new CommonDialog(context).builder().setTitle("提示")
                .setContentMsg(msg)
                .setCanceledOnTouchOutside(false)
                .setPositiveBtn("确定", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {

                    }
                }).show();
    }

}
