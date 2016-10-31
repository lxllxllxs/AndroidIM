package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    private final String TAG = "ChatAcitvity";
    @InjectView(R.id.tv_send_text)
    TextView tvSendText;
    @InjectView(R.id.tv_send_image)
    TextView tvSendImage;
    @InjectView(R.id.tv_message)
    TextView tvMessage;
    private User receriver;

    /**
     * 在这里获取系统的传感器
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    private void initData() {
        receriver = getIntent().getParcelableExtra(ConstantUtil.USER);
        getUnReceiveMessage();
    }

    private void initView() {
    }

    private void getUnReceiveMessage() {
    }
    /**
     * 若要改此方法里面的字段，可能还需要改发红包界面里面的发送红包方法，多一个参数的为语音消息的秒数
     */
    private void sendMessage() {

    }

    @OnClick({R.id.tv_send_text, R.id.tv_send_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_text:
                sendMessage();
                break;
            case R.id.tv_send_image:
                break;
        }
    }
}
