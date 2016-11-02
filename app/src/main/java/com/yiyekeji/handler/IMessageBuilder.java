package com.yiyekeji.handler;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;

import java.util.UUID;

/**
 * Created by lxl on 2016/11/2.
 */
public class IMessageBuilder {
    public static IMessageFactory.IMessage.Builder getBuilder() {
        IMessageFactory.IMessage.Builder imBuidler=IMessageFactory.IMessage.newBuilder();
        imBuidler.setId(UUID.randomUUID().toString());
        imBuidler.setSenderId(IMApp.userInfo.getUserId());
        return imBuidler;
    }
}
