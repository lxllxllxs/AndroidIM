package com.yiyekeji.Event;

import com.yiyekeji.bean.IMessageFactory;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ChatMessageEvent {
    public IMessageFactory.IMessage getiMessage() {
        return iMessage;
    }

    public void setiMessage(IMessageFactory.IMessage iMessage) {
        this.iMessage = iMessage;
    }

    IMessageFactory.IMessage iMessage;
}
