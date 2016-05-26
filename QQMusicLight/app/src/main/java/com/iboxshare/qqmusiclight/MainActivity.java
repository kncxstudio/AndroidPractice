package com.iboxshare.qqmusiclight;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.iboxshare.qqmusiclight.util.PostTool;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private static int naviViewSelectedItemID = R.id.top_songs;

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
                    System.out.println(PostTool.getTopSongs("5"));
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
