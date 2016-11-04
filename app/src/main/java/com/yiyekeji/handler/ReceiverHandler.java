package com.yiyekeji.handler;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.Event.LinkManEvent;
import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.utils.DbUtil;
import com.yiyekeji.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ReceiverHandler {

    /**************************Receive***************************************/
    /**
     * 主：0 系统 1聊天
     *
     * @param iMessage
     * @return
     */
    public static Object receive(IMessageFactory.IMessage iMessage){
        if (iMessage.getMainType().equals("0")){
            switch (iMessage.getSubType()){
                case "0":
                    loginVerfity(iMessage);
                    break;
                case "1":
                    LogUtil.d("ReceiverHandler"," 接收好友列表");
                    LinkManEvent linkManEvent=new LinkManEvent();
                    linkManEvent.setiMessage(iMessage);
                    EventBus.getDefault().post(linkManEvent);
                    break;
                case "2"://接受离线消息（离线消息？）    //A保存聊天类信息
                    LogUtil.d("ReceiverHandler","接收离线消息");
                    DbUtil.saveReceiveChatMessage(iMessage);
                    ChatMessageEvent event=new ChatMessageEvent();
                    event.setiMessage(iMessage);
                    EventBus.getDefault().post(event);
                    break;
            }
        }else if(iMessage.getMainType().equals("1")){
            //A保存聊天类信息
            DbUtil.saveReceiveChatMessage(iMessage);
            switch (iMessage.getSubType()){
                case "0":

                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
            }
        }
        return null;
    }

    /**
     * 登录验证
     * 发送通知进入下一界面
     * 记录用户的相关信息
     * 用户的id将用于生产IMeeage的senderId;
     * @param iMessage
     */
    private static void loginVerfity(IMessageFactory.IMessage iMessage) {
        LogUtil.d("ReceiverHandler","登录验证");
        LoginEvent loginEvent=new LoginEvent();
        if (iMessage.getResult().equals("1")){
            loginEvent.setSuccess(true);
            //记录用户的信息
            IMApp.isLogin=true;
            IMApp.userInfo.setUserId(iMessage.getUser(0).getUserId());
            IMApp.userInfo.setUserName(iMessage.getUser(0).getUsername());
        }else {
            loginEvent.setSuccess(false);
        }
        EventBus.getDefault().post(loginEvent);
    }


}
