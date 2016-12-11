package com.yiyekeji.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yiyekeji.Config;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.handler.ReceiverHandler;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.MessageQueue;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

/**
 * Created by lxl on 2016/10/31.
 */
public class WebSocketService extends Service {
    static boolean isSendSuccessfull=true;
    private static Context context;
    private static WebSocketConnection connection = null;
    private static boolean isConnected = false;
    private static final String MTAG="WebSocketService";

    private static MessageQueue messageQueue;

    static Thread thread;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connect(this);
        messageQueue=new MessageQueue();
        thread = new Thread(runnable);
        thread.start();
    }

    public static void connect(final Context context1) {
        context=context1;
        if(connection == null) {
            LogUtil.d("WebSocketService","正在创建WebSocketService");
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
                        LogUtil.d("onTextMessage 发送信息id：",payload);
                        //只有发送成功才会发送下一条信息 只针对聊天信息
                        if (payload.equals(iMessage.getId())){
                            if (iMessage.getMainType().equals("1")) {
                                DbUtil.upDataSendChatMsg(iMessage.getId());//只有是聊天信息才需要更新状态
                            }
                            isSendSuccessfull=true;
                        }
                    }
                    @Override
                    public void onRawTextMessage(byte[] payload) {
                    }
                    @Override
                    public void onBinaryMessage(byte[] payload) {
                        try {
                            IMessageFactory.IMessage iMessage = IMessageFactory.IMessage.parseFrom(payload);
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


    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            loop();
        }
    };

   static IMessageFactory.IMessage iMessage=null;
    public  void loop(){
        while (true) {
            if (isSendSuccessfull) {
                iMessage = messageQueue.getEgg();
                isSendSuccessfull=false;//锁住
                send(iMessage);
            }
        }
    }

    /**
     * 若是10秒后还未收到回执执行此动作
     * 重发
     * 先插入头部 在执行
     * 防止在此时收到回执
     */
    public static Runnable runnable2=new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                if (isSendSuccessfull) {
                    return;
                }
                messageQueue.insertToFirst(iMessage);
                isSendSuccessfull = true;
            }
        }

    };

    private static
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**在断线时要先连上
     * 发送信息主函数 缓存信息
     */
    public static void chat(@NonNull IMessageFactory.IMessage iMessage) {
        if (!isConnected){
            return;
        }
        messageQueue.putEgg(iMessage);
    }


    public static void send(@NonNull IMessageFactory.IMessage iMessage){
        if(!isConnected()) {
            Log.d("WebSocketService", "服务断开，发送失败");
            connect(context);
            return;
        }
        Log.d("WebSocketService", "正在发送的信息大小"+iMessage.toString().length());
        Log.d("WebSocketService", "正在发送的信息:"+iMessage.toString());
        connection.sendBinaryMessage(iMessage.toByteArray());
        handler.postDelayed(runnable2,5*1000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable2);
        disconnect();
        LogUtil.d("WebSocketService","正在销毁WebSocketService");
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
