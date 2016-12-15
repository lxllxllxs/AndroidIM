package com.yiyekeji.handler;

import android.net.Uri;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.utils.DateUtil;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.utils.IMessageBuilder;
import com.yiyekeji.utils.ImageUtils;

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


    /**
     * 普通单人多媒体信息
     * 这里就组装完全了
     * @return
     */
    public static IMessageFactory.IMessage sendImgMessage(Uri uri, UserInfo receiver) {
        IMessageFactory.IMessage.Builder imBuidler= IMessageBuilder.getBuilder();

        imBuidler.setMainType("1");
        imBuidler.setSubType("1");
        imBuidler.setReceiverId(receiver.getUserId());
        imBuidler.setReceiverName(receiver.getUserName());

        imBuidler.setSenderId(sender.getUserId());
        imBuidler.setSenderName(sender.getUserName());

        imBuidler.setDate(DateUtil.getTimeString());
        try {
            imBuidler.setContent(ImageUtils.toBase64(IMApp.getContext(),uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        IMessageFactory.IMessage.Builder imBuidler2=imBuidler.clone();
        imBuidler2.setContent(uri.toString());
        IMessageFactory.IMessage iMessage=imBuidler.build();
        IMessageFactory.IMessage iMessage2=imBuidler2.build();
        DbUtil.saveSendMessage(iMessage2);//本地只是保存其uri
        return iMessage;
    }
}
