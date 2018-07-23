package com.xfkc.caimai.net.upload;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xfkc.caimai.application.MyApplication;
import com.xfkc.caimai.config.Constant;
import com.xfkc.caimai.util.DeviceUtils;
import com.xfkc.caimai.util.JUtil;
import com.xfkc.caimai.util.MD5Util;
import com.xfkc.caimai.util.Utils;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by BlingBling on 2017/3/7.
 */

public class MultipartUtil {

    private static final String MEDIA_TYPE_TEXT = "text/plain; charset=UTF-8";

    public static MediaType getFileMediaType(String fileName) {
        final FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return MediaType.parse(contentTypeFor);
    }

    public static MultipartBody.Part getMultipart(String key, File file) {
        final MediaType mediaType = MultipartUtil.getFileMediaType(file.getAbsolutePath());
        final RequestBody body = RequestBody.create(mediaType, file);
        return MultipartBody.Part.createFormData(key, file.getName(), body);
    }


    public static Map<String, RequestBody> getRequestBodyMap(Map<String, String> params, Map<String, File> files) {

        params.put("sysType", Constant.SYS_TYPE);
        params.put("sysVersion", Constant.SYS_VERSIN);
        params.put("appVersion", Utils.getVersion(MyApplication.getInstance()));
        params.put("sysTerNo", DeviceUtils.getDeviceId(MyApplication.getInstance()));
        params.put("txnDate", Utils.getCurrentDate("yyMMdd"));
        params.put("txnTime", Utils.getCurrentDate("HHmmss"));

        HashMap<String, Object> data = new HashMap<String, Object>();
        HashMap<String, Object> signMap = new HashMap<String, Object>();
        String sign = MD5Util.generateParams(JUtil.toJsonString(params));
        signMap.put("SIGN", sign);
        data.put("REQ_BODY", params);
        data.put("REQ_HEAD", signMap);

        final Map<String, RequestBody> body = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        body.put("REQ_MESSAGE", RequestBody.create(MediaType.parse(MEDIA_TYPE_TEXT), gson.toJson(data)));

        if (files != null) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                final File file = entry.getValue();
                final MediaType mediaType = MultipartUtil.getFileMediaType(file.getAbsolutePath());
                final RequestBody fileBody = RequestBody.create(mediaType, file);
                //body.put(entry.getKey() + "\"; filename=\"" + file.getName(), fileBody);
                String key = entry.getKey();
                Log.e("key---------", key);
                body.put(key + "\"; filename=\"" + file.getName(), fileBody);
            }
        }
        return body;
    }
}
