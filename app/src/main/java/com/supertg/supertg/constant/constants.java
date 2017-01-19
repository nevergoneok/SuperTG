package com.supertg.supertg.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by xiongxing on 2017/1/18.
 */

public class Constants {
    /**
     * sd卡根目录
     */
    public static String SDDRECTORY = Environment.getExternalStorageDirectory().getPath() + File.separator;
    public static String GETIMAGESLIST = "http://mlf.f3322.net:83/";
}
