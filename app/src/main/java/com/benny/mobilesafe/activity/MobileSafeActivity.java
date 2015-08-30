package com.benny.mobilesafe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benny.mobilesafe.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MobileSafeActivity extends AppCompatActivity {


    @ViewInject(R.id.tv_PhoneNum)
    private TextView tvPhone;

    @ViewInject(R.id.iv_lock)
    private ImageView ivLock;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe);
        ViewUtils.inject(this);

        sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("Phone", "");
        boolean lock = sharedPreferences.getBoolean("Protect", false);
        tvPhone.setText(phone);
        ivLock.setImageResource(lock ? R.mipmap.lock : R.mipmap.unlock);

    }

    public void resetClick(View v) {
        Intent intent = new Intent(this, MobileSafeWizard1Activity.class);
        startActivity(intent);
    }


}
