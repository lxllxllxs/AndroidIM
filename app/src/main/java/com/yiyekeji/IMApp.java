package com.yiyekeji;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.DaoMaster;
import com.yiyekeji.dao.DaoSession;
import com.yiyekeji.dao.SaveMessage;
import com.yiyekeji.dao.SaveMessageDao;
import com.yiyekeji.utils.LogUtil;

import java.util.ArrayList;

import de.greenrobot.dao.query.Query;

/**
 * Created by lxl on 2016/10/26.
 */
public class IMApp extends Application {
    public static  boolean isLogin;
    public static IMessageFactory.IMessage.User user;
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private  static SaveMessageDao smd;

    public static UserInfo userInfo = new UserInfo();

    @Override
    public void onCreate() {
        super.onCreate();
       helper = new DaoMaster.DevOpenHelper(this,"im_db",null);
       db = helper.getWritableDatabase();
       daoMaster = new DaoMaster(db);
       daoSession = daoMaster.newSession();
    }


    public static   void saveChatMessage(IMessageFactory.IMessage iMessage){
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }
        smd = daoSession.getSaveMessageDao();  //拿到这么个工具dao
        if (smd==null){
            LogUtil.d("smd","iMesss is null");
        }
        SaveMessage saveMessage = new SaveMessage();
        saveMessage.setSenderId(iMessage.getSenderId());
        saveMessage.setId(iMessage.getId());
        saveMessage.setContent(iMessage.getContent());
        saveMessage.setDate(iMessage.getDate());
        saveMessage.setReceiverId(iMessage.getReceiverId());
        saveMessage.setIsReceiver(false);//肯定不是接收
        smd.insert(saveMessage);
    }

    public static   void saveReceiveChatMessage(IMessageFactory.IMessage iMessage){
        if (daoSession == null) {
            daoSession = daoMaster.newSession();
        }
        smd = daoSession.getSaveMessageDao();  //拿到这么个工具dao
        if (smd==null){
            LogUtil.d("smd","iMesss is null");
        }
        SaveMessage saveMessage = new SaveMessage();
        saveMessage.setSenderId(iMessage.getSenderId());
        saveMessage.setId(iMessage.getId());
        saveMessage.setContent(iMessage.getContent());
        saveMessage.setDate(iMessage.getDate());
        saveMessage.setReceiverId(iMessage.getReceiverId());
        saveMessage.setIsReceiver(true);
        smd.insert(saveMessage);
    }

    public static ArrayList<SaveMessage> search() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = smd.queryBuilder()
                .where(SaveMessageDao.Properties.IsReceiver.eq(true))
                .orderAsc(SaveMessageDao.Properties.Date)
                .build();

      ArrayList<SaveMessage> saveMessages = (ArrayList<SaveMessage>) query.list();
        LogUtil.d("search",saveMessages.size());
        return saveMessages;
    }


}
