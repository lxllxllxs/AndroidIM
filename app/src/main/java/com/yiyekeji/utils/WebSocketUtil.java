package com.yiyekeji.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yiyekeji.Config;
import com.yiyekeji.autobahn.WebSocket;
import com.yiyekeji.autobahn.WebSocketConnection;
import com.yiyekeji.autobahn.WebSocketException;
import com.yiyekeji.iminterface.CommonCallBack;
import com.yiyekeji.iminterface.SendMessageCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class WebSocketUtil {
	public static String currentActivityName = null;
	public static int closeCode = 0;
	
	private static Context context;
	private static WebSocketConnection connection = null;
	private static boolean isConnected = false;
	private static SendMessageCallBack sendMessageCallBack;
	private static CommonCallBack commonCallBack;
	private static final String MTAG="WebSocketUtil";
	public static void connect(final Context context) {
		WebSocketUtil.context = context;
		if(connection == null) {
			connection = new WebSocketConnection();
		}
		try {
			if(!connection.isConnected()) {
				LogUtil.d("WebSocketUtil","IM后台地址为"+Config.IM_URL);
				connection.connect(Config.IM_URL, new WebSocket.ConnectionHandler() {
					@Override
					public void onOpen() {
						Log.d(MTAG, "onOpen: ");
						isConnected=true;
					}
					@Override
					public void onClose(int code, String reason) {
						Log.d(MTAG, "onClose: "+code+"reason:"+reason);
						isConnected=false;
						connection=null;
					}
					@Override
					public void onTextMessage(String payload) {
						parseCommonCallBackJson(payload);
					}
					@Override
					public void onRawTextMessage(byte[] payload) {
						Log.d(MTAG, "onRawTextMessage: "+new String(payload));
					}
					@Override
					public void onBinaryMessage(byte[] payload) {
						Log.d(MTAG, "onBinaryMessage: "+new String(payload));
						if (sendMessageCallBack==null){
							return;
						}
						sendMessageCallBack.sendMessageCallBack(true,payload);

					}
				});
			}
		} catch (WebSocketException e) {
			Log.d("WebSocketUtil", "connect: "+e.toString());
			isConnected = false;
			connection = null;
		}
	}

	/**
	 * 只解析登录验证返回
	 * @param payload
     */
	private static void parseCommonCallBackJson(String payload) {
		LogUtil.d("parseCommonCallBackJson",payload);
		try {
			JSONObject jsonObject = new JSONObject(payload);
			commonCallBack.commonCallBack(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送信息主函数
	 * @param jsonObject
	 * @param messageCallBack
     */
	public static void chat(@NonNull JSONObject jsonObject, SendMessageCallBack messageCallBack) {
		if(messageCallBack != null) {
			sendMessageCallBack = messageCallBack;
		}
		if(!isConnected()) {
			Log.d("WebSocketUtil", "服务断开，发送失败");
			WebSocketUtil.connect(context);
			callBackCommon(null);
			return;
		}
		Log.d("WebSocketUtil", "chat: JSONObject大小"+jsonObject.toString().length());

		connection.sendBinaryMessage(jsonObject.toString().getBytes());
	}

	public static void login(@NonNull JSONObject jsonObject,CommonCallBack callBack){
		if (callBack!=null){
			commonCallBack=callBack;
		}
		if(!isConnected()) {
			Log.d("WebSocketUtil", "服务断开，发送失败");
			WebSocketUtil.connect(context);
			callBackCommon(null);
			return;
		}
		connection.sendBinaryMessage(jsonObject.toString().getBytes());
	}

	/**
	 * 对象转数组
	 * @param obj
	 * @return
	 */
	public static byte[] toByteArray (Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray ();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Log.d("toByteArray", "toByteArray: size"+bytes.length);
		return bytes;
	}




	private static void callBackCommon(JSONObject json) {
		if(commonCallBack != null) {
			commonCallBack.commonCallBack(json);
		}
	}

	public static void disconnect() {
		if(isConnected()) {
			connection.disconnect();
			connection = null;
		}
	}
	public static boolean isConnected() {
		if(connection!=null && isConnected) {
			return true;
		}else {
			return false;
		}
	}
	public static void common(String headJsonString, byte[]content, CommonCallBack callBack) {
		commonCallBack = callBack;
	}

}
