package com.supertg.supertg.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.supertg.supertg.ui.about.view.AboutActivity;
import com.supertg.supertg.ui.pic.view.PictureActivity;


/**
 * Created by Administrator on 2016/6/14.
 */
public class ActivitySwitcher {


    private static void startActivityDefault(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 可爱的关于页面
     * @param activity
     */
    public static void goAboutAct(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        startActivityDefault(activity, intent);
    }

    public static void
    startPictureActivity(Activity context, String meizhi, View transitView) {
        Intent intent = new Intent(context,PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL,meizhi);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, transitView, PictureActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            context.startActivity(intent);
        }
    }
}
