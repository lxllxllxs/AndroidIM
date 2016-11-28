package com.yiyekeji.Event;

import com.yiyekeji.dao.ChatMessage;

/**
 * 用作通知信息列表里
 * Created by Administrator on 2016/11/1.
 */
public class UnReceiveEvent {
    private ChatMessage  chatMessage;

    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessageMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
}
