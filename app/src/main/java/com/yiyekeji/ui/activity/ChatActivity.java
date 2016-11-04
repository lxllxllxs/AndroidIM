package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.dao.BaseChatMessage;
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
    ArrayList<BaseChatMessage> messageList = new ArrayList<>();
    private ChatAdapter chatAdapter;
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
    }

    private void getChatMessageFormDb(){
        messageList.addAll(DbUtil.searchReceivedMsg());
        messageList=DbUtil.searchSendMsg(receriver.getUserId());
        LogUtil.d("getChatMessageFormDb", messageList.size()+"");
        for (BaseChatMessage cm : messageList) {
            LogUtil.d("BaseChatMessage:",cm.getContent()+cm.getIsReceiver());
        }
    }

    private void initView() {
        getChatMessageFormDb();
//        chatAdapter=new ChatAdapter(this,messageList);
//        recylerView.setAdapter(chatAdapter);
//        recylerView.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * 若要改此方法里面的字段，可能还需要改发红包界面里面的发送红包方法，多一个参数的为语音消息的秒数
     */
    private void sendMessage() {
        WebSocketService.chat(ChatMessageHandler.sendTextMessage("无边落木萧萧下，不尽长江滚滚来", receriver.getUserId()));
    }

    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                sendMessage();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTvMessage(ChatMessageEvent event) {
        LogUtil.d("chatActivity","已经收到信息");
        IMessageFactory.IMessage iMessage = event.getiMessage();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

}
