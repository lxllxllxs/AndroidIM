package com.yiyekeji;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws IOException, Exception {
        
        Schema schema = new Schema(1, "com.yiyekeji.dao");
        addSendMessage(schema);
        addReceiMessage(schema);
        new DaoGenerator().generateAll(schema, "E:\\DaoExample");
    }
    
    private static void addSendMessage(Schema schema) {
        Entity SendMessage= schema.addEntity("SendMessage");
        SendMessage.addStringProperty("id");
        SendMessage.addStringProperty("msgId");
        SendMessage.addStringProperty("senderId");
        SendMessage.addStringProperty("receiverId");
        SendMessage.addStringProperty("groupId");//单聊 群聊？单聊没有此id
        SendMessage.addStringProperty("messageType");//文字还是语音还是图片
        SendMessage.addStringProperty("content");//非文本消息是需要根据messagetype转换
        SendMessage.addStringProperty("date");//发送日期
        SendMessage.addBooleanProperty("isReceiver");
    }


    private static void addReceiMessage(Schema schema) {

        Entity ReceiveMessage= schema.addEntity("ReceiveMessage");
        ReceiveMessage.addStringProperty("id");
        ReceiveMessage.addStringProperty("msgId");
        ReceiveMessage.addStringProperty("senderId");
        ReceiveMessage.addStringProperty("receiverId");
        ReceiveMessage.addStringProperty("groupId");//单聊 群聊？单聊没有此id
        ReceiveMessage.addStringProperty("messageType");//文字还是语音还是图片
        ReceiveMessage.addStringProperty("content");//非文本消息是需要根据messagetype转换
        ReceiveMessage.addStringProperty("date");//接收日期
        ReceiveMessage.addBooleanProperty("isReceiver");
    }
}