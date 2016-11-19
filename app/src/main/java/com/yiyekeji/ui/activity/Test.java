package com.yiyekeji.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.utils.LogUtil;

import java.util.concurrent.LinkedBlockingDeque;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lxl on 2016/11/8.
 */
public class Test extends BaseActivity {

    @InjectView(R.id.put)
    TextView put;
    @InjectView(R.id.get)
    TextView get;
    @InjectView(R.id.insertFirst)
    TextView insertFirst;
    int Count=0;

    LinkedBlockingDeque<String> blockingDeque=new LinkedBlockingDeque<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.put, R.id.get, R.id.insertFirst})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.put:
                Count++;
                blockingDeque.add(Count+"");
                LogUtil.d("put", blockingDeque.size());
                break;
            case R.id.get:
                try {
                    LogUtil.d("get",  blockingDeque.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.insertFirst:
                Count++;
                try {
                    blockingDeque.putFirst(Count+"");
                    LogUtil.d("insertFirst",  blockingDeque.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
