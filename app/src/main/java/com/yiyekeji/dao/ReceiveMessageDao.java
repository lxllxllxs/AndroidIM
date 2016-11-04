package com.yiyekeji.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.yiyekeji.dao.ReceiveMessage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECEIVE_MESSAGE".
*/
public class ReceiveMessageDao extends AbstractDao<ReceiveMessage, Void> {

    public static final String TABLENAME = "RECEIVE_MESSAGE";

    /**
     * Properties of entity ReceiveMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property MsgId = new Property(1, String.class, "msgId", false, "MSG_ID");
        public final static Property SenderId = new Property(2, String.class, "senderId", false, "SENDER_ID");
        public final static Property ReceiverId = new Property(3, String.class, "receiverId", false, "RECEIVER_ID");
        public final static Property GroupId = new Property(4, String.class, "groupId", false, "GROUP_ID");
        public final static Property MessageType = new Property(5, String.class, "messageType", false, "MESSAGE_TYPE");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Date = new Property(7, String.class, "date", false, "DATE");
        public final static Property IsReceiver = new Property(8, Boolean.class, "isReceiver", false, "IS_RECEIVER");
    };


    public ReceiveMessageDao(DaoConfig config) {
        super(config);
    }
    
    public ReceiveMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECEIVE_MESSAGE\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"MSG_ID\" TEXT," + // 1: msgId
                "\"SENDER_ID\" TEXT," + // 2: senderId
                "\"RECEIVER_ID\" TEXT," + // 3: receiverId
                "\"GROUP_ID\" TEXT," + // 4: groupId
                "\"MESSAGE_TYPE\" TEXT," + // 5: messageType
                "\"CONTENT\" TEXT," + // 6: content
                "\"DATE\" TEXT," + // 7: date
                "\"IS_RECEIVER\" INTEGER);"); // 8: isReceiver
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECEIVE_MESSAGE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ReceiveMessage entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String msgId = entity.getMsgId();
        if (msgId != null) {
            stmt.bindString(2, msgId);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(3, senderId);
        }
 
        String receiverId = entity.getReceiverId();
        if (receiverId != null) {
            stmt.bindString(4, receiverId);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(5, groupId);
        }
 
        String messageType = entity.getMessageType();
        if (messageType != null) {
            stmt.bindString(6, messageType);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(8, date);
        }
 
        Boolean isReceiver = entity.getIsReceiver();
        if (isReceiver != null) {
            stmt.bindLong(9, isReceiver ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public ReceiveMessage readEntity(Cursor cursor, int offset) {
        ReceiveMessage entity = new ReceiveMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // msgId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // senderId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // receiverId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // groupId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // messageType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // date
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0 // isReceiver
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ReceiveMessage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setMsgId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSenderId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setReceiverId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGroupId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMessageType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsReceiver(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(ReceiveMessage entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(ReceiveMessage entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
