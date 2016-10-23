package com.yiyekeji.utils;


import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/10/13.
 */
public class GsonUtils {



    public static String beanToString(Object object ){
        Gson gson=new Gson();
        String s=gson.toJson(object);
        return s;
    }
}
