package com.xfkc.caimai.net.covert;

import com.google.gson.TypeAdapter;
import com.xfkc.caimai.net.ApiException;
import com.xfkc.caimai.net.Response;
import com.xfkc.caimai.util.Utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 当前类注释：自定义ResponseBodyConverter
 * Author :LeonWang
 * Created  2016/10/11.11:33
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */

final class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private String mResult;

    CustomResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        try {
        //Utils.debug("value.string--->"+value.string());
        //解密
//            String response = new JSONObject(value.string()).getString("REP_BODY");
        String response = value.string();
        Utils.debug("接口返回结果---->" + response);
        Response rs = (Response) adapter.fromJson(response);

        if (!rs.getRspCod().equals("000000")) {
//            if (rs.getRspCod().equals("888887")) {
//                throw new ApiException(rs.getRspCod(), "0");
//            }
// else if(rs.getRspCod().equals("000107")){
//                    throw new ApiException(rs.getRspCod(), response);
//                }else if(rs.getRspCod().equals("000108")){
//                    throw new ApiException(rs.getRspCod(), response);
//                }else if (!rs.getRspCod().equals("000606")) {
//                    throw new ApiException(rs.getRspCod(), rs.getRspMsg());
//                }else{
            throw new ApiException(rs.getRspCod(), rs.getRspMsg());
//                }
        }
        try {
            mResult = decodeResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(rs.getRspCod(), "解密错误");
        }
        return adapter.fromJson(mResult);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//            throw new ApiException("01000000", "数据解析错误");
//        }
//        finally {
//            value.close();
//        }
    }

    public String decodeResponse(String data) {

        return data;
    }

}
