package com.iboxshare.qqmusiclight.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iboxshare.qqmusiclight.R;

/**
 * Created by KNCX on 2016/5/26.
 */
public class SongInfoVH extends RecyclerView.ViewHolder{
    ImageView albumImg,downloadUrl;
    TextView songName,singerName,albumName;
    public SongInfoVH(View itemView) {
        super(itemView);
        albumImg = (ImageView)itemView.findViewById(R.id.songinfo_albumImg);
        downloadUrl = (ImageView)itemView.findViewById(R.id.songinfo_downloadUrl);
        songName = (TextView)itemView.findViewById(R.id.songinfo_songName);
        singerName = (TextView)itemView.findViewById(R.id.songinfo_singerName);
        albumName = (TextView)itemView.findViewById(R.id.songinfo_albumName);
    }

    public ImageView getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(ImageView albumImg) {
        this.albumImg = albumImg;
    }

    public ImageView getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(ImageView downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public TextView getAlbumName() {
        return albumName;
    }

    public void setAlbumName(TextView albumName) {
        this.albumName = albumName;
    }

    public TextView getSongName() {
        return songName;
    }

    public void setSongName(TextView songName) {
        this.songName = songName;
    }

    public TextView getSingerName() {
        return singerName;
    }

    public void setSingerName(TextView singerName) {
        this.singerName = singerName;
    }
}
