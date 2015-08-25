package com.benny.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benny.mobilesafe.R;

public class MobileSafeWizard1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard1);
    }



    public  void nextClick(View v){
        Intent intent= new Intent(this,MobileSafeWizard2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_right_in,R.anim.activity_left_out);
    }
}
