package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.handler.ChatMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private IMessageFactory.IMessage.User receriver;

    /**
     * 在这里获取系统的传感器
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initData();
        initView();

    }

    private void initData() {
        receriver = (IMessageFactory.IMessage.User) getIntent().getSerializableExtra(ConstantUtil.USER);
        LogUtil.d("initData", receriver.toString());
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
        WebSocketService.chat(ChatMessageHandler.sendTextMessage("无边落木萧萧下，不尽长江滚滚来",receriver.getUserId()));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTvMessage(ChatMessageEvent event){
        IMessageFactory.IMessage iMessage=event.getiMessage();
        tvMessage.setText(iMessage.getContent());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
