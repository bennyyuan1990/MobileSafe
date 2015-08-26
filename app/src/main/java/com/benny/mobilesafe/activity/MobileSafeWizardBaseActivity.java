package com.benny.mobilesafe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.benny.mobilesafe.R;

public abstract class MobileSafeWizardBaseActivity extends AppCompatActivity {

    private GestureDetector mGestrueDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGestrueDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (Math.abs(e1.getRawY() - e2.getRawY()) > 150) return true;

                if ((e2.getRawX() - e1.getRawX()) > 150) {
                    previousClick(null);
                    return true;
                } else if ((e1.getRawX() - e2.getRawX()) > 150) {
                    nextClick(null);
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 上一页
     *
     * @param v
     */
    public void previousClick(View v) {
        previousActivity();
        finish();
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
    }

    /**
     * 上一页的Activity
     */
    public abstract void previousActivity();

    /**
     * 下一页
     *
     * @param v
     */
    public void nextClick(View v) {
        nextActivity();
        finish();
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }

    /**
     * 下一页的Acitivity
     */
    public abstract void nextActivity();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestrueDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
