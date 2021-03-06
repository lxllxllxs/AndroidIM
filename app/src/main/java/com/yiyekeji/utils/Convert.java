package com.yiyekeji.utils;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.dao.Session;

/**
 * Created by lxl on 2016/12/11.
 */
public class Convert  {
    /**
     * 网络转本地
     * @param iMessage
     * @return
     * 在这的操作：
     * 判断接收方、记录信息归属账号
     *
     */
    public static ChatMessage IMessageToChatMessage(IMessageFactory.IMessage iMessage) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setSenderId(iMessage.getSenderId());
        chatMessage.setSenderName(iMessage.getSenderName());

        chatMessage.setMsgId(iMessage.getId());
        chatMessage.setContent(iMessage.getContent());
        chatMessage.setDate(DateUtil.getTimeString());//也许可改为直接存日期

        chatMessage.setReceiverId(iMessage.getReceiverId());//
        chatMessage.setReceiverName(iMessage.getReceiverName());
        //根据比对Id 记录是否接收方
        chatMessage.setIsReceiver(iMessage.getReceiverId().equals(IMApp.userInfo.getUserId())?"1":"0");
        //记录归属
        chatMessage.setOwner(IMApp.userInfo.getUserId());
        //设置聊天信息类型
        chatMessage.setMessageType(iMessage.getSubType());

        return chatMessage;
    }

    /**
     * 这里的未读消息应该有误 未考虑到在外接收时
     * @param msgId
     * @param userId
     * @return
     */
    public static Session createSessionFromMsg(String msgId,String userId) {
        return createSessionFromMsg(msgId,userId,0);
    }
    public static Session createSessionFromMsg(String msgId,String userId,int unRead) {
        Session session = new Session();
        session.setMsgId(msgId);
        session.setUserId(userId);
        session.setUnRead(unRead);
        session.setOwner(IMApp.userInfo.getUserId());
        return session;
    }


}
