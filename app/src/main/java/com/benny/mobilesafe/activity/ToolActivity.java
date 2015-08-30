package com.benny.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.benny.mobilesafe.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        ViewUtils.inject(this);


    }

    @OnClick(R.id.tv_PhoneLocation)
    public void queryLoaction(View view){
        Intent intent = new Intent(this,PhoneAddressActivity.class);
        startActivity(intent);
    }




}
