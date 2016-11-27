package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.fragment.ContactsFragment;
import com.yiyekeji.ui.fragment.InformationFragment;
import com.yiyekeji.ui.view.StatusBarCompat;

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
    @InjectView(R.id.fragment)
    LinearLayout fragment;
    @InjectView(R.id.tv_setting)
    TextView tvSetting;

    int color_gray, color_bule;
    @InjectView(R.id.iv_contacts)
    ImageView ivContacts;
    @InjectView(R.id.iv_information)
    ImageView ivInformation;
    @InjectView(R.id.iv_setting)
    ImageView ivSetting;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ll_contacts)
    LinearLayout llContacts;
    @InjectView(R.id.ll_information)
    LinearLayout llInformation;
    @InjectView(R.id.ll_setting)
    LinearLayout llSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        ButterKnife.inject(this);
        initView();
        initViewPager();
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //定制状态栏颜色使用此方法
        //StatusBarCompat.compat(this, 0xFFFF0000);
        StatusBarCompat.compat(this);
        color_gray = ContextCompat.getColor(this, R.color.gray_black);
        color_bule = ContextCompat.getColor(this, R.color.theme_blue);
    }

    public void initViewPager() {
        contactsFragment = new ContactsFragment();
        informationFragment = new InformationFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment, contactsFragment)
//                .add(R.id.fragment, informationFragment)
                .show(contactsFragment)
                .commit();
    }

    FragmentManager fm;

    private void revertTab() {
        tvContacts.setTextColor(color_gray);
        tvInfo.setTextColor(color_gray);
        tvSetting.setTextColor(color_gray);

        ivContacts.setImageResource(R.mipmap.contacts);
        ivInformation.setImageResource(R.mipmap.information);
        ivSetting.setImageResource(R.mipmap.setting);
    }

    @OnClick({R.id.ll_contacts, R.id.ll_information, R.id.ll_setting})
    public void onClick(View view) {
        revertTab();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(contactsFragment).hide(informationFragment);
        switch (view.getId()) {
            case R.id.ll_contacts:
                tvTitle.setText("联系人");
                ft.show(contactsFragment);
                tvContacts.setTextColor(color_bule);
                ivContacts.setImageResource(R.mipmap.ic_contacts);
                break;
            case R.id.ll_information:
                tvTitle.setText("消息");
                ft.show(informationFragment);
                tvInfo.setTextColor(color_bule);
                ivInformation.setImageResource(R.mipmap.ic_infomation);
                break;
            case R.id.ll_setting:
                tvTitle.setText("设置");
                tvSetting.setTextColor(color_bule);
                ivSetting.setImageResource(R.mipmap.ic_setting);
                break;
        }
        ft.commit();
    }
}
