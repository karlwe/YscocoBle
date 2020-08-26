package com.ys.module.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityCollectorUtils {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        Activity activity = null;
        if (activities == null || (activities.size() == 0)) {
            return;
        }
        for (int i = (activities.size() - 1); i >= 0; i--) {
            activity = activities.get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
            activities.remove(i);
        }
    }

    public static void finishOther(Activity mActivity) {
        Activity activity = null;
        if (activities == null || (activities.size() == 0)) {
            return;
        }
        for (int i = (activities.size() - 2); i >= 0; i--) {
            activity = activities.get(i);
            if(activity!=mActivity) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
                activities.remove(i);
            }
        }
    }



}
