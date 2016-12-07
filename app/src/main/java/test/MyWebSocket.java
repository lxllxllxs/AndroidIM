package test;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketOptions;

/**
 * Created by lxl on 2016/12/6.
 */
public class MyWebSocket implements WebSocket {
    @Override
    public void connect(String wsUri, ConnectionHandler wsHandler) throws WebSocketException {

    }

    @Override
    public void connect(String wsUri, ConnectionHandler wsHandler, WebSocketOptions options) throws WebSocketException {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void sendBinaryMessage(byte[] payload) {

    }

    @Override
    public void sendRawTextMessage(byte[] payload) {

    }

    @Override
    public void sendTextMessage(String payload) {

    }
}
