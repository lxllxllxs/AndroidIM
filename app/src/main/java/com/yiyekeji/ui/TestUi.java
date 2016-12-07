package com.yiyekeji.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.yiyekeji.Config;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.activity.base.BaseActivity;
import com.yiyekeji.ui.view.DividerItemDecoration;
import com.yiyekeji.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import test.MyScrollView;
import test.adapter.TestAdapter;
import test.bean.MyType;

/**
 * Created by Administrator on 2016/11/19.
 */
public class TestUi extends BaseActivity implements MyScrollView.OnScrollChangeListener {

    @InjectView(R.id.recylerView)
    RecyclerView recylerView;
    @InjectView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);
//        initView();
        initWebSocket();
    }

    private void initWebSocket() {
        WebSocketConnection cn=null;
        for (int i=0;i<5;i++){
            try {
                cn= new WebSocketConnection();
                cn.connect(Config.IM_URL, new WebSocket.ConnectionHandler() {
                    @Override
                    public void onOpen() {
                        LogUtil.d("open:","sdas");
                    }
                    @Override
                    public void onClose(int code, String reason) {
                    }
                    @Override
                    public void onTextMessage(String payload) {
                    }
                    @Override
                    public void onRawTextMessage(byte[] payload) {
                    }
                    @Override
                    public void onBinaryMessage(byte[] payload) {
                    }
                });
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
            connectionList.add(cn);
        }
    }

    private List<WebSocketConnection> connectionList = new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
    }

    private TestAdapter mAdapter;
    ArrayList<MyType> list = new ArrayList<>();
    private  int textViewHeight;
    private float scrollviewHeight, screenHeight;


    @Override
    public void initView() {
        for (int i = 0; i < 50; i++) {
            MyType myType = new MyType();
            myType.setName("原哈哈" + i);
            list.add(myType);
        }
        mAdapter = new TestAdapter(this, list);
        recylerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(mAdapter);
        ViewTreeObserver vto = recylerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recylerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollviewHeight=recylerView.getHeight();
                textViewHeight=(int) scrollviewHeight/list.size();
                LogUtil.d("scrollviewHeight",textViewHeight+"");
            }
        });
        mAdapter.setOnItemClickLitener(new TestAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int postion) {
                recylerView.scrollBy(0,postion* textViewHeight);

            }
        });

    }


    @Override
    public void onScrollChange(float Y) {
    }

    @OnClick(R.id.tv)
    public void onClick() {
    }
}
