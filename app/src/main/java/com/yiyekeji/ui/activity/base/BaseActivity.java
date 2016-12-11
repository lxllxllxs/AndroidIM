package com.yiyekeji.ui.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yiyekeji.IMApp;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.view.LoadDialog;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Administrator on 2016/10/23.
 */
public  abstract  class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {

    private static LoadDialog mdDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        IMApp.addActivity(this);
    }

    public abstract void initView() ;

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

    /**
     * 创建loadDialog
     *
     * @param msg
     */
    protected static void showLoadDialog(String msg) {
        if (mdDialog == null) {
            mdDialog = new LoadDialog(IMApp.getContext(), R.layout.layout_load_dialog,
                    R.style.DialogLogin);
            mdDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        initProgressDialog(msg);
        // 默认不能按屏幕取消dialog
        mdDialog.setCanceledOnTouchOutside(false);
        mdDialog.show();

    }
    /**
     * 获取dialog对象
     *
     * @return
     */
    protected LoadDialog getLoadDialog() {
        return mdDialog;
    }
    /**
     * 载入loadDialog控件
     *
     * @param msg
     */
    protected static void initProgressDialog(String msg) {
        View view = mdDialog.getEntryView();
        ((TextView) view.findViewById(R.id.CtvInitTip)).setText(msg);
        if (TextUtils.isEmpty(msg)) {
            ((TextView) view.findViewById(R.id.CtvInitTip))
                    .setVisibility(View.GONE);
        }
    }





    @Override
    public void onClick(View v) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        IMApp.removeActivity(this);
    }
}
