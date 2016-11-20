package com.yiyekeji.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxl on 2016/11/2.
 */
public class UserInfo implements Parcelable {
    private  String userName;
    private String userId;
    private  byte[] imgBytes;

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeByteArray(this.imgBytes);
    }

    protected UserInfo(Parcel in) {
        this.userName = in.readString();
        this.userId = in.readString();
        this.imgBytes = in.createByteArray();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
