package com.iboxshare.ipctest1_2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by KN on 16/10/15.
 */

public class MyService extends Service {
    static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("what ==> ","0");
                    break;
                case 1:
                    Log.e("what ==> ","1");
                    break;
            }
        }
    };
    Messenger mMessenger = new Messenger(handler);
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Service","onBind");
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
