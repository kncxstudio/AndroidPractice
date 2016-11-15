package com.iboxshare.testgyroscope.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iboxshare.testgyroscope.R;
import com.iboxshare.testgyroscope.utils.BLEUtils;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MotionSensoringModeActivity extends AppCompatActivity {
    private String TAG = "MotionSensoringMode";
    private Context context = this;
    private TextView xTV, yTV, zTV, gxTV, gyTV, gzTV, gyroscopeXTV, gyroscopeYTV, gyroscopeZTV,userModeTV;
    private Sensor aSensor, gSensor, gyroscopeSensor;
    private SensorManager sensorManager;
    private Button startGameBtn;
    static double ax, ay, az, gx, gy, gz, gyroscopeX, gyroscopeY, gyroscopeZ;

    private SensorEventListener aListener, gListener;
    BluetoothSPP bt = new BluetoothSPP(context);

    //Flags
    public static String currentDirection = "null";
    public static int currentUserHoldMode;
    public static long lastChangedTime = System.currentTimeMillis();
    public static boolean allowSendData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensoring_mode);
        bindView();
        init();

        if (!bt.isBluetoothAvailable()) {
            Log.e(TAG, "初始化失败");
        } else {
            bt.setupService();
            bt.startService(BluetoothState.DEVICE_OTHER);
            Intent intent = new Intent(context, DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    //Log.e(TAG,"此线程在执行");
                    if (System.currentTimeMillis() - lastChangedTime > 1000){
                        if (allowSendData) {
                            //Log.e(TAG,"发送数据");
                            //bt.send(String.valueOf(lastChangedTime),true);
                            allowSendData = false;
                        }
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注册加速仪监听器
        sensorManager.registerListener(aListener, aSensor, SensorManager.SENSOR_DELAY_UI);


        //注册重力加速度监听器
        sensorManager.registerListener(gListener, gSensor, SensorManager.SENSOR_DELAY_UI);


    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(aListener);
        sensorManager.unregisterListener(gListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        bt.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    /**
     * 绑定View
     */

    void bindView() {
        xTV = (TextView) findViewById(R.id.loc_x);
        yTV = (TextView) findViewById(R.id.loc_y);
        zTV = (TextView) findViewById(R.id.loc_z);
        gxTV = (TextView) findViewById(R.id.g_x);
        gyTV = (TextView) findViewById(R.id.g_y);
        gzTV = (TextView) findViewById(R.id.g_z);
        userModeTV = (TextView) findViewById(R.id.user_mode_TV);
        gyroscopeXTV = (TextView) findViewById(R.id.gyroscope_x);
        gyroscopeYTV = (TextView) findViewById(R.id.gyroscope_y);
        gyroscopeZTV = (TextView) findViewById(R.id.gyroscope_z);
    }

    /**
     * 初始化
     */
    void init() {
        startGameBtn = (Button) findViewById(R.id.start_motion_sensoring_mode);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt.send(BLEUtils.hexStringToBytes("AA"),false);
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //X轴 左右
                //Log.e(TAG,""+event.values[0]);
                gyroscopeX = event.values[0];
                //Y轴 前后
                //Log.e(TAG,""+event.values[1]);
                gyroscopeY = event.values[1];
                //Z轴 上下
                //Log.e(TAG,""+event.values[2]);
                gyroscopeZ = event.values[2];

                gyroscopeXTV.setText("gyrosX: " + gyroscopeX);
                gyroscopeYTV.setText("gyrosY: " + gyroscopeY);
                gyroscopeZTV.setText("gyrosZ: " + gyroscopeZ);
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);

        //加速仪SensorEventListener
        aListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //X轴 左右
                //Log.e(TAG,""+event.values[0]);
                ax = event.values[0];
                //Y轴 前后
                //Log.e(TAG,""+event.values[1]);
                ay = event.values[1];
                //Z轴 上下
                //Log.e(TAG,""+event.values[2]);
                az = event.values[2];


                xTV.setText("ax: " + ax);
                yTV.setText("ay: " + ay);
                zTV.setText("az: " + az);

                //计算方向
                calculateDirection();




            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                Log.e(TAG,"onAccuracyChanged");
            }

        };


        //重力加速度SensorEventListener
        gListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //X轴 左右
                //Log.e(TAG,""+event.values[0]);
                gx = event.values[0];
                //Y轴 前后
                //Log.e(TAG,""+event.values[1]);
                gy = event.values[1];
                //Z轴 上下
                //Log.e(TAG,""+event.values[2]);
                gz = event.values[2];
                gxTV.setText("gx: " + gx);
                gyTV.setText("gy: " + gy);
                gzTV.setText("gz: " + gz);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


    }


    /**
     * 通过ax,ay,zy,gx,gy,gz计算设备移动方向
     */
    void calculateDirection() {


        //矫正加速度
        ax = 0.8 * ax + 0.2 * gx;
        ay = 0.8 * ay + 0.2 * gy;
        az = 0.8 * az + 0.2 * gz;


        //判断玩家手机握持方式
        currentUserHoldMode = calculateUserHoldMode();


        if (az - gz > 5.0) {
            Log.e(TAG,"向下移动");

            switch (currentUserHoldMode) {
                case 0:
                    currentDirection = "33";
                    break;
                case 1:
                    currentDirection = "99";
                    break;
                case 2:
                    currentDirection = "77";
                    break;
            }
            lastChangedTime = System.currentTimeMillis();
        } else if (az - gz < -5.0) {
            Log.e(TAG,"向上移动");
            switch (currentUserHoldMode) {
                case 0:
                    currentDirection = "11";
                    break;
                case 1:
                    currentDirection = "77";
                    break;
                case 2:
                    currentDirection = "99";
                    break;
            }
            lastChangedTime = System.currentTimeMillis();
        } else if (ax - gx > 5.0) {
            Log.e(TAG,"向左移动");
            switch (currentUserHoldMode) {
                case 0:
                    currentDirection = "77";
                    break;
                case 1:
                    currentDirection = "33";
                    break;
                case 2:
                    currentDirection = "11";
                    break;
            }
            lastChangedTime = System.currentTimeMillis();
        } else if (ax - gx < -5.0) {
            Log.e(TAG,"向右移动");
            switch (currentUserHoldMode) {
                case 0:
                    currentDirection = "99";
                    break;
                case 1:
                    currentDirection = "11";
                    break;
                case 2:
                    currentDirection = "33";
                    break;
            }
            lastChangedTime = System.currentTimeMillis();

        }
        if (System.currentTimeMillis() - lastChangedTime > 300){
            //Log.e(TAG,"传输通道打开");
            allowSendData = true;
            if (currentDirection != null) {
                bt.send(BLEUtils.hexStringToBytes(currentDirection), false);
                currentDirection = null;
            }
        }
    }


    /**
     * 计算用户握持方式
     *
     * @return
     */
    int calculateUserHoldMode() {
        int mode = 0;
        if (gx < 4.0 && gx > -4.0) {
            //屏幕朝上标准握持方式
            //Log.e(TAG,"标准握持方式");
            userModeTV.setText("标准握持方式");
            mode = 0;
        } else if (gx > 4.0) {
            //右手自然握持方式
            //Log.e(TAG,"右手自然握持方式");
            userModeTV.setText("右手自然握持方式");
            mode = 1;
        } else if (gx < -4.0) {
            //左手自然握持方式
            //Log.e(TAG,"左手自然握持方式");
            userModeTV.setText("左手自然握持方式");
            mode = 2;
        }
        return mode;
    }
}

