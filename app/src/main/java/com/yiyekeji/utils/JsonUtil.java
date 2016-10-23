package com.yiyekeji.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/21.
 */
public class JsonUtil {
    public final static String MESSAG_TYPE="messageType";

    public final static String CONTENT="content";


    public final static String SENDER="sender";
    public final static String RECEIVER="receiver";
    public final static String DATE="date";
    public final static String SENDER_ID="senderId";
    public final static String RECEIVER_ID="receiverID";

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
}
