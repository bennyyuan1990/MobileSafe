package com.benny.mobilesafe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.dao.PhoneLocationDao;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PhoneAddressActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_Phone)
    private EditText tvPhone;

    @ViewInject(R.id.tv_PhoneResult)
    private TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_address);
        ViewUtils.inject(this);

        tvPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onClick(null);
            }
        });
    }

    @OnClick(R.id.btn_Query)
    private void onClick(View view)
    {
        tvResult.setText(PhoneLocationDao.getAddress(tvPhone.getText().toString()));
    }


}
