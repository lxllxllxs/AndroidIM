package com.yiyekeji.handler;

import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.ReceiveMessage;
import com.yiyekeji.bean.User;
import com.yiyekeji.imenum.ChatMessageType;
import com.yiyekeji.imenum.MainType;
import com.yiyekeji.imenum.SysMessType;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ReceiverHandler {


    /**************************Receive***************************************/
    /**
     * 头部 MainType
     * MESSAGE_TYPE：ChatMessageType or SysMessType;
     * @param iMessage
     * @return
     */
    public static Object receive(IMessageFactory.IMessage iMessage){
        JSONObject jsonObject;
        SysMessType sysMessType;
        ChatMessageType chatMessageType;
        if (iMessage.getMainType().equals("0")){
            switch (iMessage.getSubType()){
                case "0":
                    LoginEvent loginEvent=new LoginEvent();
                    if (iMessage.getResult().equals("1")){
                        loginEvent.setSuccess(true);
                    }else {
                        loginEvent.setSuccess(false);
                    }
                    EventBus.getDefault().post(loginEvent);
                    break;
            }
        }


        return null;
    }


    /**
     * 解析聊天信息
     * @param type
     * @param jsonObject
     * @return
     */
    public static Object  ChatMessage(ChatMessageType type,JSONObject jsonObject){
        switch (type) {
            case TextMessage:
                unReceiveMessage(jsonObject);
                break;
            case ImageMessage:
                break;
            case RedPocketMessage:
                break;

        }
        return null;
    }

    public static Object SysMessage(SysMessType type,JSONObject jsonObject){
        switch (type) {
            case Login:
               parseLinkListJson(jsonObject);
                break;
            case AddFriend:
                break;
            case AddGrop:
                break;
        }
        return null;
    }



    /**
     * 解析返回的好友列表，和用户本身信息，暂时只有用户id和用户名
     * @param jo
     * @return
     */
    private static void parseLinkListJson(JSONObject jo) {
        try {
            if (jo.getString("result").equals("false")){
                LoginEvent loginEvent=new LoginEvent();
                loginEvent.setSuccess(false);
                EventBus.getDefault().post(loginEvent);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> userList = new ArrayList<>();
        Iterator<String> it=jo.keys();
        User user;
        while (it.hasNext()){
            String key=it.next();
            if (MainType.getName().equals(key)||
                SysMessType.getName().equals(key)) {
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
        ReceiveMessage rm=new ReceiveMessage();
        rm.setUsers(userList);
        LoginEvent loginEvent=new LoginEvent();
        loginEvent.setSuccess(true);
        loginEvent.setMessage(rm);
        EventBus.getDefault().post(loginEvent);
//        rm.setUsers(userList);
//        Intent intent =new Intent(ConstantUtil.USER_LIST);
//        intent.putExtra(ConstantUtil.USER_LIST,rm);
//        context.sendBroadcast(intent);
    }


    private static void unReceiveMessage(JSONObject jo) {
        ReceiveMessage rm=null;
        try {
            rm=new ReceiveMessage();
            rm.setReceiverID(jo.getString(ChatMessageHandler.RECEIVER_ID));
            rm.setReceiver(jo.getString(ChatMessageHandler.RECEIVER));
            rm.setContent(jo.getString(ChatMessageHandler.CONTENT));
            rm.setSender(jo.getString(ChatMessageHandler.SENDER));
            rm.setSenderId(jo.getString(ChatMessageHandler.SENDER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
