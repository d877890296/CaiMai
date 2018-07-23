package com.hyf.tdlibrary;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hyf.tdlibrary.crash.CustomActivityOnCrash;


/**
 * Created by LK on 2016/3/8 16:49
 */
public class RootApplication extends Application {

    private static final String TAG = "RootApplication";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        CustomActivityOnCrash.install(this);//正式投产时放开
        //CustomActivityOnCrash.setEventListener(new CustomEventListener());

    }

    public static Context getContext() {
        return context;
    }

    static class CustomEventListener implements CustomActivityOnCrash.EventListener {
        @Override
        public void onLaunchErrorActivity() {
            Log.i(TAG, "onLaunchErrorActivity()");
        }

        @Override
        public void onRestartAppFromErrorActivity() {
            Log.i(TAG, "onRestartAppFromErrorActivity()");
        }

        @Override
        public void onCloseAppFromErrorActivity() {
            Log.i(TAG, "onCloseAppFromErrorActivity()");
        }
    }
}
