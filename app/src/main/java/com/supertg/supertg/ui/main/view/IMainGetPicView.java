package com.supertg.supertg.ui.main.view;

/**
 * 对应于Activity，负责View的绘制以及与用户交互
 * Created by xiongxing on 2017/1/16.
 */

public interface IMainGetPicView {

    //①获取是个耗时的过程，要显示进度条
    void showLoading();
    void hideLoading();
}
