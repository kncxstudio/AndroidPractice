package com.iboxshare.viewtest1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by KN on 16/10/15.
 */

public class MyView extends View {
    String TAG = "MyView";
    Context context;
    View thisView = this;

    public MyView(Context context) {
        super(context);
        this.context = context;
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"ACTION_DOWN");
                makeToast("RawX" + event.getRawX() + "   RawY" + event.getRawY() + "\n"
                        + "X" + event.getX() + "   Y" + event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.e(TAG,"ACTION_MOVE");
                Log.e(TAG, " x ==> " + event.getX() + " y ==> " + event.getY());

                //ObjectAnimator.ofFloat(thisView,"translationX",0,100).start();
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"MOVE_UP");
                break;
        }
        return super.onTouchEvent(event);
    }


    void makeToast(String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }
}
