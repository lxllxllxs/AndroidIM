package com.yiyekeji;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws IOException, Exception {
        
        Schema schema = new Schema(1, "com.yiyekeji.dao");
        addSendMessage(schema);
        DaoGenerator daoGenerator= new DaoGenerator();
        daoGenerator .generateAll(schema, "D:\\DaoExample");
    }

    /**
     * 主键用于dao的更新操作
     * msgId用于比对服务端信息
     * @param schema
     */
    private static void addSendMessage(Schema schema) {
        Entity ChatMessage= schema.addEntity("ChatMessage");
        ChatMessage.addIdProperty();
        ChatMessage.addStringProperty("msgId");
        ChatMessage.addStringProperty("senderId");
        ChatMessage.addStringProperty("receiverId");
        ChatMessage.addStringProperty("groupId");//单聊 群聊？单聊没有此id
        ChatMessage.addStringProperty("messageType");//文字还是语音还是图片
        ChatMessage.addStringProperty("content");//非文本消息是需要根据messagetype转换
        ChatMessage.addStringProperty("date");//发送日期
        ChatMessage.addStringProperty("sendStatus");//发送状态
        ChatMessage.addStringProperty("senderName");//收件人姓名
        ChatMessage.addStringProperty("receiverName");//发件人姓名

        ChatMessage.addStringProperty("isReceiver");//判断 接收方
        ChatMessage.addStringProperty("owner");//记录信息归属

        /**
         * 记录会话列表、
         * 主要是非己，
         */
        Entity Session= schema.addEntity("Session");
        Session.addIdProperty();
        Session.addStringProperty("msgId");//最新的一条聊天信息id
        Session.addStringProperty("userId");//聊天对象
        Session.addIntProperty("unRead");//记录未读的消息数 应该用int类型
        Session.addStringProperty("owner");//记录信息归属 防止多个用户登录 信息混乱
    }


    private static void addStudent(Schema schema) {
        Entity Student = schema.addEntity("Student");
        Student.addIdProperty();
        Student.addStringProperty("name");
        Student.addStringProperty("age");
        Student.addStringProperty("sex");
        Student.addStringProperty("birthDay");//单聊 群聊？单聊没有此id
        Student.addStringProperty("studentId");//文字还是语音还是图片
        Student.addStringProperty("headImg");//非文本消息是需要根据messagetype转换
        Student.addStringProperty("nation");//发送日期
        Student.addStringProperty("phone");//发送状态
        Student.addStringProperty("note");//收件人姓名

        Entity Administrator = schema.addEntity("Administrator");
        Administrator.addIdProperty();
        Administrator.addStringProperty("name");
    }
}