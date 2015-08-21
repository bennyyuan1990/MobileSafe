package com.benny.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by BennyYuan on 2015/8/20.
 */
public class StreamUtil {


    public static String getString(InputStream stream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1000];
        int length = 0;
        while ((length = stream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, length);

        }
        return outputStream.toString();
    }
}
