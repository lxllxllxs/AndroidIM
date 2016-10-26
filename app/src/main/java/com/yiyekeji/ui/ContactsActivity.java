package com.yiyekeji.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yiyekeji.adapter.ContactsAdapter;
import com.yiyekeji.bean.ReceiveMessage;
import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 在联系人界面才 接收离线消息
 */
public class ContactsActivity extends BaseActivity {

    private final String TAG = "ContactsActivity";
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    ContactsAdapter ca;
    ArrayList<User> userArrayList;
    ChatMessageReceiver receiver;
    ReceiveMessage rm;
    /**
     * 在这里获取系统的传感器
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.inject(this);
//        register();
        initData();
        initView();

    }

    private void register() {
        receiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantUtil.USER_LIST);
        registerReceiver(receiver, filter);
    }

    private void initData() {
        rm=getIntent().getParcelableExtra(ConstantUtil.RECEIVER_MESSAGE);
        userArrayList=rm.getUsers();
    }



    private void initView() {
        ca=new ContactsAdapter(this,userArrayList);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(ca);
        ca.setOnItemClickLitener(new ContactsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent =new Intent(ContactsActivity.this,ChatActivity.class);
                intent.putExtra(ConstantUtil.USER,userArrayList.get(position));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
    }

    class ChatMessageReceiver extends BroadcastReceiver {
        public void onReceive(Context arg0, Intent arg1) {
            rm=arg1.getParcelableExtra(ConstantUtil.USER_LIST);
            userArrayList=rm.getUsers();
        }
    }
}
