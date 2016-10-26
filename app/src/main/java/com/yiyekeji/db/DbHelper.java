package com.yiyekeji.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yiyekeji.utils.DbUtils;


/**
 * Created by zhouyaozhong on 15/12/2.
 * 
 * modify 2016-04-08 添加手势密码表  by Kwong
 */
public class DbHelper extends SQLiteOpenHelper{
    //数据库版本号
    private static final int DB_VERSION = 9;

    //崩溃日志表
    public static final String CRASH_TABLE_NAME = "t_crash";
    //聊天记录表
    public static final String CHAT_TABLE_NAME = "t_chat";
    //手势密码表
    public static final String GESTUREPSW_TABLE_NAME = "t_gesturepsw";


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, DB_VERSION);
    }


    public DbHelper(Context context, String name, int version) {
        super(context, name, null, DB_VERSION);
    }


    public DbHelper(Context context, String name) {
        super(context, name, null, DB_VERSION);
    }


    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createCrashTable(sqLiteDatabase);
        createChatTable(sqLiteDatabase);
        createGesturePswTable(sqLiteDatabase) ;
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        upgradeCrashTable(sqLiteDatabase);
        upgradeChatTable(sqLiteDatabase);
        upgradeGesturePswTable(sqLiteDatabase);
    }


    /**
     * CrashTable，保存异常崩溃信息
     * */
    private void createCrashTable(SQLiteDatabase db) {
        String execString = "create table " + CRASH_TABLE_NAME + " (Id integer primary key autoincrement not null,";
        execString += "CrashData varchar(200),";
        execString += "CreateDate varchar(50),";
        execString += "IsUpload integer";
        execString += ");";
        db.execSQL(execString);
    }

    private void upgradeCrashTable(SQLiteDatabase db) {
    	if(!isTableExist(db, CRASH_TABLE_NAME)) {
    		createCrashTable(db);
    	}
    }
    
    /**
     * GesturePswTable，保存手势密码信息
     * */
    private void createGesturePswTable(SQLiteDatabase db) {
        String execString = "create table " + GESTUREPSW_TABLE_NAME + " (Id integer primary key autoincrement not null,";
        execString += "_psw varchar(32),";
        execString += "_username varchar(50)";
        execString += ");";
        db.execSQL(execString);
    }

    private void upgradeGesturePswTable(SQLiteDatabase db) {
    	if(!isTableExist(db, GESTUREPSW_TABLE_NAME)) {
    		createGesturePswTable(db);
    	}
    }

    
    /**
     * ChatTable，保存聊天记录
     * */
    private void createChatTable(SQLiteDatabase db) {
    	String execString = "create table " + CHAT_TABLE_NAME + " (Id integer primary key autoincrement not null,";
    	execString += "MainType integer,";           //1为发送，0为接收
    	execString += "State integer,";          //-1为发送失败，0为正在发送，1为发送成功
    	execString += "IsRead integer,"; 
    	execString += "icon varchar(20),";
    	execString += "ChatType varchar(10),";   //“Single”, “Group”
    	execString += "Kind varchar(5),";	     //“0”普通文字,“1”图片, “2”发红包, “3”领红包, “4”语音
//    	execString += "Sender varchar(30),";     //如果ChatType为“Single”，为用户名；如果ChatType为“Group”，为群名；
//    	execString += "Receiver varchar(30),";   //如果ChatType为“Single”，为用户名；如果ChatType为“Group”，为群名；
    	execString += "OneSelf varchar(30),"; 
    	execString += "OneSelfId varchar(50),";
    	execString += "Other varchar(30),";      //当群聊时候，这字段为群名
    	execString += "OtherId varchar(50),";
//    	execString += "GroupName varchar(30),";
//    	execString += "GroupId varchar(30),";
    	execString += "GroupSender varchar(30),";    //当群聊时候，这字段为群里发该消息的用户名
    	execString += "GroupSenderId varchar(30),";
    	execString += "Date varchar(20),";
    	execString += "MessageID varchar(30),";
    	execString += "HBID varchar(30),";
    	execString += "GetHBFlag integer,";		//当类型为Inform时，这字段表示是否同意添加对方好友;当为语音消息时，表示录音秒数
    	execString += "GetMoney varchar(10),"; 
    	execString += "Content varchar(200),";   //当消息为普通文字，则为消息内容；图片消息和语音，则为本地保存或缓存的地址；其他消息则保持想对应的格式内容
    	execString += "HeadImagePath varchar(50),"; 
    	execString += "FileName varchar(30)";
    	execString += ");";
        db.execSQL(execString);
    }
    
    
    private void upgradeChatTable(SQLiteDatabase db) {
    	if(!isTableExist(db, CHAT_TABLE_NAME)) {
    		createChatTable(db);
    	}else {
    		addTableColumn(db, CHAT_TABLE_NAME, "IsRead", "integer");
    		addTableColumn(db, CHAT_TABLE_NAME, "OneSelfId", "varchar(50)");
    		addTableColumn(db, CHAT_TABLE_NAME, "OtherId", "varchar(50)");
    		addTableColumn(db, CHAT_TABLE_NAME, "GroupId", "varchar(30)");
    		addTableColumn(db, CHAT_TABLE_NAME, "GetHBFlag", "integer");
    		addTableColumn(db, CHAT_TABLE_NAME, "GetMoney", "varchar(10)");
    		addTableColumn(db, CHAT_TABLE_NAME, "HBID", "varchar(30)");
    		addTableColumn(db, CHAT_TABLE_NAME, "HeadImagePath", "varchar(50)");
    	}
    }
    

    /*************************其他通用私有方法分界线****************************/
    
    private boolean isTableExist(SQLiteDatabase db, String tableName) {
        int tableCount = 0;
        Cursor cursor = null;
        cursor = db.query("sqlite_master", new String[] { "COUNT(*) AS tableCount" }, "type=? and name=?", new String[] {"table", tableName}, null, null, null);
        while(cursor.moveToNext()) {
            tableCount = cursor.getInt(cursor.getColumnIndex("tableCount"));
        }
        DbUtils.closeCursor(cursor);
        if(tableCount == 0) {
        	return false;
        }else {
        	return true;
        }
    }
    
    
    private void addTableColumn(SQLiteDatabase db, String tableName, String column, String columnType) {
        Cursor cursor = db.rawQuery("select sql from sqlite_master where name is ?",new String[]{tableName});
        if(cursor.getCount()>0){
            cursor.moveToNext();
            String sql =cursor.getString(0).toString();
            if(!sql.contains(column)){
                String exe = "alter table "+tableName+" add "+column+" "+columnType;
                db.execSQL(exe);
            }
        }
    }

}
