package com.yiyekeji.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yiyekeji.Config;
import com.yiyekeji.autobahn.WebSocket;
import com.yiyekeji.autobahn.WebSocketConnection;
import com.yiyekeji.autobahn.WebSocketException;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.handler.ReceiverHandler;
import com.yiyekeji.iminterface.CommonCallBack;
import com.yiyekeji.iminterface.ReceiverCallBack;
import com.yiyekeji.iminterface.SendMessageCallBack;
import com.yiyekeji.utils.LogUtil;

/**
 * Created by lxl on 2016/10/31.
 */
public class WebSocketService extends Service {

    private static Context context;
    private static WebSocketConnection connection = null;
    private static boolean isConnected = false;
    private static SendMessageCallBack sendMessageCallBack;
    private static ReceiverCallBack receiverCallBack;
    private static CommonCallBack commonCallBack;
    private static final String MTAG="WebSocketService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connect(this);

    }

    public static void connect(final Context context1) {
        context=context1;
        if(connection == null) {
            connection = new WebSocketConnection();
        }
        try {
            if(!connection.isConnected()) {
                LogUtil.d("WebSocketService","IM后台地址为"+ Config.IM_URL);
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
                    }
                    @Override
                    public void onRawTextMessage(byte[] payload) {
                        Log.d(MTAG, "onRawTextMessage: "+new String(payload));
                    }
                    @Override
                    public void onBinaryMessage(byte[] payload) {
                        try {
                            IMessageFactory.IMessage iMessage = IMessageFactory.IMessage.parseFrom(payload);
                            Log.d(MTAG, "onBinaryMessage: " + iMessage.toString());
                            ReceiverHandler.receive(iMessage);
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } catch (WebSocketException e) {
            Log.d("WebSocketService", "connect: "+e.toString());
            isConnected = false;
            connection = null;
        }
    }




    /**
     * 发送信息主函数 需要清楚 返回的是什么
     */
    public static void chat(@NonNull IMessageFactory.IMessage iMessage) {
        if(!isConnected()) {
            Log.d("WebSocketUtil", "服务断开，发送失败");
            connect(context);
            return;
        }
        Log.d("WebSocketUtil", "chat: JSONObject大小"+iMessage.toString().length());
        connection.sendBinaryMessage(iMessage.toByteArray());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
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
}
