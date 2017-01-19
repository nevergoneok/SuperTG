package com.supertg.supertg.api;


import com.supertg.supertg.constant.Constants;
import com.supertg.supertg.data.bean.ImagesBean;
import com.supertg.supertg.rx.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by xiongxing on 2017/1/18.
 */

public interface ImageService {
    //域名
    String BASE_URL = Constants.GETIMAGESLIST;
    //接口名
    @GET("upload/getimage1")
    Observable<HttpResult<List<ImagesBean.ErrDesc>>> getImageList();
}
