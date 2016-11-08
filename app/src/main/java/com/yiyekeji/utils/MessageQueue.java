package com.yiyekeji.utils;

import com.yiyekeji.bean.IMessageFactory;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 消息队列
 * 缓存信息
 * 当信息发送成功（服务端接收成功并反馈）
 * Created by lxl on 2016/11/2.
 */
public class MessageQueue {


    LinkedBlockingDeque<IMessageFactory.IMessage> blockingDeque=new LinkedBlockingDeque<>();
    /** 压入 */
    public void putEgg(IMessageFactory.IMessage iMessage) {
        try {
            blockingDeque.put(iMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.d("MessageQueue:put：:现在大小为",blockingDeque.size());
    }

    /** 取出 */
    public IMessageFactory.IMessage getEgg() {
        IMessageFactory.IMessage iMessage = null;
        try {
            iMessage = blockingDeque.takeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.d("MessageQueue:get：:现在大小为",blockingDeque.size());
        return iMessage;
    }


    /** 未发送成功的插到头部 重新发送 */
    public void  insertToFirst(IMessageFactory.IMessage iMessage) {
        try {
            blockingDeque.putFirst(iMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.d("MessageQueue:insertToFirst:现在大小为",blockingDeque.size());
    }

}
