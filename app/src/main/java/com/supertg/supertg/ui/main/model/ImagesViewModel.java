package com.supertg.supertg.ui.main.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.api.ImageService;
import com.supertg.supertg.data.bean.ImagesBean;
import com.supertg.supertg.rx.HttpResult;
import com.supertg.supertg.rx.TransformUtils;
import com.supertg.supertg.rx.retrofit.ServiceFactory;
import com.supertg.supertg.rx.subscriber.HttpResultSubscriber;
import com.supertg.supertg.ui.main.adapter.MeizhiListAdapter;
import com.supertg.supertg.ui.main.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.util.ActivitySwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Model类，业务逻辑和实体模型
 * Created by xiongxing on 2017/1/16.
 */

public class ImagesViewModel implements IImagesViewModel {

    @Override
    public void setRecycleViewData(final Activity context, List<String> urlList, RecyclerView mRecyclerView) {
        //瀑布流布局
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        MeizhiListAdapter meizhiListAdapter = new MeizhiListAdapter(context, urlList);
        mRecyclerView.setAdapter(meizhiListAdapter);
        meizhiListAdapter.setOnMeizhiTouchListener(new OnMeizhiTouchListener() {
            @Override
            public void onTouch(View meizhiView, String meizhi, List<String> mList) {
                ActivitySwitcher.startPictureActivity(context, meizhi, meizhiView);
            }
        });
        Logger.e("setRecycleViewData" + urlList.toString());
    }

    @Override
    public void getMainPicData(final onGetPicListener onGetPicListener) {
        ServiceFactory.getInstance()
                .createService(ImageService.class)
                .getImageList()//获取Observable对象
                .compose(TransformUtils.<HttpResult<List<ImagesBean.ErrDesc>>>defaultSchedulers())
                .subscribe(new HttpResultSubscriber<List<ImagesBean.ErrDesc>>() {
                    @Override
                    public void onSuccess(List<ImagesBean.ErrDesc> list) {
                        Logger.e("onNext" + list.get(0).getA());
                        List<String> urlList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            urlList.add(list.get(i).getA());
                        }
                        onGetPicListener.onGetPicSuccess(urlList);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        Logger.e("onError" + e.toString());
                    }
                });
    }
}
