package com.yiyekeji.iminterface;

import org.json.JSONObject;

public interface SendMessageCallBack {
    void sendMessageCallBack(boolean isSucceed, String messageId, JSONObject contentJson);
	}
	
