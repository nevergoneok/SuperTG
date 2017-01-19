package com.supertg.supertg.ui.main.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.constant.Constants;
import com.supertg.supertg.data.bean.ImagesBean;
import com.supertg.supertg.http.ImageApi;
import com.supertg.supertg.ui.main.adapter.MeizhiListAdapter;
import com.supertg.supertg.ui.main.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.ui.main.interfaces.onGetPicListener;
import com.supertg.supertg.util.ActivitySwitcher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Model类，业务逻辑和实体模型
 * Created by xiongxing on 2017/1/16.
 */

public class ImagesViewModel implements IImagesViewModel {

    @Override
    public void setRecycleViewData(final Activity context, ImagesBean imagesBean, RecyclerView mRecyclerView) {
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < imagesBean.getErrDesc().size(); i++) {
            urlList.add(imagesBean.getErrDesc().get(i).getA());
        }
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
        Logger.e("setRecycleViewData"+urlList.toString());
    }

    @Override
    public void getMainPicData(final onGetPicListener onGetPicListener) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.GETIMAGESLIST)
                .build();
        ImageApi imageApi = retrofit.create(ImageApi.class);
        imageApi.getImageList()//获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程执行
                .observeOn(Schedulers.io())//请求完在io线程执行
                .doOnNext(new Action1<ImagesBean>() {
                    @Override
                    public void call(ImagesBean imagesBean) {
                        //去保存list到数据库
                        Logger.e("doOnNext===" + imagesBean.getRet() + "====" + imagesBean.getErrDesc().size());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Observer<ImagesBean>() {
                    @Override
                    public void onNext(ImagesBean imagesBean) {
                        Logger.e("onNext" + imagesBean.getErrDesc().get(0).getA());
                        onGetPicListener.onGetPicSuccess(imagesBean);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.e("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("onError" + e.toString());
                    }
                });
    }
}
