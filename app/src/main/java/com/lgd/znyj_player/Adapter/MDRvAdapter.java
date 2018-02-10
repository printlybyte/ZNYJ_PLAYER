package com.lgd.znyj_player.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.lgd.znyj_player.Applacationx;
import com.lgd.znyj_player.R;
import com.lgd.znyj_player.bean.YJBean;
import com.lgd.znyj_player.greendao.gen.DaoUtils;
import com.lgd.znyj_player.utils.RecycleItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This implementation of {@link RecyclerView.Adapter}
 * <p>
 * Created by KyoWang on 2016/06/30 .
 */
public class MDRvAdapter extends RecyclerView.Adapter<MDRvAdapter.ViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    /**
     * 展示数据
     */
    private List<YJBean> mData;

    /**
     * 事件回调监听
     */
    private MDRvAdapter.OnItemClickListener onItemClickListener;

    public MDRvAdapter(List<YJBean> data) {
        this.mData = data;
    }

    public void updateData(List<YJBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
//        mData.add(0, "new Item");
        notifyItemInserted(0);
    }

    /**
     * 删除Item
     */
    public void deleteItem() {
        if (mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(0);
        notifyItemRemoved(0);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MDRvAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        holder.mDevices.setText(mData.get(position).getMYJdevice());
        holder.mUrl.setText(mData.get(position).getMYJip());
        Glide.with(Applacationx.getAppContext()).load(R.mipmap.devices_image).placeholder(R.drawable.ic_back_icon).into(holder.mImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onItemDelete(int positon) {
        DaoUtils.deleteItem(mData.get(positon).getMYJdevice());
        mData.remove(positon);
        notifyItemRemoved(positon);
        onItemClickListener.onItemChangeNotifacion(positon);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);//交换数据
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mDevices;
        TextView mUrl;
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mDevices = (TextView) itemView.findViewById(R.id.item_tv);
            mUrl = (TextView) itemView.findViewById(R.id.item_tv_url);
            mImage = (ImageView) itemView.findViewById(R.id.devices_image);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onItemChangeNotifacion(int posion);
    }
}
