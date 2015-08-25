package com.benny.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.utils.EncryptUtil;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_Items;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gv_Items = (GridView) findViewById(R.id.gv_items);

        initItems();

        mSharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
    }


    /**
     * 初始化菜单项
     */
    private void initItems() {
        final int[] images = new int[]{R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
                R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
                R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};
        final String[] titles = new String[]{"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};

        gv_Items.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public Object getItem(int position) {
                return titles[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.view_home_item, null);
                ((ImageView) convertView.findViewById(R.id.iv_item_image)).setImageResource(images[position]);
                ((TextView) convertView.findViewById(R.id.tv_item_text)).setText(titles[position]);
                return convertView;
            }
        });

        gv_Items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 8: //设置中心
                        enterSettingActivity();
                        break;


                    case 0:
                    enterSafeActivity();
                        break;
                }
            }
        });

    }


    /**
     * 进入设置中心Activity
     */
    private void enterSettingActivity() {
        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
        startActivity(intent);

    }

    /**
     * 进入手机防盗
     */
    private void enterSafeActivity() {
        final String password = mSharedPreferences.getString("safePassword", null);

        if (TextUtils.isEmpty(password)) {

            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            View view = getLayoutInflater().inflate(R.layout.view_password_set, null);
            final EditText tvPassword = (EditText) view.findViewById(R.id.tv_view_password);
            final EditText tvPassword1 = (EditText) view.findViewById(R.id.tv_view_password1);
            view.findViewById(R.id.btn_view_quit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            view.findViewById(R.id.btn_view_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(tvPassword.getText())) {
                        Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!tvPassword.getText().toString().equals(tvPassword1.getText().toString())) {
                        Toast.makeText(HomeActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("safePassword", EncryptUtil.getMD5(tvPassword.getText().toString()));
                    editor.commit();
                    dialog.dismiss();
                }
            });

            dialog.setView(view, 0, 0, 0, 0);
            dialog.show();
        } else {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            View view = getLayoutInflater().inflate(R.layout.view_password_get, null);
            final EditText tvPassword = (EditText) view.findViewById(R.id.tv_view_password);
            view.findViewById(R.id.btn_view_quit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            view.findViewById(R.id.btn_view_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(tvPassword.getText())) {
                        Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(EncryptUtil.getMD5(tvPassword.getText().toString()))) {
                        Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog.dismiss();

                    Intent intent= new Intent(HomeActivity.this,MobileSafeWizard1Activity.class);
                    startActivity(intent);
                }
            });

            dialog.setView(view, 0, 0, 0, 0);
            dialog.show();
        }
    }

}
