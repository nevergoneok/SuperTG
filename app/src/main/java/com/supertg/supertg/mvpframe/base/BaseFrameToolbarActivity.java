package com.supertg.supertg.mvpframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.supertg.supertg.base.ToolbarActivity;
import com.supertg.supertg.mvpframe.utils.TUtil;


/**
 * Created by xiongxing on 2017/1/22.
 */

public abstract class BaseFrameToolbarActivity<P extends BasePresenter, M extends BaseModel> extends ToolbarActivity implements BaseView{
    public P mPresenter;

    public M mModel;

    abstract protected int getContentViewId();

    @Override
    protected int provideContentViewId() {
        return getContentViewId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initListener();
    }

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachVM();
        super.onDestroy();
    }

}
