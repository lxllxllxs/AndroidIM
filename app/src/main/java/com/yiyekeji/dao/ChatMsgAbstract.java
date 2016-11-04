package com.yiyekeji.dao;

/**
 * 聊天信息
 * Created by lxl on 2016/11/4.
 */
public abstract class ChatMsgAbstract {
    public abstract String getId() ;

    public abstract void setId(String id);

    public abstract String getMsgId();

    public abstract void setMsgId(String msgId);
    public abstract String getSenderId();

    public abstract void setSenderId(String senderId);

    public abstract  String getReceiverId();

    public abstract void setReceiverId(String receiverId);

    public abstract String getGroupId();

    public abstract void setGroupId(String groupId);

    public abstract String getMessageType();

    public abstract void setMessageType(String messageType);

    public abstract String getContent();

    public abstract void setContent(String content);

    public abstract String getDate() ;

    public abstract void setDate(String date) ;

    public abstract Boolean getIsReceiver();

    public abstract void setIsReceiver(Boolean isReceiver);
}
