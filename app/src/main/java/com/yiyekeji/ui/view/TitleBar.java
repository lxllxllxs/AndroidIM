package com.yiyekeji.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiyekeji.im.R;


/**
 * 左返回 中间标题
 * Created by lxl on 16/08/16.
 */
public class TitleBar extends RelativeLayout {

    private Context context;
    private RelativeLayout containerLayout;
    private TextView bigTitleTv;

    public TitleBar(Context context) {
        super(context);

    }
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 传activity设置标题
     * @param activity
     */
    public void initView(final Activity activity) {
        context=activity;
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this);
        containerLayout = (RelativeLayout) findViewById(R.id.rl_title_bar_container);
        bigTitleTv = (TextView) findViewById(R.id.tv_title_bar_big_title);
        setTitleText(activity.getTitle());
    }

    /**
     * 传context 手动设置标题
     * @param context
     */
    public void initView(Context context, String title) {
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this);
        containerLayout = (RelativeLayout) findViewById(R.id.rl_title_bar_container);
        bigTitleTv = (TextView) findViewById(R.id.tv_title_bar_big_title);
        bigTitleTv.setText(title);
    }


    public void setBarBackgroundColor(int color) {
        containerLayout.setBackgroundColor(color);
    }

    public void setTitleText(CharSequence title) {
        bigTitleTv.setText(title);
    }



}
