package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.IMApp;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.dao.DateComParator;
import com.yiyekeji.handler.ChatMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.adapter.ChatAdapter;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.DbUtil;
import com.yiyekeji.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    private final String TAG = "ChatAcitvity";
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    @InjectView(R.id.edt_content)
    EditText edtContent;
    @InjectView(R.id.tv_send)
    TextView tvSend;
    private IMessageFactory.IMessage.User receriver;
    ArrayList<ChatMessage> messageList = new ArrayList<>();
    private ChatAdapter chatAdapter;

    ChatMessage chatMessage;
     String receiverId;
    final String senderId = IMApp.userInfo.getUserId();
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
        receiverId = receriver.getUserId();
        tvName.setText(receriver.getUsername());
        LogUtil.d("initData", receriver.toString());
    }

    private void initView() {
        getChatMessageFormDb();
        chatAdapter=new ChatAdapter(this,messageList);
        recylerView.setAdapter(chatAdapter);
        recylerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getChatMessageFormDb(){
        messageList.addAll(DbUtil.searchReceivedMsg());
        messageList.addAll(DbUtil.searchSendMsg(receriver.getUserId()));
        Collections.sort(messageList,new DateComParator());
        LogUtil.d("getChatMessageFormDb", messageList.size()+"");
    }

    /**
     * 若要改此方法里面的字段，可能还需要改发红包界面里面的发送红包方法，多一个参数的为语音消息的秒数
     */
    private void sendTextMessage() {
        String content = edtContent.getText().toString();
        upDateLocal(content);
        WebSocketService.chat(ChatMessageHandler.sendTextMessage(content,receiverId));//这里数据库保存
    }

    private void upDateLocal(String content) {
        chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setSenderId(senderId);
        chatMessage.setReceiverId(receiverId);
        messageList.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                sendTextMessage();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(ChatMessageEvent event) {
        LogUtil.d("chatActivity","已经收到信息");
        ChatMessage chatMessage = event.getChatMessage();
        messageList.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }


}
