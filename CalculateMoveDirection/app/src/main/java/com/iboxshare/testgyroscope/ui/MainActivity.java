package com.iboxshare.testgyroscope.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iboxshare.testgyroscope.R;

/**
 * Created by KN on 16/11/14.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_main_motion_sensoring_mode_btn)
                .setOnClickListener(this);

        findViewById(R.id.activity_main_button_mode_btn)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_main_motion_sensoring_mode_btn:
                Log.e(TAG,"体感模式");
                startActivity(new Intent(MainActivity.this
                        ,MotionSensoringModeActivity.class));
                break;
            case R.id.activity_main_button_mode_btn:
                Log.e(TAG,"按键模式");
                startActivity(new Intent(MainActivity.this
                        ,ButtonModeActivity.class));
                break;
        }
    }
}
