package com.benny.mobilesafe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benny.mobilesafe.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ToastLoactionSetupActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_tip1)
    private TextView tvTip1;
    @ViewInject(R.id.tv_tip2)
    private TextView tvTip2;
    @ViewInject(R.id.iv_drag)
    private ImageView ivDrag;
    private SharedPreferences mpreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_loaction_setup);
        ViewUtils.inject(this);
        mpreference = getSharedPreferences("setting", Context.MODE_PRIVATE);


        try {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivDrag.getLayoutParams();
            layoutParams.leftMargin = mpreference.getInt("ToastX", 0);
            layoutParams.topMargin = mpreference.getInt("ToastY", 0);
            ivDrag.setLayoutParams(layoutParams);
            ivDrag.setOnClickListener(new View.OnClickListener() {
                private long[] times = new long[2];

                @Override
                public void onClick(View v) {
                    System.arraycopy(times, 0, times, 1, times.length - 1);
                    times[0] = SystemClock.uptimeMillis();
                    if ((times[0] - times[1] < 500)) {
                        int winWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
                        int winHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();
                        RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) ivDrag.getLayoutParams();
                        layoutParam.leftMargin = winWidth / 2 - ivDrag.getWidth() / 2;
                        layoutParam.topMargin = winHeight / 2 - ivDrag.getHeight() / 2;
                        ivDrag.setLayoutParams(layoutParam);
                        SharedPreferences.Editor editor = mpreference.edit();
                        editor.putInt("ToastX", layoutParam.leftMargin);
                        editor.putInt("ToastY", layoutParam.topMargin);
                        editor.commit();
                    }
                }
            });

            ivDrag.setOnTouchListener(new View.OnTouchListener() {
                private double prex, prey;
                private int dx, dy;
                private int winWidth, winHeight;
                private RelativeLayout.LayoutParams layoutParam;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutParam = (RelativeLayout.LayoutParams) ivDrag.getLayoutParams();

                            prex = event.getRawX();
                            prey = event.getRawY();

                            winWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
                            winHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();

                            break;
                        case MotionEvent.ACTION_MOVE:
                            dx = (int) (event.getRawX() - prex);
                            dy = (int) (event.getRawY() - prey);
                            layoutParam.leftMargin = layoutParam.leftMargin + dx;
                            layoutParam.topMargin = layoutParam.topMargin + dy;
                            if ((layoutParam.leftMargin + ivDrag.getWidth()) < winWidth && (layoutParam.topMargin + ivDrag.getHeight()) < winHeight && layoutParam.topMargin > 0 && layoutParam.leftMargin > 0) {
                                ivDrag.setLayoutParams(layoutParam);
                            }
                            if (layoutParam.topMargin > winHeight / 2) {
                                tvTip1.setVisibility(View.VISIBLE);
                                tvTip2.setVisibility(View.INVISIBLE);
                            } else {
                                tvTip1.setVisibility(View.INVISIBLE);
                                tvTip2.setVisibility(View.VISIBLE);
                            }
                            prex = event.getRawX();
                            prey = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:

                            SharedPreferences.Editor editor = mpreference.edit();
                            editor.putInt("ToastX", layoutParam.leftMargin);
                            editor.putInt("ToastY", layoutParam.topMargin);
                            editor.commit();
                            break;
                    }
                    return false;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
