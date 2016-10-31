package com.yiyekeji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yiyekeji.Event.LinkManEvent;
import com.yiyekeji.bean.IMessageFactory;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.adapter.ContactsAdapter;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    List<IMessageFactory.IMessage.User> userArrayList = new ArrayList<>();
    /**
     * 在这里获取系统的传感器
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    private void initData() {
        WebSocketService.chat(SysMessageHandler.getLinkManList());
    }



    private void initView() {
        ca=new ContactsAdapter(this,userArrayList);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(ca);
        ca.setOnItemClickLitener(new ContactsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                showShortToast(userArrayList.get(position).toString());
                Intent intent =new Intent(ContactsActivity.this,ChatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(ConstantUtil.USER,userArrayList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLinkManList(LinkManEvent event){
        for (IMessageFactory.IMessage.User user:event.getiMessage().getUserList()){
            LogUtil.d("updateLinman",user.toString());
        }
        userArrayList=event.getiMessage().getUserList();
        if (userArrayList == null) {
            return;
        }
        initView();
    }

}
