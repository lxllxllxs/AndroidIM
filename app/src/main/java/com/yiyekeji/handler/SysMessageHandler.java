package com.yiyekeji.handler;

import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.utils.IMessageBuilder;
import com.yiyekeji.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class SysMessageHandler {

    public final static String MESSAG_TYPE="messageType";

    public final static String USER_NAME="userName";
    public final static String PASSWORD="password";
    public final static String RESULT="result";


    /**
     * 发送登录信息
     * @param username
     * @param password
     * @return
     */
    public static IMessageFactory.IMessage login(String username, String password){
        IMessageFactory.IMessage.Builder builder= IMessageBuilder.getBuilder();
        IMessageFactory.IMessage.User.Builder builder1= IMessageFactory.IMessage.User.newBuilder();
        builder.setMainType("0");
        builder.setSubType("0");
        builder1.setPassword(password);
        builder1.setUsername(username);
        IMessageFactory.IMessage.User user=builder1.build();
        builder.addUser(user);
        IMessageFactory.IMessage iMessage=builder.build();
        LogUtil.d("loginSys",iMessage.toString());
        return iMessage;
    }


    /**
     * 获得所有好友
     * @return
     */
    public static IMessageFactory.IMessage getLinkManList(){
        IMessageFactory.IMessage.Builder builder=IMessageBuilder.getBuilder();
        IMessageFactory.IMessage.User.Builder builder1= IMessageFactory.IMessage.User.newBuilder();
        builder.setMainType("0");
        builder.setSubType("1");
        IMessageFactory.IMessage.User user=builder1.build();
        builder.addUser(user);
        IMessageFactory.IMessage iMessage=builder.build();
        LogUtil.d("loginSys",iMessage.toString());
        return iMessage;
    }

    /**0 2
     * 获得离线消息
     * @return
     */
    public static IMessageFactory.IMessage getUnRecieveMessage(){
        IMessageFactory.IMessage.Builder builder=IMessageBuilder.getBuilder();
        builder.setMainType("0");
        builder.setSubType("2");
        IMessageFactory.IMessage iMessage=builder.build();
        LogUtil.d("getUnRecieveMessage",iMessage.toString());
        return iMessage;
    }


    /**0 5
     * 反馈已经接收的离线消息id
     * @return
     */
    public static IMessageFactory.IMessage feedBackUnRecieveMessage(List<String> msgIdList){
        IMessageFactory.IMessage.Builder builder=IMessageBuilder.getBuilder();
        builder.setMainType("0");
        builder.setSubType("5");
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<msgIdList.size();i++) {
            sb.append(msgIdList.get(i));
            if (i==msgIdList.size()-1){
                continue;
            }
            sb.append(",");
        }
        builder.setContent(sb.toString());
        IMessageFactory.IMessage iMessage=builder.build();
        LogUtil.d("getUnRecieveMessage",iMessage.toString());
        return iMessage;
    }
}
