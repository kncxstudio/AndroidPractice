package com.example.kncx.recyclerviewtest.viewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kncx.recyclerviewtest.R;

/**
 * Created by KNCX on 2016/5/17.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_view);
    }
}
