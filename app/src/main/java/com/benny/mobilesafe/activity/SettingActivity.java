package com.benny.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.service.PhoneService;
import com.benny.mobilesafe.view.SetingItem;
import com.benny.mobilesafe.view.SetupItem;

public class SettingActivity extends AppCompatActivity {

    private SetingItem mvAutoUpdate;
    private SharedPreferences mpreference;
    private SetingItem mvshowLocation;
    private SetupItem mvType;
    private  SetupItem mvToasLocation;
    private String[] typeArray = new String[]{"白色","灰色","蓝色","绿色","橙色"};
    private int[] typeImage = new int[]{R.drawable.call_locate_white,R.drawable.call_locate_gray,
            R.drawable.call_locate_blue,R.drawable.call_locate_green,R.drawable.call_locate_orange};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mpreference = getSharedPreferences("setting", Context.MODE_PRIVATE);
        initAutoUpdate();
        initShowLocation();
        initLocationStyle();

        mvToasLocation = (SetupItem) findViewById(R.id.mv_locationSize);
        mvToasLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ToastLoactionSetupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initLocationStyle() {
        mvType = (SetupItem) findViewById(R.id.mv_locationStyle);
        int locationStyle = mpreference.getInt("LocationStyle", 0);
        mvType.setDesc(typeArray[locationStyle]);
        mvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setItems(typeArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferences.Editor editor = mpreference.edit();
                        editor.putInt("LocationStyle", which);
                        editor.commit();
                        mvType.setDesc(typeArray[which]);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void initShowLocation() {
        //归属地显示
        mvshowLocation = (SetingItem) findViewById(R.id.mv_showLocation);
        boolean showLocation = mpreference.getBoolean("ShowLocation", false);
        mvshowLocation.setCheck(showLocation);
        mvshowLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mpreference.edit();
                editor.putBoolean("ShowLocation", isChecked);
                editor.commit();
                if (isChecked) {
                    Intent intent = new Intent(SettingActivity.this, PhoneService.class);
                    startService(intent);
                } else {
                    Intent intent = new Intent(SettingActivity.this, PhoneService.class);
                    stopService(intent);
                }
            }
        });
    }

    private void initAutoUpdate() {
        //自动更新
        mvAutoUpdate = (SetingItem) findViewById(R.id.mv_autoUpdate);
        boolean autoUpdate = mpreference.getBoolean("AutoUpdate", false);
        mvAutoUpdate.setCheck(autoUpdate);
        mvAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mpreference.edit();
                editor.putBoolean("AutoUpdate", isChecked);
                editor.commit();
            }
        });
    }


}
