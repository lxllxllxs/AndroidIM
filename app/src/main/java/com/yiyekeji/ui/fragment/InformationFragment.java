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
import android.widget.TextView;

import com.yiyekeji.Event.UnReceiveEvent;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.handler.SysMessageHandler;
import com.yiyekeji.im.R;
import com.yiyekeji.impl.IInformation;
import com.yiyekeji.service.WebSocketService;
import com.yiyekeji.ui.activity.ChatActivity;
import com.yiyekeji.ui.adapter.InformAdapter;
import com.yiyekeji.ui.view.DividerItemDecoration;
import com.yiyekeji.utils.ConstantUtil;
import com.yiyekeji.utils.LogUtil;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    List<ChatMessage> messageList = new ArrayList<>();
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
    }


    private void initView() {
        adapter = new InformAdapter(getActivity(), chatMap);
        recylerView.setAdapter(adapter);
        recylerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickLitener(new InformAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, UserInfo info) {
                LogUtil.d("onItemClick", info.toString());
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(ConstantUtil.USER, info);
                startActivity(intent);
            }
        });
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private View getTextView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.textview, null);
        TextView tv = (TextView)view.findViewById(R.id.tv);
       /* tv.setText("哈哈");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,25);
        tv.setPadding(15,15,15,15);
        tv.setBackgroundColor(Color.GRAY);*/
        AutoUtils.autoTextSize(tv,25);
        return  tv;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            LogUtil.d("setUserVisibleHint", "asd");
        } else {
        }
    }

    /**
     * 作为接收方需要发送人id
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unReceiMessage(UnReceiveEvent event) {
        LogUtil.d(TAG, event.getChatMap().size());
        adapter.setData(event.getChatMap());
        WebSocketService.chat(SysMessageHandler.feedBackUnRecieveMessage(event.getMsgIdList()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }
}
