package com.hyf.tdlibrary.global;

import android.content.Context;

public final class TDConfig {
    /**
     * 单例
     */
    private volatile static TDConfig instance;
    public static TDConfig getInstance() {
        if (instance == null) {
            synchronized (TDConfig.class) {
                if (instance == null) {
                    instance = new TDConfig();
                }
            }
        }
        return instance;
    }


    /**
     * *********************************************************************************************
     * Global ApplicationContext
     * *********************************************************************************************
     */
    static Context sAppContext;
    public TDConfig context(Context context) {
        sAppContext = context;
        return this;
    }

    /**
     * *********************************************************************************************
     * AppUtil
     * *********************************************************************************************
     */
    public static final String KEY_DOWNLOAD_URL = "download_url";
    static String sUpdateJsonUrl;
    public TDConfig app(String updateJsonUrl) {
        sUpdateJsonUrl = updateJsonUrl;
        return this;
    }

    /**
     * *********************************************************************************************
     * UpdateUtil
     * *********************************************************************************************
     */
    public static int sNotificationFrequent = 5;
    public static String sDownloadSDPath;
    public static int sUpdateIcon;
    @Deprecated
    public TDConfig update(String downloadSDPath, int notificationFrequent) {
        sNotificationFrequent = notificationFrequent;
        sDownloadSDPath = downloadSDPath;
        return this;
    }

    public TDConfig update(String downloadSDPath, int notificationFrequent, int updateIcon) {
        sNotificationFrequent = notificationFrequent;
        sDownloadSDPath = downloadSDPath;
        sUpdateIcon = updateIcon;
        return this;
    }

    /**
     * *********************************************************************************************
     * HttpLess
     * *********************************************************************************************
     */
    public static int sConnectTimeOut = 5000;
    public static int sReadTimeout = 5000;
    public TDConfig http(int connectTimeOut, int readTimeout) {
        sConnectTimeOut = connectTimeOut;
        sReadTimeout = readTimeout;
        return this;
    }

}
