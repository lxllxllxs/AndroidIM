package com.yiyekeji.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yiyekeji.bean.ReceiveMessage;
import com.yiyekeji.bean.User;
import com.yiyekeji.handler.ChatMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.iminterface.ReceiverCallBack;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.WebSocketUtil;

public class ChatActivity extends BaseActivity  {

    private final String TAG = "ChatAcitvity";
    private User receriver;
    private TextView tv_sendText;
    private TextView tv_sendImage;
    /**
     * 在这里获取系统的传感器
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
        WebSocketUtil.connect(this);
    }

    private void initData() {
        receriver = getIntent().getParcelableExtra(ConstantUtil.USER);
    }

    private void initView(){
        tv_sendText=(TextView)findViewById(R.id.tv_send_text);
        tv_sendImage=(TextView)findViewById(R.id.tv_send_image);
        tv_sendText.setOnClickListener(this);
        tv_sendImage.setOnClickListener(this);
    }

    /**
     * 若要改此方法里面的字段，可能还需要改发红包界面里面的发送红包方法，多一个参数的为语音消息的秒数
     */
    private void sendMessage() {
        WebSocketUtil.chat(ChatMessageHandler.sendTextMessage(receriver, "春江潮水连海平，海上明月共潮生"),
                new ReceiverCallBack() {
            @Override
            public void receiverCallBack(ReceiveMessage receiveMessage) {
                LogUtil.d("receiverCallBack", receiveMessage.toString());
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_send_image){
        }
        if (v.getId()==R.id.tv_send_text){
            sendMessage();
        }

    }
}
