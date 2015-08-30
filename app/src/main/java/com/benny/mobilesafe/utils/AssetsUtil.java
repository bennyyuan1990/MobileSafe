package com.benny.mobilesafe.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.benny.mobilesafe.common.AppParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BennyYuan on 2015/8/30.
 */
public class AssetsUtil {

    public static void copyAddressBd(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            File filesDir = context.getFilesDir();
            File file = new File(filesDir, "location.db");
            AppParams.LOCATIONDB = file.toString();
            if (!file.exists()) {

                InputStream inputStream = assetManager.open("telocation.db");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] data = new byte[1000];
                int length = 0;
                while ((length = inputStream.read(data)) > 0) {
                    fileOutputStream.write(data, 0, length);
                }
                fileOutputStream.close();
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
