package com.yiyekeji;

import android.app.Application;

/**
 * Created by lxl on 2016/10/26.
 */
public class IMApp extends Application {
    private static IMApp instance;
    public static IMApp getInstance() {
        if (instance == null) {
            instance=new IMApp();
        }
        return instance;
    }
}
