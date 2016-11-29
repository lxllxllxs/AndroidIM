package com.yiyekeji.Event;

import com.yiyekeji.dao.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用作通知信息列表里
 * Created by Administrator on 2016/11/1.
 */
public class UnReceiveEvent {
    private ChatMessage  chatMessage;
    HashMap<String, ArrayList<ChatMessage>> hashMap;

    public static HashMap<String, ArrayList<ChatMessage>> chatMap = new HashMap<>();
    public static void setChatMessageMessage(ChatMessage chatMessage) {
        if (!chatMap.containsKey(chatMessage.getSenderId())){
            chatMap.put(chatMessage.getSenderId(),new ArrayList<ChatMessage>());
        }
        chatMap.get(chatMessage.getSenderId()).add(chatMessage);
    }
}
