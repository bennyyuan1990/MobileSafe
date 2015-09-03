package com.benny.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.dao.PhoneLocationDao;

public class PhoneService extends Service {
    private static String TAG = PhoneService.class.getName();

    private PhoneStateListener listener;
    private TelephonyManager telephonyManager;
    private WindowManager windowManager;
    private SharedPreferences mpreference;

    private OutCallReceiver outCallReceiver;
    private View view;

    private int[] typeImage = new int[]{R.drawable.call_locate_white, R.drawable.call_locate_gray,
            R.drawable.call_locate_blue, R.drawable.call_locate_green, R.drawable.call_locate_orange};


    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mpreference = getSharedPreferences("setting", Context.MODE_PRIVATE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        clearView();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        clearView();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        showWindow(incomingNumber);
                        break;
                }
            }
        };
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        intentFilter.setPriority(Integer.MAX_VALUE);
        outCallReceiver = new OutCallReceiver();
        registerReceiver(outCallReceiver, intentFilter);
        Log.d(TAG, "registerReceiver");
    }

    private void showWindow(String incomingNumber) {
        int locationStyle = mpreference.getInt("LocationStyle", 0);

        view = View.inflate(PhoneService.this, R.layout.view_phone_location, null);
        view.setBackgroundResource(typeImage[locationStyle]);

        String result = PhoneLocationDao.getAddress(incomingNumber);
        ((TextView) view.findViewById(R.id.tv_PhoneAddress)).setText(result);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT + Gravity.TOP;
        params.x = mpreference.getInt("ToastX", 0);
        params.y = mpreference.getInt("ToastY", 0);
        params.setTitle("Toast");
        view.setOnTouchListener(new View.OnTouchListener() {
            private double prex, prey;
            private int dx, dy;
            private int winWidth, winHeight;
            private WindowManager.LayoutParams layoutParam;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        layoutParam = (WindowManager.LayoutParams) view.getLayoutParams();

                        prex = event.getRawX();
                        prey = event.getRawY();

                        winWidth = windowManager.getDefaultDisplay().getWidth();
                        winHeight = windowManager.getDefaultDisplay().getHeight();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) (event.getRawX() - prex);
                        dy = (int) (event.getRawY() - prey);
                        layoutParam.x = layoutParam.x + dx;
                        layoutParam.y = layoutParam.y + dy;
                        if ((layoutParam.x + view.getWidth()) < winWidth && (layoutParam.y + view.getHeight()) < winHeight && layoutParam.y > 0 && layoutParam.x > 0) {
                            windowManager.updateViewLayout(view,layoutParam);
                        }

                        prex = event.getRawX();
                        prey = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:

                        SharedPreferences.Editor editor = mpreference.edit();
                        editor.putInt("ToastX", layoutParam.x);
                        editor.putInt("ToastY", layoutParam.x);
                        editor.commit();
                        break;
                }
                return false;
            }
        });

        windowManager.addView(view, params);
        Log.d(TAG, "addView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
        if (outCallReceiver != null) {
            unregisterReceiver(outCallReceiver);
            outCallReceiver = null;
        }

        clearView();
    }

    private void clearView() {
        if (view != null) {
            windowManager.removeViewImmediate(view);
            view = null;
        }
    }


    public class OutCallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                showWindow(phoneNumber);
                Log.d(TAG, "showWindow");
            }
        }
    }
}
