package com.supertg.supertg.ui.main.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.supertg.supertg.data.bean.ImagesBean;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;

/**
 * Model类，业务逻辑和实体模型
 * Created by xiongxing on 2017/1/18.
 */
public interface IImagesViewModel {

    public void setRecycleViewData(Activity context, ImagesBean imagesBean, RecyclerView mRecyclerView);
    public void getMainPicData(onGetPicListener onGetPicListener);
}
