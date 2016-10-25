package com.yiyekeji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;
import com.yiyekeji.iminterface.SendMessageCallBack;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.MessageHandlerUtil;
import com.yiyekeji.utils.WebSocketUtil;

import java.util.ArrayList;

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

    }


    @SuppressWarnings("unchecked")
    public void login(final String name,String pwd) {
        WebSocketUtil.chat(MessageHandlerUtil.login(name, pwd), new SendMessageCallBack() {
            @Override
            public void sendMessageCallBack(boolean isSucceed, String jsonString) {
                LogUtil.d("sendMessageCallBack", "" +jsonString);
                userArrayList = (ArrayList<User>) MessageHandlerUtil.receive(jsonString);
                LogUtil.d("userListSize",userArrayList.size()+"");
                Intent intent=new Intent(LoginActivity.this,ContactsActivity.class);
                intent.putParcelableArrayListExtra(ConstantUtil.USER_LIST, userArrayList);
                startActivity(intent);
            }
        });
    }

}
