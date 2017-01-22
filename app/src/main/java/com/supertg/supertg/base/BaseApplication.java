package com.supertg.supertg.base;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * Created by xiongxing on 2017/1/16.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
    }

    public static BaseApplication getInstance()
    {
        return mInstance;
    }

    /**
     * 缓存数据地址
     * @return
     */
    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return super.getCacheDir();
    }
}
