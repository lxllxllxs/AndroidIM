package com.yiyekeji.utils;

import com.yiyekeji.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/21.
 */
public class JsonUtil {
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

    public static JSONObject  sendTextMessage(String receiver,String receiverId,String content){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MESSAG_TYPE, Eume.MessageType.TextMessage);

            jsonObject.put(SENDER,"lxl");
            jsonObject.put(SENDER_ID,"123");
            jsonObject.put(RECEIVER,receiver);
            jsonObject.put(RECEIVER_ID,receiverId);
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
    public static Object receive(byte[] payload){
        JSONObject jsonObject=null;
        Eume.MessageType type=null;
        try {
            jsonObject = new JSONObject(new String(payload));
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

    private static ArrayList<User> parseLinkListJson(JSONObject jo) {
        ArrayList<User> userList = new ArrayList<>();
        Iterator<String> it=jo.keys();
        User user;
        while (it.hasNext()){
            String key=it.next();
            if (LinkList.equals(key)) {
                user = new User();
                try {
                    String userinfo = jo.getString(key);
                    String [] users=userinfo.split(",");
                    user.setUserId(users[0]);
                    user.setUsernName(users[1]);
                    userList.add(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }



}
