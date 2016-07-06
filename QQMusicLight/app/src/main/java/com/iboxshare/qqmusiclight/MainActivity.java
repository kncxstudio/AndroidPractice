package com.iboxshare.qqmusiclight;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.iboxshare.qqmusiclight.fragment.SearchSongsFragment;

/**
 * Created by KN on 16/7/6.
 */
public class MainActivity extends AppCompatActivity{
    private Fragment topSongsFragment,searchSongsFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private int naviViewSelectedItemID;
    private static FrameLayout contentLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initViews();

    }


    //绑定View
    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_default);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerLayout);
        contentLayout = (FrameLayout) findViewById(R.id.activity_main_content);
        searchSongsFragment = new SearchSongsFragment();
    }

    //初始化View
    private void initViews() {
        mToolbar.setTitle("首页");
        mNavigationView.getMenu().getItem(0).setChecked(true);


        setSupportActionBar(mToolbar);//设置ActionBar
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置抽屉打开关闭动作
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.app_name, R.string.app_name) {//s
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

        //加载Fragment
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main_content,searchSongsFragment);
        fragmentTransaction.hide(searchSongsFragment);
        fragmentTransaction.commit();

    }

    //给抽屉中的navigationview设置监听器
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
                            if (getSupportActionBar() != null){
                                getSupportActionBar().setTitle("搜索歌曲");
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.show(searchSongsFragment);
                                fragmentTransaction.commit();
                            }
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
