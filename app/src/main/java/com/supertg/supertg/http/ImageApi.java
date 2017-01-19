package com.supertg.supertg.http;


import com.supertg.supertg.data.bean.ImagesBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by xiongxing on 2017/1/18.
 */

public interface ImageApi {
    @GET("getImageList")
    Observable<ImagesBean> getImageList();
}
