package com.benny.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benny.mobilesafe.R;

public class MobileSafeWizard2Activity extends MobileSafeWizardBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard2);
    }

    @Override
    public void previousActivity() {
        Intent intent= new Intent(this,MobileSafeWizard1Activity.class);
        startActivity(intent);
    }


    @Override
    public void nextActivity() {
        Intent intent= new Intent(this,MobileSafeWizard3Activity.class);
        startActivity(intent);
    }
}
