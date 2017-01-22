package com.supertg.supertg.ui.main;

import com.supertg.supertg.mvpframe.rx.Api;

import rx.Observable;

/**
 * Created by xiongxing on 2017/1/22.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable getMeiZiList(int page) {
        return Api.getInstance().getDefault().getMeiZiList(1);
    }
}
