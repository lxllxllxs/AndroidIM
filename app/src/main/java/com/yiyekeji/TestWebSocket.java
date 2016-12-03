package com.yiyekeji;

import com.yiyekeji.utils.LogUtil;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

/**
 * Created by lxl on 2016/12/3.
 */
public class TestWebSocket {

  /*  public static void main(String[] args){
        TestWebSocket mytest=new TestWebSocket();
        for (int i=0;i<50;i++){
            try {
                mytest.connect();
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
        }
    }*/


    public void connect() throws WebSocketException {
        WebSocketConnection connection = new WebSocketConnection();
        connection.connect(Config.IM_URL, new WebSocket.ConnectionHandler() {
            @Override
            public void onOpen() {
                LogUtil.d("onOpen 发送信息id：","onOpen");
            }
            @Override
            public void onClose(int code, String reason) {
            }
            @Override
            public void onTextMessage(String payload) {
                LogUtil.d("onTextMessage 发送信息id：",payload);
            }
            @Override
            public void onRawTextMessage(byte[] payload) {
            }
            @Override
            public void onBinaryMessage(byte[] payload) {
            }
        });
    }
}
