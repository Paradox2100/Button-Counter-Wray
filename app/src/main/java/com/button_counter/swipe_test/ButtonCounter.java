package com.button_counter.swipe_test;

import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class ButtonCounter extends AppCompatActivity {

    private static final String DEBUG_TAG = "Velocity";
    private VelocityTracker mVelocityTracker = null;
    private TextView display;
    private int counter = 0;
    private TextView info;
    private Button clearBtn;

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(mVelocityTracker == null){
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else{
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);
                float velX = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
                float velY = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);
                if(Math.abs(velX) > Math.abs(velY)){
                    if(velX > 0){
                        counter++;
                        display.setText(Integer.toString(counter));
                    }
                    else{
                        counter--;
                        display.setText(Integer.toString(counter));
                    }
                }
                /*
                else if(Math.abs(velY) > (4 * Math.abs(velX))){
                    if(velY > 0){
                        counter = 0;
                        display.setText(Integer.toString(counter));
                    }
                }
                */
                velX = 0;
                velY = 0;
                //Log.d("", "X velocity: " + VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
                //Log.d("", "Y velocity: " + VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                //Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_counter);

        display = (TextView)findViewById(R.id.display);

        info = (TextView)findViewById(R.id.info);

        clearBtn = (Button)findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                display.setText("0");
            }
        });
    }


    @Override
    protected void onSaveInstanceState (Bundle outState){
        outState.putString("savedDisplay", display.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        display.setText(savedInstanceState.getString("savedDisplay"));

    }

}
