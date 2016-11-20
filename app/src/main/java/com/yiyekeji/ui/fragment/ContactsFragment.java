package com.yiyekeji.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiyekeji.Event.LinkManEvent;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.ChatActivity;
import com.yiyekeji.ui.adapter.ContactsAdapter;
import com.yiyekeji.utils.ConstantUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 在联系人界面才 接收离线消息
 */
public class ContactsFragment extends Fragment {

    private final String TAG = "ContactsFragment";
    ContactsAdapter ca;
    ArrayList<UserInfo> userArrayList = new ArrayList<>();
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        WebSocketService.chat(SysMessageHandler.getLinkManList());
    }


    private void initView() {
        ca = new ContactsAdapter(getActivity(), userArrayList);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recylerView.setAdapter(ca);
        ca.setOnItemClickLitener(new ContactsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(ConstantUtil.USER,userArrayList.get(position));
            startActivity(intent);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLinkManList(LinkManEvent event) {
        userArrayList = event.getLinkManList();
        if (userArrayList == null) {
            return;
        }
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
