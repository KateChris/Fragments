package com.lovejjfg.fragments.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lovejjfg.fragments.R;
import com.lovejjfg.fragments.model.ModelBean;
import com.lovejjfg.fragments.pagetransformer.MyListView;


/**
 * Created by Joe on 2016/10/10.
 * Email lovejjfg@gmail.com
 */

public class NestedHolder extends RecyclerView.ViewHolder {

    private final TextView mTittle;
    private final TextView mContent;
    private final TextView mDate;
    private final ImageView mImg;
    private final ListView mList;
    private final Context context;
    private final String[] stringArray;

    public NestedHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        stringArray = context.getResources().getStringArray(R.array.names);
        mTittle = (TextView) itemView.findViewById(R.id.tv_tittle);
        mContent = (TextView) itemView.findViewById(R.id.tv_content);
        mDate = (TextView) itemView.findViewById(R.id.tv_time);
        mImg = (ImageView) itemView.findViewById(R.id.image);
        mList = (MyListView) itemView.findViewById(R.id.list_view);

    }

    public void onBind(ModelBean position) {
        mTittle.setText(position.getTittle());
        mList.setAdapter(new ArrayAdapter<>(context, R.layout.layout_text, stringArray));

    }
}
