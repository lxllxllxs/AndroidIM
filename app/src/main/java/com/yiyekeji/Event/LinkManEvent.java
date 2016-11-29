package com.yiyekeji.Event;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.bean.UserInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/31.
 */
public class LinkManEvent {
    private IMessageFactory.IMessage iMessage;

    private ArrayList<UserInfo> userInfoList = new ArrayList<>();
    public ArrayList<UserInfo> getLinkManList() {
        return userInfoList;
    }

    /**
     * 一条打包过来
     * @param iMessage
     */
    public void addLinkMan(IMessageFactory.IMessage iMessage) {
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
        //不要直接赋值引用
        IMApp.linkManList.addAll(userInfoList);
    }
}
