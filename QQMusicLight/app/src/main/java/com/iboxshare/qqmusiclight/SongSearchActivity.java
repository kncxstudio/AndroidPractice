package com.iboxshare.qqmusiclight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by KN on 16/7/5.
 */
public class SongSearchActivity extends AppCompatActivity implements View.OnClickListener{
    EditText searchSongsET;
    Button searchBtn;
    String searchSongsContent;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_songs);


        bindViews();
        initViews();
        searchBtn.setOnClickListener(this);
    }

    void bindViews(){
        searchSongsET = (EditText) findViewById(R.id.searchSongs_searchET);
        searchBtn = (Button) findViewById(R.id.searchSongs_searchBtn);
        toolbar = (Toolbar) findViewById(R.id.toolbar_default);
    }

    void initViews(){
        toolbar.setTitle("搜索歌曲");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchSongs_searchBtn:
                searchSongsContent = searchSongsET.getText().toString();
                break;
        }
    }

    //设置toolbar返回键监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://android.R.id.home代表左上角的返回键
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
