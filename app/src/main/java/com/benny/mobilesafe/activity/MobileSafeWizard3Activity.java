package com.benny.mobilesafe.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MobileSafeWizard3Activity extends MobileSafeWizardBaseActivity {

    @ViewInject(R.id.tv_PhoneNum)
    private EditText tvPhoneNum;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_safe_wizard3);

        ViewUtils.inject(this);
        mSharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);

        String phone = mSharedPreferences.getString("Phone", null);
        tvPhoneNum.setText(phone);
    }

    @OnClick(R.id.btn_SelectContact)
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            // 得到ContentResolver对象
            ContentResolver cr = getContentResolver();
            // 取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            // 向下移动光标
            while (cursor.moveToNext()) {
                // 取得联系人名字
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                cursor.moveToFirst();

                String phoneNum = this.getContactPhone(cursor);
                tvPhoneNum.setText(phoneNum);
            }
        }
    }

    //获取联系人电话
    private String getContactPhone(Cursor cursor) {

        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        //System.out.print(phoneNum);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            //int phoneCount = phones.getCount();
            //allPhoneNum = new ArrayList<String>(phoneCount);
            if (phones.moveToFirst()) {
                // 遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index);
                    switch (phone_type) {
                        case 2:
                            phoneResult = phoneNumber;
                            break;
                    }
                    //allPhoneNum.add(phoneNumber);
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }


    @Override
    public void previousActivity() {
        Intent intent = new Intent(this, MobileSafeWizard2Activity.class);
        startActivity(intent);
    }


    @Override
    public void nextActivity() {
        String sim = mSharedPreferences.getString("SIM", null);
        if ((!TextUtils.isEmpty(sim) && TextUtils.isEmpty(tvPhoneNum.getText().toString().trim()))) {
            ToastUtil.show(this, "请选择联系号码");
            return;
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("Phone", tvPhoneNum.getText().toString().trim());
        editor.commit();

        Intent intent = new Intent(this, MobileSafeWizard4Activity.class);
        startActivity(intent);
    }
}
