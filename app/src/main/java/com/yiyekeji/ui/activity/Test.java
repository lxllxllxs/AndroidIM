package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lxl on 2016/11/8.
 */
public class Test extends BaseActivity {

    TextView get;
    int Count=0;

    LinkedBlockingDeque<String> blockingDeque=new LinkedBlockingDeque<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    @Override
    public void initView() {

    }

}
