package com.supertg.supertg.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*功能：一键退出App
 * 活动管理器
 * */
public class ActivityCollector {
    //储存所有打开的activity
    public static List<Activity> activities = new ArrayList<Activity>();

    //把打开的activity添加进activities这个List
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移除当前activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //一键退出APP
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
