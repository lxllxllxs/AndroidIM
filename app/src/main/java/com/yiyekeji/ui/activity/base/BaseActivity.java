package com.yiyekeji.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/23.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private Toast shortToast, longToast ;

    protected void showShortToast(CharSequence text) {
        if(shortToast==null){
            shortToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }else{
            shortToast.setText(text);
        }
        shortToast.show();
    }

    public void startActivity(Class<?extends BaseActivity> activity){
        Intent intent =new Intent(this,activity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }



}
