package com.yiyekeji.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SESSION".
 */
public class Session {

    private Long id;
    private String msgId;
    private String userId;
    private Integer unRead;
    private String owner;

    public Session() {
    }

    public Session(Long id) {
        this.id = id;
    }

    public Session(Long id, String msgId, String userId, Integer unRead, String owner) {
        this.id = id;
        this.msgId = msgId;
        this.userId = userId;
        this.unRead = unRead;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUnRead() {
        return unRead;
    }

    public void setUnRead(Integer unRead) {
        this.unRead = unRead;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
