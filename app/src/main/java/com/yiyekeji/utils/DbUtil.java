package com.yiyekeji.utils;

import android.database.sqlite.SQLiteDatabase;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.dao.ChatMessageDao;
import com.yiyekeji.dao.DaoMaster;
import com.yiyekeji.dao.DaoSession;
import com.yiyekeji.dao.Session;
import com.yiyekeji.dao.SessionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static SessionDao sd;

     static  {
         LogUtil.d("DbUtil","数据库操作工具类正在初始化");
         helper = new DaoMaster.DevOpenHelper(IMApp.getContext(), "im_db", null);
         db = helper.getWritableDatabase();
         daoMaster = new DaoMaster(db);
         daoSession = daoMaster.newSession();
         cmd = daoSession.getChatMessageDao();  //拿到这么个工具dao
         sd = daoSession.getSessionDao();
         LogUtil.d("DbUtil","数据库操作工具类初始化完成");
    }

    /**
     * 保存发送的聊天信息
     * @param iMessage
     */
    public static   void saveSendMessage(IMessageFactory.IMessage iMessage){
        cmd.insert(Convert.IMessageToChatMessage(iMessage));
        saveSession(iMessage.getReceiverId(), iMessage.getId());
    }
    /**
     * 保存接收的聊天信息
     * @param iMessage
     */
    public static   void saveReceiveChatMessage(IMessageFactory.IMessage iMessage){
        cmd.insert(Convert.IMessageToChatMessage(iMessage));
        saveSession(iMessage.getSenderId(), iMessage.getId());
    }

    /**
     * 由调用决定 userId,当自己为接收方时，userId为发送方
     * @param msgId
     * @param userId
     */
    public  static void saveSession(String msgId,String userId){
        Query query = sd.queryBuilder()
                .where(SessionDao.Properties.UserId.eq(msgId))
                .build();
        if (query.list().isEmpty()) {
            sd.insert(Convert.createSessionFromMsg(msgId,userId));
        }else {
            Session session= (Session) query.list().get(0);
            session.setMsgId(msgId);
            sd.update(session);
        }
    }

    /**
     *更改为发送成功（发送到服务器端）
     */
    public static void upDataSendChatMsg(String msgId){
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.MsgId.eq(msgId))
                .build();
        if (query.list().isEmpty()) {
            return;
        }
        ChatMessage cm=((ArrayList<ChatMessage>)query.list()).get(0);
        cm.setSendStatus("1");
        cmd.update(cm);
    }

    /**
     * 根据msgId查询 判断有无重复值，
     * @return
     */
    public static boolean isHaveSameMsg(String id) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.MsgId.eq(id))
                .build();
        if (query.list().isEmpty()) {
            return true;
        }
        return false;
    }



    /**
     * 查询发送聊天信息表
     * @param receiverId 接收人
     * @return
     *
     * 两种情况 ：在会话列表时receiverId为自己
     * 在聊天主界面时receiveId 为对方
     */
    public static ArrayList<ChatMessage> searchAllSendMsg(String receiverId) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.ReceiverId.eq(receiverId))
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> sendMessageList = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchSendMsg",sendMessageList.size());
        return sendMessageList;
    }

    /**
     *接收者 或发送方
     * 对聊天信息进行归类，userInfo:最新的ChatMessage
     * @param receiverId
     * @return
     */
    public static Map<UserInfo,ChatMessage> getSessionMap(String receiverId){
        //原始数据 这里主要考虑不同账号保持独立信息
        List<ChatMessage> chatMessageList=searchAllSendMsg(receiverId);
        Map<String, List<ChatMessage>> hashMap = new HashMap<>();
        for (ChatMessage chatMessage : chatMessageList) {
        }
        return null;
    }


    /**
     * 查询接收聊天信息表
     * @return
     */
    public static ArrayList<ChatMessage> searchReceivedMsg() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.ReceiverId.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> receiveMessages = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchReceivedMsg",receiveMessages.size());
        return receiveMessages;
    }
}
