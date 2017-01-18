package com.supertg.supertg.ui.main.biz;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.app.app;
import com.supertg.supertg.ui.main.adapter.MeizhiListAdapter;
import com.supertg.supertg.ui.main.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.util.ActivitySwitcher;
import com.supertg.supertg.util.getPicAlbum.ImageFetcher;
import com.supertg.supertg.util.getPicAlbum.ImageItem;

import java.util.List;

/**
 * Model类，业务逻辑和实体模型
 * Created by xiongxing on 2017/1/16.
 */

public class ImagesViewBiz implements IImagesViewBiz {

    @Override
    public void setRecycleViewData(final Activity context, List<ImageItem> imagesList, RecyclerView mRecyclerView) {
        //瀑布流布局
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        MeizhiListAdapter meizhiListAdapter = new MeizhiListAdapter(context, imagesList);
        mRecyclerView.setAdapter(meizhiListAdapter);
        meizhiListAdapter.setOnMeizhiTouchListener(new OnMeizhiTouchListener() {
            @Override
            public void onTouch(View meizhiView, ImageItem meizhi, List<ImageItem> mList) {
                ActivitySwitcher.startPictureActivity(context,meizhi,meizhiView);
            }
        });
        Logger.e("setRecycleViewData");
    }

    @Override
    public void getMainPicData(onGetPicListener onGetPicListener) {
        //获取本地相册图片的地址集合
        List<ImageItem> mDataList = ImageFetcher.getInstance(app.getApplication().getApplicationContext()).getDateImageList(false);
        Logger.e("mDataList.size=="+mDataList.size());
        onGetPicListener.onGetPicSuccess(mDataList);
    }
}
