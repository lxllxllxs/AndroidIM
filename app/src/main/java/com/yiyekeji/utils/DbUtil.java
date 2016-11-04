package com.yiyekeji.utils;

import android.database.sqlite.SQLiteDatabase;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.dao.ChatMsgAbstract;
import com.yiyekeji.dao.DaoMaster;
import com.yiyekeji.dao.DaoSession;
import com.yiyekeji.dao.ReceiveMessage;
import com.yiyekeji.dao.ReceiveMessageDao;
import com.yiyekeji.dao.SendMessage;
import com.yiyekeji.dao.SendMessageDao;

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
    private  static SendMessageDao smd;
    private  static ReceiveMessageDao rmd;


     static  {
         LogUtil.d("DbUtil","数据库操作工具类正在初始化");
         helper = new DaoMaster.DevOpenHelper(IMApp.getContext(), "im_db", null);
         db = helper.getWritableDatabase();
         daoMaster = new DaoMaster(db);
         daoSession = daoMaster.newSession();


         smd = daoSession.getSendMessageDao();  //拿到这么个工具dao
         rmd = daoSession.getReceiveMessageDao();  //拿到这么个工具dao
         LogUtil.d("DbUtil","数据库操作工具类初始化完成");
    }

    /**
     * 保存发送的聊天信息
     * @param iMessage
     */
    public static   void saveSendMessage(IMessageFactory.IMessage iMessage){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setMsgId(iMessage.getId());
        sendMessage.setSenderId(iMessage.getSenderId());
        sendMessage.setReceiverId(iMessage.getReceiverId());
        sendMessage.setContent(iMessage.getContent());
        sendMessage.setDate(iMessage.getDate());
        sendMessage.setIsReceiver(false);
        smd.insert(sendMessage);
    }

    public static   void saveReceiveChatMessage(IMessageFactory.IMessage iMessage){

        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setSenderId(iMessage.getSenderId());
        receiveMessage.setMsgId(iMessage.getId());
        receiveMessage.setContent(iMessage.getContent());
        receiveMessage.setDate(iMessage.getDate());
        receiveMessage.setReceiverId(iMessage.getReceiverId());//
        receiveMessage.setIsReceiver(true);
        rmd.insert(receiveMessage);
    }

    /**
     * 查询发送聊天信息表
     * @param receiverId 接收人
     * @return
     */
    public static ArrayList<ChatMsgAbstract> searchSendMsg(String receiverId) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = smd.queryBuilder()
                .where(SendMessageDao.Properties.ReceiverId.eq(receiverId))
                .orderAsc(SendMessageDao.Properties.Date)
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMsgAbstract> sendMessageList = (ArrayList<ChatMsgAbstract>)query.list();
        LogUtil.d("searchSendMsg",sendMessageList.size());
        return sendMessageList;
    }

    /**
     * 查询接收聊天信息表
     * @return
     */
    public static ArrayList<ChatMsgAbstract> searchReceivedMsg() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = rmd.queryBuilder()
                .where(ReceiveMessageDao.Properties.ReceiverId.eq(IMApp.userInfo.getUserId()))
                .orderAsc(ReceiveMessageDao.Properties.Date)
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMsgAbstract> receiveMessages = (ArrayList<ChatMsgAbstract>)query.list();
        LogUtil.d("searchReceivedMsg",receiveMessages.size());
        return receiveMessages;
    }
}
