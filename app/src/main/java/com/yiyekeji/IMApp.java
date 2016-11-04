package com.yiyekeji;

import android.app.Application;
import android.content.Context;

import com.yiyekeji.bean.UserInfo;

/**
 * Created by lxl on 2016/10/26.
 */
public class IMApp extends Application {
    public static  boolean isLogin;


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
