package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;
import com.yiyekeji.iminterface.SendMessageCallBack;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.JsonUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.WebSocketUtil;

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
    ArrayList<User> userArrayList;
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
                    public void sendMessageCallBack(boolean isSucceed,byte[] payload) {
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
        WebSocketUtil.chat(JsonUtil.login(name, pwd), new SendMessageCallBack() {
            @Override
            public void sendMessageCallBack(boolean isSucceed, byte[] payload) {
                LogUtil.d("parseCommonCallBackJson", "" + new String(payload));
                userArrayList = (ArrayList<User>) JsonUtil.receive(payload);
                LogUtil.d("userListSize",userArrayList.size());
            }
        });
    }

}
