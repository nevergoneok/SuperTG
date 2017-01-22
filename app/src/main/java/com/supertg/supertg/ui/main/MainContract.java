package com.supertg.supertg.ui.main;

import com.supertg.supertg.mvpframe.base.ActivityLifeCycleEvent;
import com.supertg.supertg.mvpframe.base.BaseModel;
import com.supertg.supertg.mvpframe.base.BasePresenter;
import com.supertg.supertg.mvpframe.base.BaseView;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by xiongxing on 2017/1/22.
 */

public interface MainContract {

    /**
     * model去请求网络数据
     */
    interface Model extends BaseModel{
        Observable getMeiZiList(int page);
    }

    /**
     * activity  fragment要显示UI的更新
     */
    interface View extends BaseView{
        //①获取是个耗时的过程，要显示进度条
        void showLoading();
        void hideLoading();
    }

    /**
     * 处理数据返回，数据处理，沟通view和model     Presenter持有view和model去做操作
     */
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void  getMeiZiList(final MainActivity mainActivity,int page, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject);
    }
}
