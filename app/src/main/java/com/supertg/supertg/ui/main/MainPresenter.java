package com.supertg.supertg.ui.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.adapter.MeizhiListAdapter;
import com.supertg.supertg.bean.Meizhi;
import com.supertg.supertg.interfaces.OnMeizhiTouchListener;
import com.supertg.supertg.mvpframe.base.ActivityLifeCycleEvent;
import com.supertg.supertg.mvpframe.rx.http.HttpUtil;
import com.supertg.supertg.mvpframe.rx.subscriber.ProgressSubscriber;
import com.supertg.supertg.util.ActivitySwitcher;

import java.util.ArrayList;
import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by xiongxing on 2017/1/22.
 */

public class MainPresenter extends MainContract.Presenter {
    @Override
    void getMeiZiList(final MainActivity mainActivity, final RecyclerView mRecyclerView, int page, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        mView.showLoading();
        HttpUtil.getInstance().toSubscribe(mModel.getMeiZiList(page), new ProgressSubscriber<List<Meizhi>>(mainActivity) {

            @Override
            protected void _onNext(List<Meizhi> meizhis) {
                mView.hideLoading();
                Logger.e("onNext==" + meizhis.get(0).url);
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i < meizhis.size(); i++) {
                    urlList.add(meizhis.get(i).url);
                }
                //瀑布流布局
                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(layoutManager);
                MeizhiListAdapter meizhiListAdapter = new MeizhiListAdapter(mainActivity, urlList);
                mRecyclerView.setAdapter(meizhiListAdapter);
                meizhiListAdapter.setOnMeizhiTouchListener(new OnMeizhiTouchListener() {
                    @Override
                    public void onTouch(View meizhiView, String meizhi, List<String> mList) {
                        ActivitySwitcher.startPictureActivity(mainActivity, meizhi, meizhiView);
                    }
                });
            }

            @Override
            protected void _onError(String message) {
                Logger.e("onError==" + message);
            }
        }, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }
}
