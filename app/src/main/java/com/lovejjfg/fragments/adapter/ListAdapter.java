package com.lovejjfg.fragments.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.fragments.OnItemClickListener;
import com.lovejjfg.fragments.R;
import com.lovejjfg.fragments.holder.ListBigImgHolder;
import com.lovejjfg.fragments.holder.NestedHolder;
import com.lovejjfg.fragments.model.ModelBean;
import com.lovejjfg.powerrecycle.RefreshRecycleAdapter;

import static com.lovejjfg.fragments.Constants.TYPE_BIG_IMG;
import static com.lovejjfg.fragments.Constants.TYPE_NORMAL;


/**
 * Created by Joe on 2016/10/10.
 * Email lovejjfg@gmail.com
 */

public class ListAdapter extends RefreshRecycleAdapter<ModelBean> {
    private int currentType;

    public ListAdapter(int currentType) {
        this.currentType = currentType;
    }

    @Override
    public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
        View inflate;
        switch (currentType) {
            case TYPE_NORMAL:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_nested, parent, false);
                return new NestedHolder(inflate);
            case TYPE_BIG_IMG:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_big_image, parent, false);
                return new ListBigImgHolder(inflate);
        }
        throw new IllegalArgumentException("没有对应的类型！！");

    }

    @Override
    public void onViewHolderBind(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, null, position);
                }
            }
        });
        ModelBean modelBean = list.get(position);
        modelBean.setTittle("这是" + position);
        switch (currentType) {
            case TYPE_NORMAL:
                ((NestedHolder) holder).onBind(modelBean);
                break;
            case TYPE_BIG_IMG:
                ((ListBigImgHolder) holder).onBind(modelBean);
                break;

        }

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private OnItemClickListener listener;
}
