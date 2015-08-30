package com.benny.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by BennyYuan on 2015/8/27.
 */
public class ToastUtil {


    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
