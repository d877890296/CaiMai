package com.xfkc.caimai.net;


import com.xfkc.caimai.bean.EmptyBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LK on 2017/5/5 10:41.
 */

public interface PayService {

    //首页轮播图
    @POST("SY0001.json")
    Observable<EmptyBean> getHomeImg(@Body EmptyBean id);

}
