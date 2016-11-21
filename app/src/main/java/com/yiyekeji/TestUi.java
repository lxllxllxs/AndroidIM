package com.yiyekeji;

import android.os.Bundle;
import android.widget.ImageView;

import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.utils.PicassoUtil;

/**
 * Created by Administrator on 2016/11/19.
 */
public class TestUi  extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView view = new ImageView(this);
        String url="http://192.168.1.102:8080/im/test.png";
        PicassoUtil.setBitmapToView(url,view);
        setContentView(view);
    }

    @Override
    public void initView() {

    }
}
