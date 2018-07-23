package com.xfkc.caimai.net.intercepter;

import com.hyf.tdlibrary.utils.EncodeUtils;
import com.orhanobut.logger.Logger;
import com.xfkc.caimai.util.Utils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@dongjinxu
 * 3.@2018/4/18.
 */

public class MyRequestIntercepter implements Interceptor {

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
        return response;
    }

    private Request addPostParamsSign(Request request) {

        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();
            //原有的参数
            HashMap<String, String> params = new HashMap<>();
            for (int i = 0; i < formBody.size(); i++) {
                Logger.e(formBody.encodedName(i), EncodeUtils.urlDecode(formBody.encodedValue(i)));
                params.put(formBody.encodedName(i), EncodeUtils.urlDecode(formBody.encodedValue(i)));
            }

        }
        return request;
    }
}
