package com.iboxshare.testgyroscope.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.iboxshare.testgyroscope.R;
import com.iboxshare.testgyroscope.utils.BLEUtils;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

/**
 * Created by KN on 16/11/14.
 */

public class ButtonModeActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ButtonModeActivity";
    private Context context = this;
    BluetoothSPP bt = new BluetoothSPP(context);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_mode);

        $(R.id.activity_button_mode_start_btn).setOnClickListener(this);
        $(R.id.activity_button_mode_up_btn).setOnClickListener(this);
        $(R.id.activity_button_mode_down_btn).setOnClickListener(this);
        $(R.id.activity_button_mode_left_btn).setOnClickListener(this);
        $(R.id.activity_button_mode_right_btn).setOnClickListener(this);

        if (!bt.isBluetoothAvailable()) {
            Log.e(TAG, "初始化失败");
        } else {
            bt.setupService();
            bt.startService(BluetoothState.DEVICE_OTHER);
            Intent intent = new Intent(context, DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bt.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e(TAG, data.getExtras().getString(BluetoothState.EXTRA_DEVICE_ADDRESS));
                bt.connect(data);
            } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
                if (resultCode == Activity.RESULT_OK) {
                    bt.setupService();
                    bt.startService(BluetoothState.DEVICE_OTHER);
                } else {
                    // Do something if user doesn't choose any device (Pressed back)
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_button_mode_start_btn:
                Log.e(TAG,"===START===");
                bt.send(BLEUtils.hexStringToBytes("AA"),false);
                break;
            case R.id.activity_button_mode_up_btn:
                Log.e(TAG,"===UP===");
                bt.send(BLEUtils.hexStringToBytes("11"),false);
                break;
            case R.id.activity_button_mode_down_btn:
                Log.e(TAG,"===DOWN===");
                bt.send(BLEUtils.hexStringToBytes("33"),false);
                break;
            case R.id.activity_button_mode_left_btn:
                Log.e(TAG,"===LEFT===");
                bt.send(BLEUtils.hexStringToBytes("77"),false);
                break;
            case R.id.activity_button_mode_right_btn:
                Log.e(TAG,"===RIGHT===");
                bt.send(BLEUtils.hexStringToBytes("99"),false);
                break;
        }
    }

    View $(int id){
        return findViewById(id);
    }
}
