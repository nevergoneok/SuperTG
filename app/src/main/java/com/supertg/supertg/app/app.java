package com.supertg.supertg.app;

import android.app.Application;

/**
 * Created by xiongxing on 2017/1/16.
 */

public class app extends Application {

    private static app mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }

    public static app getApplication()
    {
        return mContext;
    }
}
