package com.gjs.gjsmall.user.login.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  GJSTXDemo0418_1 
 *  @包名：    com.gjs.gjsmall.user.login.base
 *  @文件名:   ActivityCollector
 *  @创建者:   Administrator
 *  @创建时间:  2016/4/18 15:37
 *  @描述：    TODO
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
