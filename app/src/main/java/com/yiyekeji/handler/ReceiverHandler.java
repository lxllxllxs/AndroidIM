package com.yiyekeji.handler;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.Event.LinkManEvent;
import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.Event.UnReceiveEvent;
import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.utils.Convert;
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
                    linkManEvent.addLinkMan(iMessage);
                    EventBus.getDefault().post(linkManEvent);
                    break;
                case "2"://接受离线消息（离线消息？）
                    LogUtil.d("ReceiverHandler","接收离线消息");
                    //这里应该用UnReceiveEvent 只要通知界面刷新就好
                /*    LogUtil.d("ReceiverHandler", iMessage.getSenderId());
                    if (!iMessage.getSenderId().equals("end")) {
                        UnReceiveEvent.setChatMessageMessage(Convert.IMessageToChatMessage(iMessage));
                        DbUtil.saveReceiveChatMessage(iMessage);
                        return null;
                    }
                    UnReceiveEvent.addUnReceiMessageToSession();
                    UnReceiveEvent event=new UnReceiveEvent();
                    EventBus.getDefault().post(event);*/
                    break;
            }
        }else if(iMessage.getMainType().equals("1")){
            //A保存聊天类信息
            DbUtil.saveReceiveChatMessage(iMessage);
            //接收成功信息反馈
            WebSocketService.sendHaveReceivdeMsgId(iMessage.getId());
            switch (iMessage.getSubType()){
                case "0":
                    /**
                     * otherSide不为空 则在聊天界面 直接送到聊天界面的ChatMessageList中 更新 不用再从数据库中
                     * 否则在主界面中 通知InformaticaFragment刷新未读消息显示
                     */
                    if (IMApp.otherSide != null) {
                        ChatMessageEvent cme = new ChatMessageEvent();
                        cme.setChatMessage(Convert.IMessageToChatMessage(iMessage));
                        EventBus.getDefault().post(cme);
                    }else {
                        EventBus.getDefault().post(new UnReceiveEvent());
                    }

                    break;
                case "1":
                    /**
                     * otherSide不为空 则在聊天界面 直接送到聊天界面的ChatMessageList中 更新 不用再从数据库中
                     * 否则在主界面中 通知InformaticaFragment刷新未读消息显示
                     */
                    if (IMApp.otherSide != null) {
                        ChatMessageEvent cme = new ChatMessageEvent();
                        cme.setChatMessage(Convert.IMessageToChatMessage(iMessage));
                        EventBus.getDefault().post(cme);
                    }else {
                        EventBus.getDefault().post(new UnReceiveEvent());
                    }
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
     * 根据id 判断有无插入重复值 防止同设置切换号导致出现重复数据
     * @param iMessage
     */
    private static void saveUnReceiveMessage(IMessageFactory.IMessage iMessage){
        if (!DbUtil.isHaveSameMsg(iMessage.getId())) {
            DbUtil.saveReceiveChatMessage(iMessage);
        }

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
            IMApp.userInfo.setImgUrl(iMessage.getUser(0).getImgUrl());
        }else {
            loginEvent.setSuccess(false);
        }
        EventBus.getDefault().post(loginEvent);
    }


}
