package com.yiyekeji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yiyekeji.adapter.ContactsAdapter;
import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.base.BaseActivity;
import com.yiyekeji.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ContactsActivity extends BaseActivity {

    private final String TAG = "ContactsActivity";
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    ContactsAdapter ca;
    ArrayList<User> userArrayList;
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
        userArrayList=getIntent().getParcelableArrayListExtra(ConstantUtil.USER_LIST);
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

}
