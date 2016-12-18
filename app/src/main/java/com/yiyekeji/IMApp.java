package com.yiyekeji;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.yiyekeji.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2016/10/26.
 */
public class IMApp extends Application {
    public static  boolean isLogin;

    public static List<UserInfo> linkManList = new ArrayList<>();
    public static UserInfo userInfo = new UserInfo();
    public static List<Activity> activityList = new ArrayList<>();
    public static Context context;
    public static UserInfo otherSide;//可用来判断当前时与哪个用户聊天
    public static void clearAllCache(){
        userInfo = new UserInfo();//指向新对象
        linkManList.clear();
        isLogin=false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void removeAllActivity(){
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }


    /**
     * 从全局变量中查找好友信息
     */
    public static UserInfo getUserInfo(String senderId) {
        for (UserInfo info : linkManList) {
            if (info.getUserId().equals(senderId)) {
                return info;
            }
        }
        return null;
    }
}
