package com.xfkc.caimai.net.intercepter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hyf.tdlibrary.utils.EncodeUtils;
import com.hyf.tdlibrary.utils.SharedPrefUtil;
import com.xfkc.caimai.application.MyApplication;
import com.xfkc.caimai.config.Constant;
import com.xfkc.caimai.config.SharedPref;
import com.xfkc.caimai.util.DeviceUtils;
import com.xfkc.caimai.util.JUtil;
import com.xfkc.caimai.util.MD5Util;
import com.xfkc.caimai.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LK on 2017/7/14 14:08.
 */

public class RequestIntercepter implements Interceptor{
  public static final String MULTIPART_FORM_DATA = "multipart/form-data";
  private HttpUrl httpUrl;

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    if (request.method().equals("POST")) {
      this.httpUrl = request.url();
      Utils.debug(httpUrl.toString());
      request = addPostParamsSign(request);
    }
    Response response = chain.proceed(request);
//    if(response.code() == 200) {
//      try {
//        String result = new JSONObject(response.body().string()).getString("REP_BODY");
//        Log.e("REP_BODY-->", result);
//        MediaType contentType = response.body().contentType();
//        ResponseBody body = ResponseBody.create(contentType, result);
//        return response.newBuilder().body(body).build();
//      } catch (JSONException e) {
//        e.printStackTrace();
//      }
//    }
    return response;
  }

  //post 添加签名和公共动态参数
  private Request addPostParamsSign(Request request) throws UnsupportedEncodingException {
    if (request.body() instanceof FormBody) {
      FormBody.Builder bodyBuilder = new FormBody.Builder();

      FormBody formBody = (FormBody) request.body();

      //原有的参数
      HashMap<String, String> params = new HashMap<>();
      for (int i = 0; i < formBody.size(); i++) {
        //params.put(formBody.encodedName(i), formBody.encodedValue(i));
        params.put(formBody.encodedName(i), EncodeUtils.urlDecode(formBody.encodedValue(i)));
      }
      if (params.containsKey("custPwd")) {
        params.put("custPwd", MD5Util.generatePassword(params.get("custPwd")));
      }

      if (params.containsKey("newPwd")) {
        params.put("newPwd", MD5Util.generatePassword(params.get("newPwd")));
      }

      String token = SharedPrefUtil.get(MyApplication.getInstance(), SharedPref.TOKEN, "");
      if(!"".equals(token)) {
        params.put("token", token);
      }
      params.put("sysType", Constant.SYS_TYPE);
      params.put("sysVersion", Constant.SYS_VERSIN);
      params.put("agentId", "2015000000");
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

      Gson gson = new GsonBuilder().serializeNulls().create();
      Log.e("REQ_MESSAGE-->", gson.toJson(data, new TypeToken<HashMap<String,HashMap<String, String>>>(){}.getType()));

      bodyBuilder.addEncoded("REQ_MESSAGE", gson.toJson(data));
      // String url = HttpUtil.createUrlFromParams(httpUrl.url().toString(), data);
      formBody = bodyBuilder.build();
      request = request.newBuilder().post(formBody).build();
    }
//    else if (request.body() instanceof MultipartBody) {
//      MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
//      MultipartBody multipartBody = (MultipartBody) request.body();
//
//      List<MultipartBody.Part> oldparts = multipartBody.parts();
//
//      for (MultipartBody.Part part : oldparts) {
//        bodyBuilder.addPart(part);
//      }
//      multipartBody = bodyBuilder.build();
//      request = request.newBuilder().post(multipartBody).build();
//    }
    return request;
  }

  public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
//压缩
    File file = new File(fileUri);
    if (file != null) {
      // 为file建立RequestBody实例
      RequestBody requestFile =
              RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
      // MultipartBody.Part借助文件名完成最终的上传
      return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
    return null;
  }


}
