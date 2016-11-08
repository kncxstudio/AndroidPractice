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
    private TextView xTV,yTV,zTV,gxTV,gyTV,gzTV;
    private Sensor aSensor,gSensor;
    private SensorManager sensorManager;

    static double ax,ay,az,gx,gy,gz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        init();
    }

    void bindView(){
        xTV = (TextView) findViewById(R.id.loc_x);
        yTV = (TextView) findViewById(R.id.loc_y);
        zTV = (TextView) findViewById(R.id.loc_z);
        gxTV = (TextView) findViewById(R.id.g_x);
        gyTV = (TextView) findViewById(R.id.g_y);
        gzTV = (TextView) findViewById(R.id.g_z);
    }

    void init(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
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
        },aSensor,SensorManager.SENSOR_DELAY_UI);


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
        },gSensor,SensorManager.SENSOR_DELAY_UI);
    }


    /**
     * 通过ax,ay,zy,gx,gy,gz计算设备移动方向
     */
    void calculateDirection(){
        //Log.e(TAG,"x方向加速度：" + (ax - gx));
        if (ax - gx > 5.0){
            Log.e(TAG,"向左移动");

            if (az - gz > 3.0){
                Log.e(TAG,"向左下方移动");
            }else if(az - gz < -3.0){
                Log.e(TAG,"向左上方移动");
            }
        }else if(ax - gx < -5.0){
            Log.e(TAG,"向右移动");
        }
    }
}
