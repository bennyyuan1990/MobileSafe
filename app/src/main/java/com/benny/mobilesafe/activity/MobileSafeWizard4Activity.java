package com.benny.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.benny.mobilesafe.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MobileSafeWizard4Activity extends MobileSafeWizardBaseActivity {

    @ViewInject(R.id.ck_Protect)
    private CheckBox ckProtect;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard4);
        ViewUtils.inject(this);
        mSharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        boolean protect = mSharedPreferences.getBoolean("Protect", false);
        ckProtect.setChecked(protect);
        ckProtect.setText(protect ? "你已开启防盗保护" : "你没有开启防盗保护");

        ckProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("Protect", isChecked);
                editor.commit();
                ckProtect.setText(isChecked ? "你已开启防盗保护" : "你没有开启防盗保护");
            }
        });
    }

    @Override
    public void previousActivity() {
        Intent intent = new Intent(this, MobileSafeWizard3Activity.class);
        startActivity(intent);
    }


    @Override
    public void nextActivity() {
        Intent intent = new Intent(this, MobileSafeActivity.class);
        startActivity(intent);
    }

}
