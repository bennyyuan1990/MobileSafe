package com.benny.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.benny.mobilesafe.R;
import com.benny.mobilesafe.utils.AssetsUtil;
import com.benny.mobilesafe.utils.StreamUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int CODE_ERROR = 1;
    private static final int CODE_UPDATE = 2;
    private static final int CODE_ENTERHOME = 3;
    private TextView tvVersion = null;

    private int mVersionCode = 0;

    private ProgressBar pb_Download;


    private android.os.Handler handler;

    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case CODE_ERROR:
                        Toast.makeText(SplashActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                        enterHome();
                        break;
                    case CODE_UPDATE:

                     Bundle bundle =   msg.getData();
                        showDialog(bundle.getString("description"),bundle.getString("url"));
                        break;
                    case CODE_ENTERHOME:
                        enterHome();
                        break;
                }

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //设置渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        findViewById(R.id.rl_splashBg).startAnimation(alphaAnimation);

        tvVersion = (TextView) findViewById(R.id.tv_version);
        pb_Download = (ProgressBar) findViewById(R.id.pb_Download);
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            mVersionCode = info.versionCode;
            tvVersion.setText("APP版本:" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences= getSharedPreferences("setting", Context.MODE_PRIVATE);
        boolean autoUpdate=   sharedPreferences.getBoolean("AutoUpdate",false);
        AssetsUtil.copyAddressBd(this);
        if(autoUpdate) {
            checkUpdate();
        }else {
            handler.sendEmptyMessageDelayed(CODE_ENTERHOME,2000);
        }
    }


    /**
     * 显示APP更新提示框
     * @param msg
     * @param url
     */
    private void showDialog(String msg, final String url)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("有新版本更新");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpUtils httpUtils = new HttpUtils();
                File file = new File(Environment.getExternalStorageDirectory(), "mobilesafe.apk");
                httpUtils.download(url, file.toString(), new RequestCallBack<File>() {

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);

                        pb_Download.setMax((int) total);
                        pb_Download.setProgress((int) current);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        // responseInfo.result
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(responseInfo.result.getAbsolutePath()), "application/vnd.android.package-archive");
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(SplashActivity.this,s,Toast.LENGTH_LONG).show();
                        enterHome();
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }


    /**
     * 进入主页
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 检查网络更新
     */
    private void checkUpdate() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                long time = SystemClock.currentThreadTimeMillis();
                try {
                    URL url = new URL("http://192.168.1.101/android/android.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    if (connection.getResponseCode() == 200) {
                        String str = StreamUtil.getString(connection.getInputStream());
                        JSONObject jobject = new JSONObject(str);
                        if (jobject.getInt("versionCode") > mVersionCode) {
                            Message msg = Message.obtain();
                            msg.what = CODE_UPDATE;
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("description", jobject.getString("description"));
                            bundle.putCharSequence("url", jobject.getString("url"));
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }else {

                            long now = SystemClock.currentThreadTimeMillis();
                            if (now - time < 2000) Thread.sleep(2000-(now - time));

                            handler.sendEmptyMessage(CODE_ENTERHOME);
                        }
                    } else {
                        handler.sendEmptyMessage(CODE_ERROR);
                    }



                } catch (Exception e) {
                    e.printStackTrace();

                    handler.sendEmptyMessage(CODE_ERROR);
                }
} }.start();



    }}