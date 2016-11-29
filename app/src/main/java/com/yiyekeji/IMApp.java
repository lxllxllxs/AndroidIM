package com.yiyekeji;

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

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }



}
