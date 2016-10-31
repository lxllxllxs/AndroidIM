package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.bean.User;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
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
        WebSocketService.chat(SysMessageHandler.login(name, pwd));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLoginSuccess(LoginEvent loginEvent){
        if (loginEvent.isSuccess()){
            showShortToast("login successfully!!");
            return;
//            Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
//            intent.putExtra(ConstantUtil.RECEIVER_MESSAGE,loginEvent.getMessage());
//            startActivity(intent);
        }else {
            Toast.makeText(this,"password is uncorrect",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
