package com.supertg.supertg.ui.main.biz;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.util.getPicAlbum.ImageItem;

import java.util.List;

/**
 * Model类，业务逻辑和实体模型
 * Created by xiongxing on 2017/1/16.
 */

public interface IImagesViewBiz {
    public void setRecycleViewData(Activity context, List<ImageItem>imagesList, RecyclerView mRecyclerView);
    public void getMainPicData(onGetPicListener onGetPicListener);
}
