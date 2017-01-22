package com.supertg.supertg.mvpframe.rx.http;

import com.orhanobut.logger.Logger;
import com.supertg.supertg.mvpframe.base.ActivityLifeCycleEvent;
import com.supertg.supertg.mvpframe.rx.RxHelper;
import com.supertg.supertg.mvpframe.rx.httpresult.HttpResult;
import com.supertg.supertg.mvpframe.rx.subscriber.ProgressSubscriber;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by helin on 2016/10/10 11:32.
 */

public class HttpUtil {

    /**
     * 构造方法私有
     */
    private HttpUtil() {

    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param subscriber
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        //数据预处理
        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Logger.e("showProgressDialog");
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                }).subscribe(subscriber);
    }
}
