package test;

import com.yiyekeji.Config;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

/**
 * Created by Administrator on 2016/10/23.
 */
public class test {
/*    public static void main(String[] args){
        test mytest=new test();
        for (int i=0;i<50;i++){
            try {
                mytest.connection();
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void  connection() throws WebSocketException {
       WebSocketConnection  connection = new WebSocketConnection();
        connection.connect(Config.IM_URL,null);
    }
}
