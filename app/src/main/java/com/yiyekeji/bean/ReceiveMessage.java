package com.yiyekeji.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lxl on 2016/10/26.
 */
public class ReceiveMessage implements Parcelable {
    private ArrayList<User> users;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.users);
    }

    public ReceiveMessage() {
    }

    @Override
    public String toString() {
        return "ReceiveMessage{" +
                "users=" + users +
                '}';
    }

    protected ReceiveMessage(Parcel in) {
        this.users = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<ReceiveMessage> CREATOR = new Parcelable.Creator<ReceiveMessage>() {
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
