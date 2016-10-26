package com.yiyekeji.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lxl on 2016/10/26.
 */
public class ReceiveMessage implements Parcelable {

    private ArrayList<User> users;
    private  String content;
    private  String sender;
    private  String receiver;
    private  String date;
    private  String senderId;

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ReceiveMessage{" +
                "users=" + users +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date='" + date + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverID='" + receiverID + '\'' +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    private  String receiverID;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ReceiveMessage() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.users);
        dest.writeString(this.content);
        dest.writeString(this.sender);
        dest.writeString(this.receiver);
        dest.writeString(this.date);
        dest.writeString(this.senderId);
        dest.writeString(this.receiverID);
    }

    protected ReceiveMessage(Parcel in) {
        this.users = in.createTypedArrayList(User.CREATOR);
        this.content = in.readString();
        this.sender = in.readString();
        this.receiver = in.readString();
        this.date = in.readString();
        this.senderId = in.readString();
        this.receiverID = in.readString();
    }

    public static final Creator<ReceiveMessage> CREATOR = new Creator<ReceiveMessage>() {
        @Override
        public ReceiveMessage createFromParcel(Parcel source) {
            return new ReceiveMessage(source);
        }

        @Override
        public ReceiveMessage[] newArray(int size) {
            return new ReceiveMessage[size];
        }
    };
}
