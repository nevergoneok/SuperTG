package com.supertg.supertg.ui.main.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.api.MeiZiService;
import com.supertg.supertg.data.table.Meizhi;
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

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
                .createService(MeiZiService.class)
                .getMeiZiList(1)//获取Observable对象
                .compose(TransformUtils.<HttpResult<List<Meizhi>>>defaultSchedulers())//不需要数据库操作可以这样写
                .subscribeOn(Schedulers.io()) //指定耗时进程
                .observeOn(Schedulers.newThread()) //指定doOnNext执行线程是新线程
                .doOnNext(new Action1<HttpResult<List<Meizhi>>>() {
                    @Override
                    public void call(HttpResult<List<Meizhi>> listHttpResult) {
                        Logger.e("doOnNext线程=="+Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定最后观察者在主线程
                .subscribe(new HttpResultSubscriber<List<Meizhi>>() {
                    @Override
                    public void onSuccess(List<Meizhi> list) {
                        Logger.e("onNext" + list.get(0).url);
                        List<String> urlList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            urlList.add(list.get(i).url);
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
