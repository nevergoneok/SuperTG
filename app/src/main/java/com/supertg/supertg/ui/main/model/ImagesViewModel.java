package com.supertg.supertg.ui.main.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.base.ActivityLifeCycleEvent;
import com.supertg.supertg.enity.Meizhi;
import com.supertg.supertg.rx.Api;
import com.supertg.supertg.rx.http.HttpUtil;
import com.supertg.supertg.rx.subscriber.ProgressSubscriber;
import com.supertg.supertg.ui.main.adapter.MeizhiListAdapter;
import com.supertg.supertg.ui.main.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.util.ActivitySwitcher;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

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
    public void getMainPicData(final Activity context,final onGetPicListener onGetPicListener,PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable meiZiList = Api.getInstance().getDefault().getMeiZiList(1);
        HttpUtil.getInstance().toSubscribe(meiZiList, new ProgressSubscriber<List<Meizhi>>(context) {

                    @Override
                    protected void _onNext(List<Meizhi> meizhis) {
                        Logger.e("onNext==" + meizhis.get(0).url);
                        List<String> urlList = new ArrayList<>();
                        for (int i = 0; i < meizhis.size(); i++) {
                            urlList.add(meizhis.get(i).url);
                        }
                        onGetPicListener.onGetPicSuccess(urlList);
                    }

                    @Override
                    protected void _onError(String message) {
                        Logger.e("onError==" + message);
                    }
                },ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }
}
