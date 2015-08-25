package com.benny.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benny.mobilesafe.R;

public class MobileSafeWizard2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard2);
    }

    public  void previousClick(View v){

        Intent intent= new Intent(this,MobileSafeWizard1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
    }

    public  void nextClick(View v){

        Intent intent= new Intent(this,MobileSafeWizard3Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);
    }
}
