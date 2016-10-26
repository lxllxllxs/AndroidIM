package com.yiyekeji.handler;

import com.yiyekeji.bean.ReceiveMessage;
import com.yiyekeji.bean.User;
import com.yiyekeji.imenum.ChatMessageType;
import com.yiyekeji.imenum.MainType;
import com.yiyekeji.imenum.SysMessType;
import com.yiyekeji.iminterface.ReceiverCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ReceiverHandler {


    private static ReceiverCallBack callBack;
    /**************************Receive***************************************/
    /**
     * 头部 MainType
     * MESSAGE_TYPE：ChatMessageType or SysMessType;
     * @param jsonString
     * @param callBack1
     * @return
     */
    public static Object receive(String jsonString, ReceiverCallBack callBack1){
        callBack=callBack1;
        JSONObject jsonObject;
        SysMessType sysMessType;
        ChatMessageType chatMessageType;
        try {
            jsonObject= new JSONObject(jsonString);
            MainType type= MainType.valueOf(jsonObject.getString(MainType.getName()));
            //根据枚举类型获取其键对应的值 再转为枚举类型判断
            String  secondType=jsonObject.getString(type.toString());

            if (ChatMessageType.contain(secondType)){
                chatMessageType = ChatMessageType.valueOf(secondType);
                ChatMessage(chatMessageType,jsonObject);
            }else {
                sysMessType = SysMessType.valueOf(secondType);
                SysMessage(sysMessType,jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                break;
            case ImageMessage:
                break;
            case RedPocketMessage:
                break;

        }
        return null;
    }

    public static Object  SysMessage(SysMessType type,JSONObject jsonObject){
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
     * 解析返回的好友列表，暂时只有用户id和用户名
     * @param jo
     * @return
     */
    private static void parseLinkListJson(JSONObject jo) {
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
        callBack.receiverCallBack(rm);
//        rm.setUsers(userList);
//        Intent intent =new Intent(ConstantUtil.USER_LIST);
//        intent.putExtra(ConstantUtil.USER_LIST,rm);
//        context.sendBroadcast(intent);
    }

}
