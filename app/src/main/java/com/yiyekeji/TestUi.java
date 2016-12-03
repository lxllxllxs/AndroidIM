package com.yiyekeji;

import android.os.Bundle;

import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;

import de.tavendo.autobahn.WebSocketException;

/**
 * Created by Administrator on 2016/11/19.
 */
public class TestUi  extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try {
            for (int i=0;i<100;i++) {
                new TestWebSocket().connect();
            }
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {

    }
}
