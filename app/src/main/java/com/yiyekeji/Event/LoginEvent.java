package com.yiyekeji.Event;

import com.yiyekeji.bean.ReceiveMessage;

/**
 * Created by lxl on 2016/10/31.
 */
public class LoginEvent {
    private boolean isSuccess=false;
    private ReceiveMessage message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ReceiveMessage getMessage() {
        return message;
    }

    public void setMessage(ReceiveMessage message) {
        this.message = message;
    }
}
