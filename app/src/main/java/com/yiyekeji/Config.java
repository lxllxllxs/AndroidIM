package com.yiyekeji;

import android.os.Environment;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Config {

    public static final boolean DEBUG=true;

//    public static final String IM_URL="ws://192.168.1.103:8080/AndroidWebSocket/Chat";//自机测试
    public static final String IM_URL="ws://192.168.10.187:9090/AndroidWebSocket/Chat";//公司测试
    public static final String LOG_FILTER = "YiYeMarket";
    public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory() + "/AndroidIM";
    public static final String IMAGE_DIR = "/image/";
    public static final String DATA_DIR = "/data/";
    public static final int USER_HEAD_SIZE = 350;
    public static final String RECORD_FORMAT = ".aac";
}
