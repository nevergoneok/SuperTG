package com.supertg.supertg.rx.apiservice;


import com.supertg.supertg.enity.Meizhi;
import com.supertg.supertg.rx.httpresult.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by helin on 2016/10/9 17:09.
 */

public interface ApiService {
    //接口名
    @GET("data/福利/10"+"/{page}")
    Observable<HttpResult<List<Meizhi>>> getMeiZiList(@Path("page")int page);
}
