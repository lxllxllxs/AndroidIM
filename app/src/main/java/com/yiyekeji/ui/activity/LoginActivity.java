package com.yiyekeji.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.Event.LoginEvent;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.utils.LogUtil;

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
    Intent intent1 =null;
    ArrayList<IMessageFactory.IMessage.User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login);
        initView();
        initWebSocketService();
    }

    private void initWebSocketService() {
        intent1 = new Intent(this, WebSocketService.class);
        startService(intent1);
    }

    @Override
    public void initView() {
        edtPassword=(EditText)findViewById(R.id.edt_password);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtUsername.setText("lxl1");
        edtPassword.setText("123");
        tvConfirm=(TextView)findViewById(R.id.tv_confirm);
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


    private  void testLogin(){
        String username="lxl";
        for (int i=4;i<200;i++){
            WebSocketService.chat(SysMessageHandler.login(username+i, "123"));
        }
    }
    private int loginCount;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLoginSuccess(LoginEvent loginEvent){
        if (loginEvent.isSuccess()){
            loginCount++;
            LogUtil.d("loginSuccess",loginCount+"");
            showShortToast("login successfully!!");
            startActivity(MainFragmentActivity.class);
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
