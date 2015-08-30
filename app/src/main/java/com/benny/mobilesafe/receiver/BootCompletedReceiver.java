package com.benny.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by BennyYuan on 2015/8/27.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        boolean protect = sharedPreferences.getBoolean("Protect", false);
        String sim = sharedPreferences.getString("SIM", null);
        if(protect && !TextUtils.isEmpty(sim))
        {
            TelephonyManager service = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if(!service.getSimSerialNumber().equals(sim)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendDataMessage(sharedPreferences.getString("Phone",null),null,(short)0,"Sim change".getBytes(),null,null);
            }
        }

    }
}
