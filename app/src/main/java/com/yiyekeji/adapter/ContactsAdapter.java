package com.yiyekeji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyekeji.bean.User;
import com.yiyekeji.im.R;

import java.util.List;

/**
 * Created by lxl on 2016/10/25.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>
        {

    private LayoutInflater mInflater;
    private List<User> userList;
    public ContactsAdapter(Context context, List<User> userList) {
        this.userList=userList;
        mInflater = LayoutInflater.from(context);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }
        TextView tvId;
        TextView tvName;
        LinearLayout llContainer;
    }
        @Override
        public int getItemCount()
        {
            return userList.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.item_contacts_adapter, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvId= (TextView) view.findViewById(R.id.tvId);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.llContainer=(LinearLayout)view.findViewById(R.id.llContainer);
            return viewHolder;
        }


        /**
         * 设置布局控件内容
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            viewHolder.tvId.setText(userList.get(i).getUserId());
            viewHolder.tvName.setText(userList.get(i).getUsernName());
            if(mOnItemClickLitener!=null){
                viewHolder.llContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.llContainer, i);
                    }
                });
            }
        }

    /**
     * 自定义一个回调点击函数
     * @author lxl
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
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
