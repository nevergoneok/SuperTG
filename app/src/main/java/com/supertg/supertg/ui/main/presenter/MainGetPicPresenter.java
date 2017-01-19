package com.supertg.supertg.ui.main.presenter;

import android.support.v7.widget.RecyclerView;

import com.supertg.supertg.data.bean.ImagesBean;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.ui.main.model.ImagesViewModel;
import com.supertg.supertg.ui.main.view.IMainGetPicView;
import com.supertg.supertg.ui.main.view.MainActivity;

/**
 * Presenter 负责完成View于Model间的交互
 * Created by xiongxing on 2017/1/16.
 */

public class MainGetPicPresenter {
    private final IMainGetPicView iMainGetPicView;
    private final ImagesViewModel imagesViewModel;

    public MainGetPicPresenter(IMainGetPicView iMainGetPicView) {
        this.iMainGetPicView=iMainGetPicView;
        this.imagesViewModel = new ImagesViewModel();
    }

    public void  getPicData(final MainActivity mainActivity, final RecyclerView mRecyclerView){
        //显示加载框
        iMainGetPicView.showLoading();
        imagesViewModel.getMainPicData(new onGetPicListener() {
            @Override
            public void onGetPicSuccess(final ImagesBean imagesBean) {
                imagesViewModel.setRecycleViewData(mainActivity,imagesBean,mRecyclerView);
                iMainGetPicView.hideLoading();
            }
        });
    }
}
