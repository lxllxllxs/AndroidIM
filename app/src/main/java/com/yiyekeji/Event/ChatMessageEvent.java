package com.yiyekeji.Event;

import com.yiyekeji.dao.ChatMessage;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ChatMessageEvent {
    public ChatMessage getiMessage() {
        return cm;
    }

    public void setiMessage(ChatMessage cm) {
        this.cm = cm;
    }

    ChatMessage cm;
}
