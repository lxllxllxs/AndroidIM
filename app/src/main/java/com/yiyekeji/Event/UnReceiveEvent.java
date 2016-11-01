package com.yiyekeji.Event;

import com.yiyekeji.bean.IMessageFactory;

/**
 * Created by Administrator on 2016/11/1.
 */
public class UnReceiveEvent {
    private IMessageFactory.IMessage iMessage;

    public IMessageFactory.IMessage getiMessage() {
        return iMessage;
    }

    public void setiMessage(IMessageFactory.IMessage iMessage) {
        this.iMessage = iMessage;
    }
}
