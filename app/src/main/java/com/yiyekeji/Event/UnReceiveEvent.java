package com.yiyekeji.Event;

import com.yiyekeji.dao.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用作通知信息列表里
 * Created by Administrator on 2016/11/1.
 */
public class UnReceiveEvent {
    HashMap<String, ArrayList<ChatMessage>> hashMap;
    String a="hello";
    public static HashMap<String, ArrayList<ChatMessage>> chatMap = new HashMap<>();

    /**
     * end作为结束符
     * @param chatMessage
     */
    public static void setChatMessageMessage(ChatMessage chatMessage) {
        if (chatMessage.getSenderId().equals("end")){
            return;
        }
        if (!chatMap.containsKey(chatMessage.getSenderId())){
            chatMap.put(chatMessage.getSenderId(),new ArrayList<ChatMessage>());
        }
        chatMap.get(chatMessage.getSenderId()).add(chatMessage);
    }

    public HashMap<String, ArrayList<ChatMessage>> getChatMap() {
        hashMap = new HashMap<>();
        assert chatMap != null;
        hashMap.putAll(UnReceiveEvent.chatMap);
        return hashMap;
    }
}
