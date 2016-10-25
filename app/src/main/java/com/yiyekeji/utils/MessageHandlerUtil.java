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
    public final static String MESSAG_TYPE="messageType";

    public final static String CONTENT="content";

    public final static String LinkList="LinkList";
    public final static String SENDER="sender";
    public final static String RECEIVER="receiver";
    public final static String DATE="date";
    public final static String SENDER_ID="senderId";
    public final static String RECEIVER_ID="receiverID";

    public final static String USER_NAME="userName";
    public final static String PASSWORD="password";

    public final static String RESULT="result";

    public static JSONObject  sendTextMessage(User user,String content){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MESSAG_TYPE, Eume.MessageType.TextMessage);

            jsonObject.put(SENDER,"lxl");
            jsonObject.put(SENDER_ID,"123");
            jsonObject.put(RECEIVER,user.getUsernName());
            jsonObject.put(RECEIVER_ID,user.getUserId());
            jsonObject.put(CONTENT,content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject  login(String username,String password){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MESSAG_TYPE, Eume.MessageType.Login);

            jsonObject.put(USER_NAME,username);
            jsonObject.put(PASSWORD,password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**************************Receive***************************************/
    public static Object receive(String jsonString){
        JSONObject jsonObject=null;
        Eume.MessageType type=null;
        try {
            jsonObject = new JSONObject(jsonString);
            type= Eume.MessageType.valueOf(jsonObject.getString(MESSAG_TYPE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (type){
            case RedPocketMessage:
                break;
            case LinkMan:
               return parseLinkListJson(jsonObject);
            case TextMessage:
                break;
            case ImageMessage:
                break;
        }
        return null;
    }

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
