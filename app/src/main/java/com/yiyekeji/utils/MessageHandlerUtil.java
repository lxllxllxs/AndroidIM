package com.yiyekeji.utils;

import com.yiyekeji.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 尽量去除无用数据
 * Created by Administrator on 2016/10/21.
 */
public class MessageHandlerUtil {








    /**
     * 解析返回的好友列表，暂时只有用户id和用户名
     * @param jo
     * @return
     */
    private static ArrayList<User> parseLinkListJson(JSONObject jo) {
        ArrayList<User> userList = new ArrayList<>();
        Iterator<String> it=jo.keys();
        User user;
        while (it.hasNext()){
            String key=it.next();
            if ("messageType".equals(key)) {
               continue;
            }
            user = new User();
            try {
                String userName = jo.getString(key);
                user.setUserId(key);
                user.setUsernName(userName);
                userList.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }



}
