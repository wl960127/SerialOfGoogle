package com.yfsd.comtest.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FTX on 2017/12/4.
 */

public class ActivityCollector {
    private static ActivityCollector instance = new ActivityCollector();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static ActivityCollector getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        activityStack.add(aty);
    }

    public void removeActivity(Activity aty) {
        activityStack.remove(aty);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}
