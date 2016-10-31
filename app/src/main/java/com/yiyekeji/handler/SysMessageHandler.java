package com.yiyekeji.handler;

import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.utils.LogUtil;

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
        IMessageFactory.IMessage.Builder builder= IMessageFactory.IMessage.newBuilder();
        IMessageFactory.IMessage.User.Builder builder1= IMessageFactory.IMessage.User.newBuilder();
        builder.setMainType("0");
        builder.setSubType("0");
        builder1.setPassword(password);
        builder1.setUsername(username);
        IMessageFactory.IMessage.User user=builder1.build();
        IMessageFactory.IMessage message=builder.build();
        builder.addUser(builder1.build());

        IMessageFactory.IMessage iMessage=builder.build();

        LogUtil.d("loginSys",iMessage.toString());



        return iMessage;
    }
}
