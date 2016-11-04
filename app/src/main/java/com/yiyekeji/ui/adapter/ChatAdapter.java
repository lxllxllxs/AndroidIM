package com.yiyekeji.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyekeji.dao.ChatMsgAbstract;
import com.yiyekeji.im.R;

import java.util.List;

/**
 * 两个布局
 * 收件人（左）和发件人（右 本用户）
 *
 * Created by lxl on 2016/10/25.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ChatMsgAbstract> messages;
    public ChatAdapter(Context context, List<ChatMsgAbstract> messages) {
        this.messages=messages;
        mInflater = LayoutInflater.from(context);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        TextView tvContent;
    }
        @Override
        public int getItemCount()
        {
            return messages.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            return messages.get(i).getIsReceiver()?setReceiverView(viewGroup):setSenderView(viewGroup);
        }

        /**
         * 设置布局控件内容
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            viewHolder.tvContent.setText(messages.get(i).getContent());
            if(mOnItemClickLitener!=null){
                viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.tvContent, i);
                    }
                });
            }
        }


        private ViewHolder setSenderView(ViewGroup viewGroup){
            View view = mInflater.inflate(R.layout.item_chat_adapter_sender, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_sender_msg);

            return viewHolder;
        }

        private ViewHolder setReceiverView(ViewGroup viewGroup){
            View view = mInflater.inflate(R.layout.item_chat_adapter_sender, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_sender_msg);

            return viewHolder;
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
         * @param mOnItemClickLitener
         */
        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }


}
