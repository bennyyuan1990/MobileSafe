package com.benny.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.CompoundButton;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.view.SetingItem;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MobileSafeWizard2Activity extends MobileSafeWizardBaseActivity {

    @ViewInject(R.id.mv_bindSim)
    private SetingItem mSetingItem;

    private SharedPreferences mSharedPreferences;

    private TelephonyManager mTelephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard2);
        ViewUtils.inject(this);

        mSharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String sim = mSharedPreferences.getString("SIM", null);
        mSetingItem.setCheck(!TextUtils.isEmpty(sim));
        mSetingItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                if (mSetingItem.isCheck()) {
                    editor.putString("SIM", mTelephonyManager.getSimSerialNumber());
                } else {
                    editor.putString("SIM", "");
                }
                editor.commit();
            }
        });
    }

    @Override
    public void previousActivity() {
        Intent intent = new Intent(this, MobileSafeWizard1Activity.class);
        startActivity(intent);
    }


    @Override
    public void nextActivity() {
        Intent intent = new Intent(this, MobileSafeWizard3Activity.class);
        startActivity(intent);
    }
}
