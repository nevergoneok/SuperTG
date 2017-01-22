package com.supertg.supertg.mvpframe.rx.apiservice;


import com.supertg.supertg.bean.Meizhi;
import com.supertg.supertg.mvpframe.rx.httpresult.HttpResult;

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
