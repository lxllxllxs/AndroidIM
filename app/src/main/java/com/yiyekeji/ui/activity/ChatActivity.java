package com.yiyekeji.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyekeji.Event.ChatMessageEvent;
import com.yiyekeji.IMApp;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.dao.DateComParator;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.handler.ChatMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.adapter.ChatAdapter;
import com.yiyekeji.ui.view.StatusBarCompat;
import com.yiyekeji.utils.LogUtil;
import com.yiyekeji.utils.ThreadPools;

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
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    @InjectView(R.id.edt_content)
    EditText edtContent;
    @InjectView(R.id.tv_send)
    TextView tvSend;
    ArrayList<ChatMessage> messageList = new ArrayList<>();
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_select_img)
    LinearLayout llSelectImg;
    @InjectView(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @InjectView(R.id.ll_red_pocket)
    LinearLayout llRedPocket;
    @InjectView(R.id.ll_record)
    LinearLayout llRecord;
    @InjectView(R.id.ll_emoji)
    LinearLayout llEmoji;
    @InjectView(R.id.ll_more)
    LinearLayout llMore;
    private ChatAdapter chatAdapter;
    private UserInfo receriver;
    ChatMessage chatMessage;

    String selfId = IMApp.userInfo.getUserId();
    int color_bule;

    private final int REQUEST_LOAD_IMAGE=0x123;



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

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        //定制状态栏颜色使用此方法
        //StatusBarCompat.compat(this, 0xFFFF0000);
        color_bule = ContextCompat.getColor(this, R.color.theme_blue);
        edtContent.addTextChangedListener(tw);
        StatusBarCompat.compat(this);
        chatAdapter = new ChatAdapter(this, messageList);
        recylerView.setAdapter(chatAdapter);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        receriver = IMApp.otherSide;
        getChatMessageFormDb();
        upDateSessionMsg();
        tvTitle.setText(receriver.getUserName());
        LogUtil.d("initData", receriver.toString());
    }

    private void upDateSessionMsg() {
        ThreadPools.addRunnable(new Runnable() {
            @Override
            public void run() {
                DbUtil.upDataSessionRead(receriver.getUserId());
            }
        });
    }

    private void getChatMessageFormDb() {
        long start = System.currentTimeMillis();
        messageList.addAll(DbUtil.searchAllMsg(receriver.getUserId()));
        Collections.sort(messageList, new DateComParator());
        LogUtil.d("总共花费时间：", System.currentTimeMillis() - start);
        LogUtil.d("getChatMessageFormDb", messageList.size() + "");
    }

    /**
     * 若要改此方法里面的字段，
     * 可能还需要改发红包界面里面的发送红包方法，多一个参数的为语音消息的秒数
     */
    private void sendTextMessage() {
        String content = edtContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        upDateLocal(content,"0");
        WebSocketService.chat(ChatMessageHandler.sendTextMessage(content, receriver));//这里数据库保存
    }

    private void upDateLocal(String content,String type) {
        chatMessage = new ChatMessage();
        chatMessage.setMessageType(type);
        chatMessage.setContent(content);
        chatMessage.setIsReceiver("0");
        chatMessage.setSenderId(selfId);
        chatMessage.setReceiverId(receriver.getUserId());
        chatMessage.setReceiverName(receriver.getUserName());
        messageList.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        //滚动到底部
        scrollToBottom();
    }


    @OnClick({R.id.tv_send,R.id.ll_select_img, R.id.ll_take_photo, R.id.ll_red_pocket, R.id.ll_record, R.id.ll_emoji, R.id.ll_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                sendTextMessage();
                edtContent.setText("");
                break;
            case R.id.ll_select_img:
                selectImg();
                break;
            case R.id.ll_take_photo:
                break;
            case R.id.ll_red_pocket:
                break;
            case R.id.ll_record:
                break;
            case R.id.ll_emoji:
                break;
            case R.id.ll_more:
                break;
        }
    }

    public void selectImg(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode){
            case REQUEST_LOAD_IMAGE:
                Uri selectedImage = data.getData();
                WebSocketService.chat(ChatMessageHandler.sendImgMessage(selectedImage,receriver));
                upDateLocal(selectedImage.toString(),"1");

                break;
        }
    }
















    private void scrollToBottom() {
        recylerView.scrollToPosition(recylerView.getAdapter().getItemCount() - 1);
    }
    @Override
    public void finish() {
        super.finish();
        MainFragmentActivity.informationFragment.refreshSessionList();
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                tvSend.setBackground(getResources().
                        getDrawable(R.drawable.bg_bulesolid_6radius));
            } else {
                tvSend.setBackground(getResources().getDrawable(R.drawable.bg_graysolid_6raidus));
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverMessage(ChatMessageEvent event) {
        LogUtil.d("chatActivity", "已经收到信息");
        ChatMessage chatMessage = event.getChatMessage();
        messageList.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollToBottom();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }


}
