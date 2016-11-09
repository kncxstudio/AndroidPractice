package com.iboxshare.testgyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Context context = this;
    private TextView xTV,yTV,zTV
            ,gxTV,gyTV,gzTV
            ,gyroscopeXTV,gyroscopeYTV,gyroscopeZTV
            ,showDirectionTV;
    private Sensor aSensor,gSensor,gyroscopeSensor;
    private SensorManager sensorManager;
    static double ax,ay,az
            ,gx,gy,gz
            ,gyroscopeX,gyroscopeY,gyroscopeZ;


    //Flags
    public static String currentDirection = "null";
    public static int currentUserHoldMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        init();
    }

    /**
     * 绑定View
     */
    void bindView(){
        xTV = (TextView) findViewById(R.id.loc_x);
        yTV = (TextView) findViewById(R.id.loc_y);
        zTV = (TextView) findViewById(R.id.loc_z);
        gxTV = (TextView) findViewById(R.id.g_x);
        gyTV = (TextView) findViewById(R.id.g_y);
        gzTV = (TextView) findViewById(R.id.g_z);
        gyroscopeXTV = (TextView) findViewById(R.id.gyroscope_x);
        gyroscopeYTV = (TextView) findViewById(R.id.gyroscope_y);
        gyroscopeZTV = (TextView) findViewById(R.id.gyroscope_z);
        showDirectionTV = (TextView) findViewById(R.id.show_direction_tv);
    }

    /**
     * 初始化
     */
    void init(){
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


                gyroscopeXTV.setText("gyrosX: "+ gyroscopeX);
                gyroscopeYTV.setText("gyrosY: "+ gyroscopeY);
                gyroscopeZTV.setText("gyrosZ: "+ gyroscopeZ);
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },gyroscopeSensor,SensorManager.SENSOR_DELAY_GAME);



        //注册加速仪监听器
        sensorManager.registerListener(new SensorEventListener() {
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


                xTV.setText("ax: "+ ax);
                yTV.setText("ay: "+ ay);
                zTV.setText("az: "+ az);

                //计算方向
                calculateDirection();


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },aSensor,SensorManager.SENSOR_DELAY_GAME);


        //注册重力加速度监听器
        sensorManager.registerListener(new SensorEventListener() {
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
        },gSensor,SensorManager.SENSOR_DELAY_GAME);
    }


    /**
     * 通过ax,ay,zy,gx,gy,gz计算设备移动方向
     */
    void calculateDirection(){


        //矫正加速度
        ax = 0.8 * ax + 0.2 * gx;
        ay = 0.8 * ay + 0.2 * gy;
        az = 0.8 * az + 0.2 * gz;


        //判断玩家手机握持方式
        currentUserHoldMode = calculateUserHoldMode();


        if (az - gz > 5.0){
            //Log.e(TAG,"向下移动");

            switch (currentUserHoldMode){
                case 0:
                    currentDirection = "向下移动";
                    break;
                case 1:
                    currentDirection = "向右移动";
                    break;
                case 2:
                    currentDirection = "向左移动";
                    break;
            }
        }else if(az - gz < -5.0){
            //Log.e(TAG,"向上移动");
            switch (currentUserHoldMode){
                case 0:
                    currentDirection = "向上移动";
                    break;
                case 1:
                    currentDirection = "向左移动";
                    break;
                case 2:
                    currentDirection = "向右移动";
                    break;
            }
        }else if (ax - gx > 5.0){
            //Log.e(TAG,"向左移动");
            switch (currentUserHoldMode){
                case 0:
                    currentDirection = "向左移动";
                    break;
                case 1:
                    currentDirection = "向下移动";
                    break;
                case 2:
                    currentDirection = "向上移动";
                    break;
            }
        }else if(ax - gx < -5.0){
            //Log.e(TAG,"向右移动");
            switch (currentUserHoldMode){
                case 0:
                    currentDirection = "向右移动";
                    break;
                case 1:
                    currentDirection = "向上移动";
                    break;
                case 2:
                    currentDirection = "向下移动";
                    break;
            }        }

        showDirectionTV.setText(currentDirection);
    }


    /**
     * 计算用户握持方式
     * @return
     */
    int calculateUserHoldMode(){
        int mode = 0;
        if (gx < 4.0 && gx > -4.0){
            //屏幕朝上标准握持方式
            //Log.e(TAG,"标准握持方式");
            mode = 0;
        }else if(gx > 4.0){
            //右手自然握持方式
            //Log.e(TAG,"右手自然握持方式");
            mode = 1;
        }else if (gx < -4.0){
            //左手自然握持方式
            //Log.e(TAG,"左手自然握持方式");
            mode = 2;
        }
        return mode;
    }
}
