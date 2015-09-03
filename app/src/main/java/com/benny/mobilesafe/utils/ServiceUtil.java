package com.benny.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by BennyYuan on 2015/8/31.
 */
public class ServiceUtil {

    /**
     * 判断指定服务是否正在运行
     * @param context
     * @param clazz
     * @return
     */
    public static boolean isRunning(Context context, Class clazz) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
            if (serviceInfo.service.getClassName().equals(clazz.getName())) {
                return true;
            }

        }
        return false;
    }

}
