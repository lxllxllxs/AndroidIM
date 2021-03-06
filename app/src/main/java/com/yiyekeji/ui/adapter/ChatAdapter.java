package com.yiyekeji.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.im.R;
import com.yiyekeji.utils.GlideUtil;
import com.yiyekeji.utils.LogUtil;

import java.util.List;

/**
 * 两个布局
 * 收件人（左）和发件人（右 本用户）
 *
 * Created by lxl on 2016/10/25.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<ChatMessage> messages;
    private final int LEFT=0x123;
    private final int RIGHT=0x122;

    private UserInfo userInfo = IMApp.userInfo;
    private String selfId=userInfo.getUserId();
    private final UserInfo otherSide= IMApp.otherSide;
    public ChatAdapter(Context context, List<ChatMessage> messages) {
        this.messages=messages;
        mInflater = LayoutInflater.from(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        TextView tvContent;
        ImageView iv_head;
        ImageView ivContent;
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
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
            ViewHolder viewHolder = null;
            switch (type){
                case LEFT:
                    viewHolder=setLeftView(viewGroup);
                    break;
                case RIGHT:
                    viewHolder=setRightView(viewGroup);
                    break;
            }
            return viewHolder;
        }


    boolean isLeft;
    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().equals(selfId)){
            isLeft = false;
            return RIGHT;
        }else {
            isLeft = true;
            return LEFT;
        }
    }

        /**
         * 设置布局控件内容
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            LogUtil.d("onBindViewHolder", userInfo);
            ChatMessage message=messages.get(i);
            switch (message.getMessageType()){
                case "0":
                    if (viewHolder.tvContent.getVisibility() != View.VISIBLE) {
                        viewHolder.tvContent.setVisibility(View.VISIBLE);
                    }
                    viewHolder.ivContent.setVisibility(View.GONE);
                    viewHolder.tvContent.setText(message.getContent());
                    break;
                case "1":
                    viewHolder.tvContent.setVisibility(View.GONE);
                    if (viewHolder.ivContent.getVisibility() != View.VISIBLE) {
                        viewHolder.ivContent.setVisibility(View.VISIBLE);
                    }
                    //如果是自己发出的 从本地加载，否则从网络加载
                    if (message.getSenderId().equals(userInfo.getUserId())) {
                        viewHolder.ivContent.setImageURI(Uri.parse(message.getContent()));
                    } else {
                        LogUtil.d("asReceiver",message.getContent());
                        GlideUtil.setBitmapToView(message.getContent(), viewHolder.ivContent);
                    }
                    break;
            }
            if (isLeft) {
                GlideUtil.setBitmapToView(otherSide.getImgUrl(), viewHolder.iv_head);
            } else {
                GlideUtil.setBitmapToView(userInfo.getImgUrl(), viewHolder.iv_head);
            }
            if(mOnItemClickLitener!=null){
                viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(viewHolder.tvContent, i);
                    }
                });
            }
        }

        private ViewHolder setRightView(ViewGroup viewGroup){
            View view = mInflater.inflate(R.layout.item_chat_adapter_right, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.ivContent = (ImageView) view.findViewById(R.id.iv_contant);
            viewHolder.iv_head = (ImageView) view.findViewById(R.id.iv_head_right);
            return viewHolder;
        }

        private ViewHolder setLeftView(ViewGroup viewGroup){
            View view = mInflater.inflate(R.layout.item_chat_adapter_left, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.tvContent = (TextView)view.findViewById(R.id.tv_content);
            viewHolder.ivContent = (ImageView) view.findViewById(R.id.iv_contant);
            viewHolder.iv_head = (ImageView) view.findViewById(R.id.iv_head_left);
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
