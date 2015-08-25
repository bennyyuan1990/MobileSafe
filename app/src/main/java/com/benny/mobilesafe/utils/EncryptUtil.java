package com.benny.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by BennyYuan on 2015/8/24.
 */
public class EncryptUtil {

    /**
     * MD5加密字符串
     * @param value
     * @return
     */
    public static String getMD5(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(value.getBytes());
            StringBuilder sb = new StringBuilder();
            String temp;
            for (byte b : result) {
                temp = Integer.toHexString(b);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                sb.append(temp);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return value;
    }
}
