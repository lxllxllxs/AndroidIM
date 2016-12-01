package test.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyekeji.im.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import test.bean.MyType;

/**
 * Created by lxl on 2016/10/25.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private int color_gray;
    private LayoutInflater mInflater;
    private ArrayList<MyType> keyList = new ArrayList<>();

    public TestAdapter(Context context,  ArrayList<MyType> datas) {
        mInflater = LayoutInflater.from(context);
        this.keyList = datas;
        color_gray= ContextCompat.getColor(context,R.color.gray);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            AutoUtils.auto(arg0);
        }
        TextView tvUserName;
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }


    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.textview ,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvUserName = (TextView) view.findViewById(R.id.tv);
        return viewHolder;
    }

    /**显示最新的一条信息
     * 设置布局控件内容
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvUserName.setBackgroundColor(color_gray);
        if (keyList.get(i).isStatus()){
            viewHolder.tvUserName.setBackgroundColor(Color.BLUE);
        }
        viewHolder.tvUserName.setText(keyList.get(i).getName());

        viewHolder.tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (MyType type : keyList) {
                    type.setStatus(false);
                }
                keyList.get(i).setStatus(true);
                notifyDataSetChanged();
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(v,i);
                }
            }
        });
    }

    /**
     * 自定义一个回调点击函数
     *
     * @author lxl
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int postion);
    }

    public OnItemClickLitener mOnItemClickLitener;

    /**
     * 点击事件设置回调
     *
     * @param mOnItemClickLitener
     */
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
