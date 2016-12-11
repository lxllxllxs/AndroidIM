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

import com.yiyekeji.Event.UnReceiveEvent;
import com.yiyekeji.IMApp;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.db.DbUtil;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.impl.IInformation;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.ChatActivity;
import com.yiyekeji.ui.adapter.InformAdapter;
import com.yiyekeji.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/11/1.
 */
public class InformationFragment extends Fragment {
    private final String TAG = "InformationFragment";
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    InformAdapter adapter;

    Map<UserInfo, IInformation> hashMap = new HashMap<>();

    private static HashMap<String, ArrayList<IInformation>> chatMap = new HashMap<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
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
        WebSocketService.chat(SysMessageHandler.getUnRecieveMessage());
        hashMap = DbUtil.getAllSessionChatMap();
    }

    private void initView() {
        adapter = new InformAdapter(getActivity(), hashMap);
        recylerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new InformAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, UserInfo info) {
                LogUtil.d("onItemClick", info.toString());
                IMApp.otherSide = info;
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void refreshSessionList(){
        hashMap = DbUtil.getAllSessionChatMap();
        adapter.setData(hashMap);
    }
    /**
     * 重新从数据库读取
     * 或许以后可以优化 直接更新部分
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unReceiMessage(UnReceiveEvent event) {
        LogUtil.d(TAG, event.getChatMap().size());
        refreshSessionList();
        WebSocketService.chat(SysMessageHandler.feedBackUnRecieveMessage(event.getMsgIdList()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
