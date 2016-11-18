package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.fragment.ContactsFragment;
import com.yiyekeji.ui.fragment.InformationFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MainFragmentActivity extends BaseActivity {

    ContactsFragment contactsFragment;
    InformationFragment informationFragment;
    @InjectView(R.id.tv_contacts)
    TextView tvContacts;
    @InjectView(R.id.tv_info)
    TextView tvInfo;
//    @InjectView(R.id.fragment)
//    LinearLayout fragment;
    @InjectView(R.id.tv_setting)
    TextView tvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        ButterKnife.inject(this);
        initViewPager();
    }

    public void initViewPager() {
        contactsFragment = new ContactsFragment();
        informationFragment = new InformationFragment();
        fm= getSupportFragmentManager();
      /*  FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment,contactsFragment)
                .add(R.id.fragment,informationFragment)
                .show(contactsFragment)
                .commit();*/
    }
    FragmentManager fm;
    @OnClick({R.id.tv_contacts, R.id.tv_info, R.id.tv_setting})
    public void onClick(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(contactsFragment).hide(informationFragment);
        switch (view.getId()) {
            case R.id.tv_contacts:
                ft.show(contactsFragment);
                break;
            case R.id.tv_info:
                ft.show(informationFragment);
                break;
            case R.id.tv_setting:
                break;
        }
        ft.commit();
    }
}
