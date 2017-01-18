package com.supertg.supertg.ui.main.presenter;

import android.support.v7.widget.RecyclerView;

import com.supertg.supertg.ui.main.biz.ImagesViewBiz;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.ui.main.view.IMainGetPicView;
import com.supertg.supertg.ui.main.view.MainActivity;
import com.supertg.supertg.util.getPicAlbum.ImageItem;

import java.util.List;

/**
 * Presenter 负责完成View于Model间的交互
 * Created by xiongxing on 2017/1/16.
 */

public class MainGetPicPresenter {
    private final IMainGetPicView iMainGetPicView;
    private final ImagesViewBiz imagesViewBiz;

    public MainGetPicPresenter(IMainGetPicView iMainGetPicView) {
        this.iMainGetPicView=iMainGetPicView;
        this.imagesViewBiz = new ImagesViewBiz();
    }

    public void  getPicData(final MainActivity mainActivity, final RecyclerView mRecyclerView){
        //显示加载框
        iMainGetPicView.showLoading();
        imagesViewBiz.getMainPicData(new onGetPicListener() {
            @Override
            public void onGetPicSuccess(final List<ImageItem> imagesList) {
                imagesViewBiz.setRecycleViewData(mainActivity,imagesList,mRecyclerView);
                iMainGetPicView.hideLoading();
            }
        });
    }
}
