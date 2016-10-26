package com.yiyekeji.handler;

import com.yiyekeji.imenum.MainType;
import com.yiyekeji.imenum.SysMessType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/25.
 */
public class SysMessageHandler {

    public final static String MESSAG_TYPE="messageType";

    public final static String USER_NAME="userName";
    public final static String PASSWORD="password";
    public final static String RESULT="result";


    /**
     * 发送登录信息
     * @param username
     * @param password
     * @return
     */
    public static JSONObject login(String username, String password){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(MainType.getName(), MainType.SysMessType);
            jsonObject.put(SysMessType.getName(), SysMessType.Login);

            jsonObject.put(USER_NAME,username);
            jsonObject.put(PASSWORD,password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
