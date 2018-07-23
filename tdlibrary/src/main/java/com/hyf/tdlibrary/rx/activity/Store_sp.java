package com.hyf.tdlibrary.rx.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/9/2.
 */

public class Store_sp  {

    private static final String SHARE_PREFS_NAME = "Li";

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }
}
