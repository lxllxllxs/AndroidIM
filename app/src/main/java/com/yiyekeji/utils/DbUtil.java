package com.yiyekeji.utils;

import android.database.sqlite.SQLiteDatabase;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.dao.ChatMessageDao;
import com.yiyekeji.dao.DaoMaster;
import com.yiyekeji.dao.DaoSession;

import java.util.ArrayList;

import de.greenrobot.dao.query.Query;

/**
 * Created by lxl on 2016/11/4.
 */
public class DbUtil {
    public static IMessageFactory.IMessage.User user;
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private  static ChatMessageDao cmd;


     static  {
         LogUtil.d("DbUtil","数据库操作工具类正在初始化");
         helper = new DaoMaster.DevOpenHelper(IMApp.getContext(), "im_db", null);
         db = helper.getWritableDatabase();
         daoMaster = new DaoMaster(db);
         daoSession = daoMaster.newSession();


         cmd = daoSession.getChatMessageDao();  //拿到这么个工具dao
         LogUtil.d("DbUtil","数据库操作工具类初始化完成");
    }

    /**
     * 保存发送的聊天信息
     * @param iMessage
     */
    public static   void saveSendMessage(IMessageFactory.IMessage iMessage){

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMsgId(iMessage.getId());
        chatMessage.setSenderId(iMessage.getSenderId());
        chatMessage.setReceiverId(iMessage.getReceiverId());
        chatMessage.setContent(iMessage.getContent());
        chatMessage.setDate(iMessage.getDate());
        cmd.insert(chatMessage);
    }

    public static   void saveReceiveChatMessage(IMessageFactory.IMessage iMessage){

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(iMessage.getSenderId());
        chatMessage.setMsgId(iMessage.getId());
        chatMessage.setContent(iMessage.getContent());
        chatMessage.setDate(DateUtil.getTimeString());
        chatMessage.setReceiverId(iMessage.getReceiverId());//
        cmd.insert(chatMessage);
    }

    /**
     * 查询发送聊天信息表
     * @param receiverId 接收人
     * @return
     */
    public static ArrayList<ChatMessage> searchSendMsg(String receiverId) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.ReceiverId.eq(receiverId))
                .orderAsc(ChatMessageDao.Properties.Date)
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> sendMessageList = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchSendMsg",sendMessageList.size());
        return sendMessageList;
    }

    /**
     * 查询接收聊天信息表
     * @return
     */
    public static ArrayList<ChatMessage> searchReceivedMsg() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.ReceiverId.eq(IMApp.userInfo.getUserId()))
                .orderAsc(ChatMessageDao.Properties.Date)
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> receiveMessages = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchReceivedMsg",receiveMessages.size());
        return receiveMessages;
    }
}
