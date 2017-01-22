package com.supertg.supertg.ui.main;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.bean.Meizhi;
import com.supertg.supertg.eventbus.CommonEvent;
import com.supertg.supertg.mvpframe.base.ActivityLifeCycleEvent;
import com.supertg.supertg.mvpframe.rx.http.HttpUtil;
import com.supertg.supertg.mvpframe.rx.subscriber.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by xiongxing on 2017/1/22.
 */

public class MainPresenter extends MainContract.Presenter {
    @Override
    void getMeiZiList(final MainActivity mainActivity,  int page, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
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
                EventBus.getDefault().post(new CommonEvent(urlList,CommonEvent.REQUESTSUEECCD,CommonEvent.asyncRecycleMain));
            }
            @Override
            protected void _onError(String message) {
                Logger.e("onError==" + message);
            }
        }, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }
}
