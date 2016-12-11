package com.yiyekeji.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiyekeji.IMApp;
import com.yiyekeji.bean.UserInfo;
import com.yiyekeji.im.R;
import com.yiyekeji.impl.IInformation;
import com.yiyekeji.ui.view.CircleImageView;
import com.yiyekeji.ui.view.NumberView;
import com.yiyekeji.utils.PicassoUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lxl on 2016/10/25.
 * 采用固定样式
 *
 */
public class InformAdapter extends RecyclerView.Adapter<InformAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Map<UserInfo, IInformation> chatMap;
    private List<UserInfo> keyList = new ArrayList<>();

    public InformAdapter(Context context, Map<UserInfo, IInformation> hashMap) {
        mInflater = LayoutInflater.from(context);
        setData(hashMap);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            AutoUtils.auto(arg0);
        }
        RelativeLayout rlContainer;
        CircleImageView civHead;//图标、头像
        TextView tvOtherSide;//对方（不用发送者这类有相对意思的）
        TextView tvMain;//主要简略内容
        TextView tvDate;//消息日期
        NumberView numberView;//消息数
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    public void setData(Map<UserInfo, IInformation> hashMap) {
        this.chatMap = hashMap;
        keyList.clear();
        Iterator iterator = chatMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            keyList.add((UserInfo) entry.getKey());
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

        viewHolder.rlContainer=(RelativeLayout)view.findViewById(R.id.rl_container);
        viewHolder.civHead = (CircleImageView) view.findViewById(R.id.civ_head);
        viewHolder.tvOtherSide = (TextView) view.findViewById(R.id.tv_userName);
        viewHolder.tvMain = (TextView) view.findViewById(R.id.tv_chatMessage);
        viewHolder.tvDate = (TextView) view.findViewById(R.id.tv_date);
        viewHolder.numberView=(NumberView)view.findViewById(R.id.numberview);

        return viewHolder;
    }

    /**显示最新的一条信息
     * 设置布局控件内容
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        UserInfo userInfo = keyList.get(i);
       final IInformation iInformations = chatMap.get(userInfo);
        PicassoUtil.setBitmapToView(iInformations.getHead(),viewHolder.civHead);
        viewHolder.tvOtherSide.setText(iInformations.getOtherSide());
        viewHolder.tvMain.setText(iInformations.getMain());
        viewHolder.numberView.setNumber(userInfo.getUnRead());//未读消息记录到用户字段里
        if (mOnItemClickLitener != null) {
            viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.rlContainer,IMApp.getUserInfo(iInformations.getOtherSide()));
                }
            });
        }
    }

    /**
     * 自定义一个回调点击函数
     *
     * @author lxl
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, UserInfo userInfo);
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
