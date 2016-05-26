package com.iboxshare.qqmusiclight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.iboxshare.qqmusiclight.R;
import com.iboxshare.qqmusiclight.myInterface.RecyclerViewOnClickListener;
import com.iboxshare.qqmusiclight.object.SongInfo;
import com.iboxshare.qqmusiclight.viewHolder.SongInfoVH;

import java.util.List;

/**
 * Created by KNCX on 2016/5/26.
 */
public class SongInfoAdapter extends RecyclerView.Adapter<SongInfoVH> implements View.OnClickListener{
    public List<SongInfo> songList;
    private RecyclerViewOnClickListener recyclerViewOnClickListener = null;
    private SongInfoVH mViewHolder;
    private static ViewGroup mViewGroup;
    public SongInfoAdapter(List<SongInfo> list){
        songList = list;
    }
    @Override
    public SongInfoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        mViewGroup = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_info,parent,false);
        SongInfoVH vh = new SongInfoVH(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(SongInfoVH holder, int position) {
        mViewHolder = holder;
        mViewHolder.getSongName().setText(songList.get(position).getSongName());
        mViewHolder.getSingerName().setText(songList.get(position).getSingerName());
        mViewHolder.getAlbumName().setText(songList.get(position).getAlbumName());
        Glide.with(mViewGroup.getContext()).load(songList.get(position).getAlbumImgUrl()).into(mViewHolder.getAlbumImg());

    }
    @Override
    public int getItemCount() {
        return songList.size();
    }

    @Override
    public void onClick(View v) {
        if (recyclerViewOnClickListener != null){
            recyclerViewOnClickListener.onItemClick(v);
        }
    }

    public void setItemOnClickListener(RecyclerViewOnClickListener listener){
        this.recyclerViewOnClickListener = listener;

    }
}
