package com.supertg.supertg.api;


import com.supertg.supertg.constant.Constants;
import com.supertg.supertg.data.table.Meizhi;
import com.supertg.supertg.rx.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xiongxing on 2017/1/18.
 */

public interface MeiZiService {
    //域名
    String BASE_URL = Constants.拿软妹子;
    //接口名
    @GET("data/福利/10"+"/{page}")
    Observable<HttpResult<List<Meizhi>>> getMeiZiList(@Path("page")int page);
}
