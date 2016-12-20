package com.yiyekeji.db;

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
import com.yiyekeji.impl.IInformation;
import com.yiyekeji.utils.Convert;
import com.yiyekeji.utils.LogUtil;

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
        saveSession(iMessage.getId(),iMessage.getReceiverId());
    }
    /**
     * 保存接收的聊天信息
     * @param iMessage
     *
     */
    public static   void saveReceiveChatMessage(IMessageFactory.IMessage iMessage){
        cmd.insert(Convert.IMessageToChatMessage(iMessage));
        saveSession(iMessage.getId(),iMessage.getSenderId());
    }


    /**确定这里是每个用户对应有且一条ChatMessage 且要判断该session 是否归属
     * 返回所有会话表
     * @return
     */
    public static Map<UserInfo, IInformation> getAllSessionChatMap(){
        Map<UserInfo, IInformation> hashMap = new HashMap<>();
        Query query = sd.queryBuilder()
                .where(SessionDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().size()<1){
            return new HashMap<>();
        }
        for (Session session:(List<Session>)query.list()){
            UserInfo userInfo=IMApp.getUserInfo(session.getUserId());
            //将数据中的读到变量里 在点击进入聊天界面后清除
            userInfo.setUnRead(session.getUnRead()+"");
    ChatMessage chatMessage=queryChatMessageById(session.getMsgId());
    if (!hashMap.containsKey(userInfo)){
        hashMap.put(userInfo,chatMessage);
    }else {
        continue;
    }
}
return hashMap;
        }

    /**
     * 点击进去后所有信息被阅读，置了“0”
     * @param userId
     */
    public  static void upDataSessionRead(String userId){
        Query query = sd.queryBuilder()
                .where(SessionDao.Properties.UserId.eq(userId),
                        SessionDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
            if (query.list().size()<1){
                return;
            }
            Session session= (Session) query.list().get(0);
            session.setUnRead(0);
            sd.update(session);
    }

    /**增加包
     * 由调用决定 userId,当自己为接收方时，userId为发送方
     * @param msgId
     * @param userId
     */
    public  static void saveSession(String msgId,String userId){
        Query query = sd.queryBuilder()
                .where(SessionDao.Properties.UserId.eq(userId),
                        SessionDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().isEmpty()) {
            sd.insert(Convert.createSessionFromMsg(msgId,userId));
        }else {
            Session session= (Session) query.list().get(0);
            //未读消息数 如果是非正是聊天对象或没有聊天对象 则喜加1
            if (IMApp.otherSide == null||(!IMApp.otherSide.getUserId().equals(userId))) {
                session.setUnRead(session.getUnRead() + 1);
            } else {
            }
            session.setMsgId(msgId);
            sd.update(session);
        }
    }

/*

    */
/**《这条可以被废除》
     * 专为离线消息设置
     * @param msgId 最新的一条消息id
     * @param userId 聊天对象id
     * @param unRead 未读信息数
     *//*

    public  static void upDataUnReceiSession(String msgId,String userId,String unRead){
        Query query = sd.queryBuilder()
                .where(SessionDao.Properties.UserId.eq(userId),
                        SessionDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().isEmpty()) {
            sd.insert(Convert.createSessionFromMsg(msgId,userId,unRead));
        }else {
            Session session= (Session) query.list().get(0);
            session.setMsgId(msgId);
            session.setUnRead(unRead);
            sd.update(session);
        }
    }
*/

    /**
     * 返回唯一ChatMessage
     * @param msgId
     * @return
     */
    public static ChatMessage queryChatMessageById(String msgId){
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.MsgId.eq(msgId))
                .build();
        return (ChatMessage) query.list().get(0);
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
    public static ArrayList<ChatMessage> searchAllMsg(String receiverId) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .whereOr(ChatMessageDao.Properties.ReceiverId.eq(receiverId),
                        ChatMessageDao.Properties.SenderId.eq(receiverId))
                .where(ChatMessageDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> sendMessageList = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchSendMsg",sendMessageList.size());
        return sendMessageList;
    }

/*
    *//**
     * 查询接收聊天信息表
     * @return
     *//*
    public static ArrayList<ChatMessage> searchReceivedMsg() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = cmd.queryBuilder()
                .where(ChatMessageDao.Properties.ReceiverId.eq(IMApp.userInfo.getUserId()),
                        ChatMessageDao.Properties.Owner.eq(IMApp.userInfo.getUserId()))
                .build();
        if (query.list().isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<ChatMessage> receiveMessages = (ArrayList<ChatMessage>)query.list();
        LogUtil.d("searchReceivedMsg",receiveMessages.size());
        return receiveMessages;
    }*/
}
