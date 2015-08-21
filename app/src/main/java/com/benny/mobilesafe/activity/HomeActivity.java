package com.benny.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.benny.mobilesafe.R;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_Items;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gv_Items = (GridView) findViewById(R.id.gv_items);

        initItems();
    }


    private void initItems()
    {
        final int[] images = new int[]{R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
                R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
                R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings };
        final String[] titles = new String[]{"安全","通话","软件","任务","网络","监控","参数","工具","设置"};

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
                if(convertView == null) convertView = getLayoutInflater().inflate(R.layout.view_item,null);
                ( (ImageView)  convertView.findViewById(R.id.iv_item_image) ).setImageResource(images[position]);
                ( (TextView)  convertView.findViewById(R.id.tv_item_text) ).setText(titles[position]);
                return convertView;
            }
        });

    }

}
