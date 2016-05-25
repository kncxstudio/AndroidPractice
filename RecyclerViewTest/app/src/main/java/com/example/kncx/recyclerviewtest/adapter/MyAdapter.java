package com.example.kncx.recyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kncx.recyclerviewtest.R;
import com.example.kncx.recyclerviewtest.myInterface.RecyclerViewOnClickListener;
import com.example.kncx.recyclerviewtest.viewHolder.MyViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by KNCX on 2016/5/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{
    public List<String> adapterData = null;
    RecyclerViewOnClickListener recyclerViewOnClickListener = null;
    MyViewHolder myViewHolder = null;
    public MyAdapter(List<String> data) {
        this.adapterData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        this.myViewHolder = holder;
        myViewHolder.textView.setText(adapterData.get(position));
    }


    @Override
    public int getItemCount() {
        return adapterData.size();
    }


    @Override
    public void onClick(View v) {
        if(recyclerViewOnClickListener != null){
            recyclerViewOnClickListener.onItemClick(v);
        }
    }

    public void setItemOnClickListener(RecyclerViewOnClickListener recyclerViewOnClickListener){
        this.recyclerViewOnClickListener = recyclerViewOnClickListener;
    }

    public void addItem(String a,int position) {

        adapterData.add(position,"haha");
        notifyDataSetChanged();
    }
}
