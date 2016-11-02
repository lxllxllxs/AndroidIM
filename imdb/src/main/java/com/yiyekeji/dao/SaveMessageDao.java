package com.yiyekeji.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.yiyekeji.dao.SaveMessage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SAVE_MESSAGE".
*/
public class SaveMessageDao extends AbstractDao<SaveMessage, Void> {

    public static final String TABLENAME = "SAVE_MESSAGE";

    /**
     * Properties of entity SaveMessage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", false, "ID");
        public final static Property SenderId = new Property(1, String.class, "senderId", false, "SENDER_ID");
        public final static Property ReceiverId = new Property(2, String.class, "receiverId", false, "RECEIVER_ID");
        public final static Property IsReceiver = new Property(3, Boolean.class, "isReceiver", false, "IS_RECEIVER");
        public final static Property GroupId = new Property(4, String.class, "groupId", false, "GROUP_ID");
        public final static Property MessageType = new Property(5, String.class, "messageType", false, "MESSAGE_TYPE");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Date = new Property(7, String.class, "date", false, "DATE");
    };


    public SaveMessageDao(DaoConfig config) {
        super(config);
    }
    
    public SaveMessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SAVE_MESSAGE\" (" + //
                "\"ID\" TEXT," + // 0: id
                "\"SENDER_ID\" TEXT," + // 1: senderId
                "\"RECEIVER_ID\" TEXT," + // 2: receiverId
                "\"IS_RECEIVER\" INTEGER," + // 3: isReceiver
                "\"GROUP_ID\" TEXT," + // 4: groupId
                "\"MESSAGE_TYPE\" TEXT," + // 5: messageType
                "\"CONTENT\" TEXT," + // 6: content
                "\"DATE\" TEXT);"); // 7: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SAVE_MESSAGE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SaveMessage entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(2, senderId);
        }
 
        String receiverId = entity.getReceiverId();
        if (receiverId != null) {
            stmt.bindString(3, receiverId);
        }
 
        Boolean isReceiver = entity.getIsReceiver();
        if (isReceiver != null) {
            stmt.bindLong(4, isReceiver ? 1L: 0L);
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
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SaveMessage readEntity(Cursor cursor, int offset) {
        SaveMessage entity = new SaveMessage( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // senderId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // receiverId
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // isReceiver
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // groupId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // messageType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SaveMessage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSenderId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setReceiverId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIsReceiver(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setGroupId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMessageType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SaveMessage entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SaveMessage entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
