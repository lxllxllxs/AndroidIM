package com.yiyekeji.handler;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.utils.DateUtil;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.utils.IMessageBuilder;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ChatMessageHandler {

    public  static  UserInfo sender=IMApp.userInfo;

    /**
     * 普通单人文本信息
     * 这里就组装完全了
     * @return
     */
    public static IMessageFactory.IMessage sendTextMessage(String content, UserInfo receiver) {
        IMessageFactory.IMessage.Builder imBuidler= IMessageBuilder.getBuilder();
        imBuidler.setContent(content);
        imBuidler.setMainType("1");
        imBuidler.setSubType("0");
        imBuidler.setReceiverId(receiver.getUserId());
        imBuidler.setReceiverName(receiver.getUserName());

        imBuidler.setSenderId(sender.getUserId());
        imBuidler.setSenderName(sender.getUserName());

        imBuidler.setDate(DateUtil.getTimeString());

        IMessageFactory.IMessage iMessage=imBuidler.build();

        DbUtil.saveSendMessage(iMessage);
        return iMessage;
    }
}
