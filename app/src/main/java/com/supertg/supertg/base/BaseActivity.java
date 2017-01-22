package com.supertg.supertg.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.supertg.supertg.mvpframe.base.ActivityLifeCycleEvent;

import rx.subjects.PublishSubject;

/**
 * activity基类
 * Created by helin on 2016/11/11 10:41.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        ActivityCollector.addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从活动管理器移除
        ActivityCollector.removeActivity(this);
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }
}
