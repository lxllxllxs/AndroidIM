package com.yiyekeji.handler;

import com.yiyekeji.bean.User;
import com.yiyekeji.imenum.ChatMessageType;
import com.yiyekeji.imenum.MainType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ChatMessageHandler {



    /*************************聊天信息****************************/
    public final static String MESSAG_TYPE="messageType";
    public final static String CONTENT="content";
    public final static String JSON_ARRAY="jsonArray";
    public final static String LinkList="LinkList";
    public final static String SENDER="sender";
    public final static String RECEIVER="receiver";
    public final static String DATE="date";
    public final static String SENDER_ID="senderId";
    public final static String RECEIVER_ID="receiverID";

    /**
     * 普通单人文本信息
     * @param user
     * @param content
     * @return
     */
    public static JSONObject sendTextMessage(User user, String content){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MainType.getName(), MainType.ChatMessageType);
            jsonObject.put(ChatMessageType.getName(),ChatMessageType.TextMessage);

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

    /**
     * 接收离线信息
     * @param user
     * @return
     */
    public static JSONObject getUnReceiveMessage(User user){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MainType.getName(),MainType.ChatMessageType);
            jsonObject.put(ChatMessageType.getName(),ChatMessageType.UnReceiveMessage);
            jsonObject.put(SENDER,user.getUsernName());
            jsonObject.put(SENDER_ID,user.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
