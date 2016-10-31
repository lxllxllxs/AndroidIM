package com.yiyekeji.handler;

import com.yiyekeji.bean.IMessageFactory;

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
     * @return
     */
    public static IMessageFactory.IMessage sendTextMessage(String content, String receiverId) {
        IMessageFactory.IMessage.Builder imBuidler=IMessageFactory.IMessage.newBuilder();
        imBuidler.setContent(content);
        imBuidler.setMainType("1");
        imBuidler.setSubType("0");
        imBuidler.setReceiverId(receiverId);
        IMessageFactory.IMessage iMessage=imBuidler.build();

        return iMessage;
    }
}
