package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.im.R;
import com.yiyekeji.iminterface.CommonCallBack;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.JsonUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.WebSocketUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/23.
 */
public class LoginActivity extends BaseActivity {
    EditText edtUsername;
    EditText edtPassword;
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WebSocketUtil.connect(this);

        edtPassword=(EditText)findViewById(R.id.edt_password);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        tvConfirm=(TextView)findViewById(R.id.tv_confirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"login",0);
                login();
            }
        });
    }

    public void login() {
        WebSocketUtil.login(JsonUtil.login(edtUsername.getText().toString(), edtPassword.getText().toString()), new CommonCallBack() {
            @Override
            public void commonCallBack(JSONObject rootJson) {
                String result= null;
                try {
                    result = rootJson.getString(JsonUtil.RESULT);
                    if (result.equals("false")){
                        LogUtil.d("parseCommonCallBackJson","登录失败");
                    }else {
                        LogUtil.d("parseCommonCallBackJson","登录成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
