package com.iboxshare.testgyroscope.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iboxshare.testgyroscope.R;

/**
 * Created by KN on 16/11/14.
 */

public class SplashActivity extends AppCompatActivity {
    private long launchTime = System.currentTimeMillis();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (System.currentTimeMillis() - launchTime == 2000){
                        startActivity(new Intent(SplashActivity.this
                                ,MainActivity.class));
                        SplashActivity.this.finish();
                    }
                }
            }
        }).start();

    }
}
