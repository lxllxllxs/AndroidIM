package com.yiyekeji.handler;

import com.yiyekeji.iminterface.CommonCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ReceiverHandler {
    /**************************Receive***************************************/
    public static Object receive(String jsonString, CommonCallBack commonCallBack){
        JSONObject jsonObject=null;
        JSONArray jsonArray;
        MessageType type=null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonArray=jsonObject.getJSONArray("JSON_ARRAY");
            if (jsonArray!=null){
                return jsonArray;
            }
            type= MessageType.valueOf(jsonObject.getString(MESSAG_TYPE));
            switch (type){
                case RedPocketMessage:
                    break;
                case LinkMan:
                    return parseLinkListJson(jsonObject);
                case TextMessage:
                    break;
                case ImageMessage:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
