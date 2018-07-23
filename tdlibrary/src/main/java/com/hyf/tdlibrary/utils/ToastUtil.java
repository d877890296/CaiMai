package com.hyf.tdlibrary.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hyf.tdlibrary.RootApplication;

/**
 * Created by LK on 2016/3/4 17:45
 */
public class ToastUtil {
    private static Toast toast;

    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(final String text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    private static void show(String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(RootApplication.getContext(), text, duration);
        toast.show();
    }
}
