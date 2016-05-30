package com.iboxshare.qqmusiclight;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iboxshare.qqmusiclight.adapter.SongInfoAdapter;
import com.iboxshare.qqmusiclight.object.SongInfo;
import com.iboxshare.qqmusiclight.util.PostTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private static int naviViewSelectedItemID = R.id.top_songs;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final String json = msg.getData().getString("json");
            final List<SongInfo> list = new ArrayList<SongInfo>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray songList = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("songlist");
                        for(int i = 0;i<songList.length();i++){
                            list.add(gson.fromJson(songList.get(i).toString(),SongInfo.class));
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SongInfoAdapter songInfoAdapter = new SongInfoAdapter(list);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(songInfoAdapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initViews();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json =PostTool.getTopSongs("5");
                    if(json != null){
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("json",json);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_default);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout);
        recyclerView = (RecyclerView)findViewById(R.id.activity_topSong_recyclerView);
    }

    private void initViews() {
        mToolbar.setTitle("热榜");
        mNavigationView.getMenu().getItem(0).setChecked(true);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        setListenerForNaviView(mNavigationView);
    }

    private void setListenerForNaviView(NavigationView mNavigationView){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (naviViewSelectedItemID == itemId){
                    drawerLayout.closeDrawers();
                    return false;
                }else{
                    item.setChecked(true);
                    switch (itemId){
                        case R.id.top_songs:
                            naviViewSelectedItemID = R.id.top_songs;
                            break;
                        case R.id.search_songs:
                            naviViewSelectedItemID = R.id.search_songs;
                            break;
                        case R.id.exit:
                            System.exit(1);
                            break;
                    }
                    drawerLayout.closeDrawers();
                    return false;
                }


            }
        });
    }

}
