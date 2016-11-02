package com.yiyekeji;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws IOException, Exception {
        
        Schema schema = new Schema(1, "com.yiyekeji.dao");
          
        addNote(schema);  
  
        new DaoGenerator().generateAll(schema, "E:\\DaoExample");
        
    }
    
    private static void addNote(Schema schema) {  
        
        Entity SaveMessage= schema.addEntity("SaveMessage");
        SaveMessage.addStringProperty("id");
        SaveMessage.addStringProperty("senderId");
        SaveMessage.addStringProperty("receiverId");
        SaveMessage.addBooleanProperty("isReceiver");
        SaveMessage.addStringProperty("groupId");//单聊 群聊？单聊没有此id
        SaveMessage.addStringProperty("messageType");//文字还是语音还是图片
        SaveMessage.addStringProperty("content");//非文本消息是需要根据messagetype转换
        SaveMessage.addStringProperty("date");//发送日期
    }
}