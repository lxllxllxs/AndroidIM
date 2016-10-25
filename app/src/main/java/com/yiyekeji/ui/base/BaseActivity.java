package com.yiyekeji.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yiyekeji.utils.WebSocketUtil;

/**
 * Created by Administrator on 2016/10/23.
 */
public class BaseActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebSocketUtil.connect(this);
    }




    @Override
    public void onClick(View v) {

    }
}
