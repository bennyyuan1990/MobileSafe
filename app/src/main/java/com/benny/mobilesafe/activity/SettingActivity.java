package com.benny.mobilesafe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.view.SetingItem;

public class SettingActivity extends AppCompatActivity {

    private SetingItem mvAutoUpdate;
    private SharedPreferences mpreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mpreference = getSharedPreferences("setting", Context.MODE_PRIVATE);
        mvAutoUpdate = (SetingItem) findViewById(R.id.mv_autoUpdate);

        boolean autoUpdate = mpreference.getBoolean("AutoUpdate",false);
        mvAutoUpdate.setCheck(autoUpdate);
        mvAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor=   mpreference.edit();
                editor.putBoolean("AutoUpdate",isChecked);
                editor.commit();
            }
        });
    }


}
