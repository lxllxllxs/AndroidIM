package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.im.R;
import com.yiyekeji.iminterface.CommonCallBack;
import com.yiyekeji.iminterface.SendMessageCallBack;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.JsonUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.WebSocketUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
public class LoginActivity extends BaseActivity {
    EditText edtUsername;
    EditText edtPassword;
    TextView tvConfirm;
    TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WebSocketUtil.connect(this);

        edtPassword=(EditText)findViewById(R.id.edt_password);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        tvConfirm=(TextView)findViewById(R.id.tv_confirm);
        tvSend=(TextView)findViewById(R.id.tv_send);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"login",Toast.LENGTH_SHORT).show();
                login(edtUsername.getText().toString(),edtPassword.getText().toString());
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSocketUtil.chat(JsonUtil.sendTextMessage("lxl", "123","hello"), new SendMessageCallBack() {
                    @Override
                    public void sendMessageCallBack(boolean isSucceed, String messageId, JSONObject contentJson) {
                        LogUtil.d("parseCommonCallBackJson",""+isSucceed+messageId);
                    }
                });
            }
        });
    }

    public void addList(){
        List<String> useinfo=new ArrayList<>();
        useinfo.add("lxl");
        useinfo.add("lxl1");
        useinfo.add("lxl2");

    }

    public void login(final String name,String pwd) {
        WebSocketUtil.login(JsonUtil.login(name,pwd), new CommonCallBack() {
            @Override
            public void commonCallBack(JSONObject rootJson) {
                String result= null;
                if (rootJson==null){
                    return;
                }
                try {
                    result = rootJson.getString(JsonUtil.RESULT);
                    if (result.equals("true")){
                        LogUtil.d("parseCommonCallBackJson",name+"登录成功");
                    }else {
                        LogUtil.d("parseCommonCallBackJson",name+"登录失败");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
