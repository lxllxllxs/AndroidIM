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

import com.yiyekeji.IMApp;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.ChatActivity;
import com.yiyekeji.ui.adapter.ContactsAdapter;
import com.yiyekeji.ui.view.DividerItemDecoration;
import com.yiyekeji.utils.ConstantUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 在联系人界面才 接收离线消息
 */
public class ContactsFragment extends Fragment {

    private final String TAG = "ContactsFragment";
    ContactsAdapter ca;
    @InjectView(R.id.recylerView)
    RecyclerView recylerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        ca = new ContactsAdapter(getActivity(), IMApp.linkManList);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recylerView.setAdapter(ca);
        recylerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        ca.setOnItemClickLitener(new ContactsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(ConstantUtil.USER,IMApp.linkManList.get(position));
            startActivity(intent);
            }
        });
    }


}
