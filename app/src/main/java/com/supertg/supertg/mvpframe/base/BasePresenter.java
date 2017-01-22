package com.supertg.supertg.mvpframe.base;

/**
 * Created by Administrator on 2016/12/31.
 */

public abstract class BasePresenter<M, V> {
    public M mModel;
    public V mView;

    public void attachVM(V v, M m) {
        this.mModel = m;
        this.mView = v;

    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

}
