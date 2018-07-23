package com.xfkc.caimai.net.covert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * 当前类注释：自定义RequestBodyConverter
 * Author :LeonWang
 * Created  2016/10/11.11:30
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */

public  class CustomRequestBodyConverter <T> implements Converter<T, RequestBody> {
    private static final String TAG = "CustomRequestBodyConver";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
//        Map<String, String > params = (Map<String, String>) value;
//        FormBody.Builder builder = new FormBody.Builder();
//        Iterator iterator = params.keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            String valueStr = params.get(key);
//            builder.add(key, valueStr);
//        }
//        return builder.build();
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

}