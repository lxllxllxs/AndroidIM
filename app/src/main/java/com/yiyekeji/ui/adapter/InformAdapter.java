package com.yiyekeji.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.dao.ChatMessage;
import com.yiyekeji.im.R;
import com.yiyekeji.ui.view.CircleImageView;
import com.yiyekeji.utils.PicassoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lxl on 2016/10/25.
 */
public class InformAdapter extends RecyclerView.Adapter<InformAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private HashMap<String, ArrayList<ChatMessage>> chatMap;
    private List<ChatMessage> messageList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();

    public InformAdapter(Context context, HashMap<String, ArrayList<ChatMessage>> hashMap) {
        setData(hashMap);
        mInflater = LayoutInflater.from(context);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        CircleImageView civHead;
        TextView tvUserName;
        TextView tvChatMessage;
        TextView tvDate;

    }

    @Override
    public int getItemCount() {
        return chatMap.size();
    }

    public void setData(HashMap<String, ArrayList<ChatMessage>> hashMap) {
        this.chatMap = hashMap;
        keyList.clear();
        Iterator iterator = chatMap.entrySet().iterator();
        while (iterator.hasNext()) {
            keyList.add((String) iterator.next());
        }
        notifyDataSetChanged();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_information_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.civHead = (CircleImageView) view.findViewById(R.id.civ_head);
        viewHolder.tvUserName = (TextView) view.findViewById(R.id.tv_userName);
        viewHolder.tvChatMessage = (TextView) view.findViewById(R.id.tv_chatMessage);
        viewHolder.tvDate = (TextView) view.findViewById(R.id.tv_date);

        return viewHolder;
    }

    /**
     * 设置布局控件内容
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        List<ChatMessage> chatMessageList = chatMap.get(keyList.get(i));
        ChatMessage chatMessage = chatMessageList.get(chatMessageList.size() - 1);//获取最新
        UserInfo info=getUserInfo(chatMessage);
        assert info != null;
        PicassoUtil.setBitmapToView(info.getImgUrl(),viewHolder.civHead);
        viewHolder.tvUserName.setText(chatMessage.getSenderId());
        viewHolder.tvChatMessage.setText(chatMessage.getContent());
        viewHolder.tvUserName.setText(chatMessage.getSenderId());

        if (mOnItemClickLitener != null) {
            viewHolder.tvUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.tvUserName, i);
                }
            });
        }
    }

    /**
     * 从全局变量中查找好友信息
     */
    private UserInfo getUserInfo(ChatMessage msg) {
        for (UserInfo info : IMApp.linkManList) {
            if (info.getUserId().equals(msg.getSenderId())) {
                return info;
            }
        }
        return null;
    }

    /**
     * 自定义一个回调点击函数
     *
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
