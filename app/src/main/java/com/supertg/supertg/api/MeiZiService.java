package com.supertg.supertg.api;


import com.supertg.supertg.bean.Meizhi;
import com.supertg.supertg.mvpframe.rx.httpresult.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xiongxing on 2017/1/18.
 */

public interface MeiZiService {
    //接口名
    @GET("data/福利/10"+"/{page}")
    Observable<HttpResult<List<Meizhi>>> getMeiZiList(@Path("page")int page);
}
