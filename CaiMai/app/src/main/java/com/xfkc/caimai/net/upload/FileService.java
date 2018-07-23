package com.xfkc.caimai.net.upload;


import com.xfkc.caimai.bean.EmptyBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by LK on 2017/7/18 15:28.
 */

public interface FileService {

    // 添加用户头像  加上一张图片的上传

    @Multipart
    @POST("SY0024.json")
    Observable<EmptyBean> saveModia(@PartMap Map<String, RequestBody> body);



}
