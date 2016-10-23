package com.yiyekeji;

import com.yiyekeji.iminterface.CommonCallBack;
import com.yiyekeji.utils.JsonUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.WebSocketUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/23.
 */
public class test {
    public static void main(String[] args){
        test mytest=new test();

    }


    public void login(final String username) {
        WebSocketUtil.login(JsonUtil.login(username,"123"), new CommonCallBack() {
            @Override
            public void commonCallBack(JSONObject rootJson) {
                String result= null;
                if (rootJson==null){
                    return;
                }
                try {
                    result = rootJson.getString(JsonUtil.RESULT);
                    if (result.equals("true")){
                        LogUtil.d("parseCommonCallBackJson",username+"登录成功");
                    }else {
                        LogUtil.d("parseCommonCallBackJson",username+"登录失败");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    ;
}
