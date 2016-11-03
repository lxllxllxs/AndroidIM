package com.yiyekeji.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.fragment.ContactsFragment;
import com.yiyekeji.ui.fragment.InformationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MainFragmentActivity extends BaseActivity {

    List<Fragment> fragmentList = new ArrayList<>();
    ContactsFragment cf;
    InformationFragment informationFragment;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.tv_contacts)
    TextView tvContacts;
    @InjectView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        ButterKnife.inject(this);
        initViewPager();
    }

    public void initViewPager() {
        fragmentList = new ArrayList<Fragment>();
        cf = new ContactsFragment();
        informationFragment = new InformationFragment();
        fragmentList.add(cf);
        fragmentList.add(informationFragment);
        viewpager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
        viewpager.setOffscreenPageLimit(fragmentList.size());
        viewpager.setCurrentItem(0);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tvContacts.setTextColor(Color.WHITE);
                        tvContacts.setBackgroundColor(getResources().getColor(R.color.blue_light));
                        tvInfo.setTextColor(getResources().getColor(R.color.blue_light));
                        tvInfo.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        tvInfo.setTextColor(Color.WHITE);
                        tvInfo.setBackgroundColor(getResources().getColor(R.color.blue_light));
                        tvContacts.setTextColor(getResources().getColor(R.color.blue_light));
                        tvContacts.setBackgroundColor(Color.WHITE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
