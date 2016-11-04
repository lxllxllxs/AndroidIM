package com.yiyekeji;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws IOException, Exception {
        
        Schema schema = new Schema(1, "com.yiyekeji.dao");
        addSendMessage(schema);
        new DaoGenerator().generateAll(schema, "E:\\DaoExample");
    }
    
    private static void addSendMessage(Schema schema) {
        Entity ChatMessage= schema.addEntity("ChatMessage");
        ChatMessage.addStringProperty("id");
        ChatMessage.addStringProperty("msgId");
        ChatMessage.addStringProperty("senderId");
        ChatMessage.addStringProperty("receiverId");
        ChatMessage.addStringProperty("groupId");//单聊 群聊？单聊没有此id
        ChatMessage.addStringProperty("messageType");//文字还是语音还是图片
        ChatMessage.addStringProperty("content");//非文本消息是需要根据messagetype转换
        ChatMessage.addStringProperty("date");//发送日期
        ChatMessage.addBooleanProperty("isReceiver");
    }



}