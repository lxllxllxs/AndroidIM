package com.yiyekeji.utils;

import com.yiyekeji.bean.IMessageFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lxl on 2016/11/2.
 */
public class MessageQueue {


    BlockingDeque<IMessageFactory.IMessage> blockingDeque=new LinkedBlockingDeque<>();
    /** 放鸡蛋 */
    public void putEgg(IMessageFactory.IMessage iMessage) {
        try {
            blockingDeque.put(iMessage);// 向盘子末尾放一个鸡蛋，如果盘子满了，当前线程阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.d("MessageQueue:putEgg",blockingDeque.size());
    }

    /** 取鸡蛋 */
    public IMessageFactory.IMessage getEgg() {
        IMessageFactory.IMessage iMessage = null;
        try {
            iMessage = blockingDeque.take();// 从盘子开始取一个鸡蛋，如果盘子空了，当前线程阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.d("MessageQueue:getEgg",blockingDeque.size()+iMessage.toString());
        return iMessage;
    }



}
