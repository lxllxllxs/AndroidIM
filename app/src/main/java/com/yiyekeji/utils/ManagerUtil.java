package com.yiyekeji.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.yiyekeji.IMApp;

import java.util.List;

/**
 * Created by lxl on 2016/11/28.
 */
public class ManagerUtil {
    protected ActivityManager mActivityManager = (ActivityManager) IMApp.getContext()
            .getSystemService(Context.ACTIVITY_SERVICE);

    public ActivityManager.RunningTaskInfo getTopTask() {
        List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            return tasks.get(0);
        }
        return null;
    }

    public boolean isTopActivity( String packageName, String activityName){
        ComponentName topActivity=getTopTask().topActivity;
        if (topActivity.getClassName().equals(activityName) &&
                topActivity.getPackageName().equals(packageName)) {
            return true;
        }
        return false;
    }
}
