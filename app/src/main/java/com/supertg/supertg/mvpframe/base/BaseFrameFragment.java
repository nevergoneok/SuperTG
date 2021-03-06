package com.supertg.supertg.mvpframe.base;

import android.os.Bundle;

import com.supertg.supertg.base.BaseFragment;
import com.supertg.supertg.mvpframe.utils.TUtil;


/**
 * Created by Administrator on 2016/12/31.
 */

public abstract class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView{
    public P mPresenter;

    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        super.onDestroy();
    }
}
