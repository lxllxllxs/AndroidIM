package com.yiyekeji.Event;

import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */
public class LinkManEvent {
    private IMessageFactory.IMessage iMessage;

    private ArrayList<UserInfo> userInfoList;
    public ArrayList<UserInfo> getLinkManList() {
        userInfoList = new ArrayList<>();
        for (IMessageFactory.IMessage.User user : iMessage.getUserList()) {
            if (user.getUserId()==null){
                continue;
            }
            UserInfo userInfo=new UserInfo();
            userInfo.setUserId(user.getUserId());
            userInfo.setImgUrl(user.getImgUrl());
            userInfo.setUserName(user.getUsername());
            userInfoList.add(userInfo);
        }
        return userInfoList;
    }

    public void setiMessage(IMessageFactory.IMessage iMessage) {
        this.iMessage = iMessage;
    }
}
