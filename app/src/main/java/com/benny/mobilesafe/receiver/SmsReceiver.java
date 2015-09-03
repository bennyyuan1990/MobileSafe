package com.benny.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;


import com.benny.mobilesafe.R;

import java.io.IOException;

/**
 * Created by BennyYuan on 2015/8/28.
 */
public class SmsReceiver extends BroadcastReceiver {
    private SharedPreferences sharedPreferences;


    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);

        Object[] pdus = (Object[]) intent.getExtras().get("pdus");

        for (Object data : pdus) {
            byte[] pdusmessage = (byte[]) data;
            SmsMessage smsMessage = SmsMessage.createFromPdu(pdusmessage);
            String msg = smsMessage.getMessageBody();


            switch (msg) {
                case "#*location*#":
                    getLocation(context);
                    break;
                case "#*alram*#":
                    playAlarm(context);
                    break;
                case "#*wipedata*#":
                    break;
                case "#*lockscreen*#":
                    break;
            }
        }
    }


    /**
     * 播放警报
     *
     * @param context
     */
    private void playAlarm(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1, 1);
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取坐标信息
     * @param context
     * @return
     */
    private String getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String result = locationManager.getBestProvider(new Criteria(), true);
        locationManager.requestLocationUpdates(result, 10000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("location","x:"+location.getLatitude() +",y:" + location.getLongitude());
                edit.commit();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        return sharedPreferences.getString("location", "");

    }


}
