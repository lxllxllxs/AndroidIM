package com.yiyekeji.handler;

import com.yiyekeji.Event.LinkManEvent;
import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.Event.UnReceiveEvent;
import com.yiyekeji.bean.IMessageFactory;
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
                    LoginEvent loginEvent=new LoginEvent();
                    if (iMessage.getResult().equals("1")){
                        loginEvent.setSuccess(true);
                    }else {
                        loginEvent.setSuccess(false);
                    }
                    EventBus.getDefault().post(loginEvent);
                    break;
                case "1":
                    LinkManEvent linkManEvent=new LinkManEvent();
                    linkManEvent.setiMessage(iMessage);
                    EventBus.getDefault().post(linkManEvent);
                    break;
                case "2"://接受离线消息
                    LogUtil.d("ReceiverHandler","接收离线消息");
                    UnReceiveEvent event=new UnReceiveEvent();
                    event.setiMessage(iMessage);
                    EventBus.getDefault().post(event);
                    break;
            }
        }else if(iMessage.getMainType().equals("1")){
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


}
