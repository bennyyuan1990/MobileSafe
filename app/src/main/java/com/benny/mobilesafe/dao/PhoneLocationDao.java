package com.benny.mobilesafe.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.benny.mobilesafe.common.AppParams;

/**
 * Created by BennyYuan on 2015/8/30.
 */
public class PhoneLocationDao {
    private static SQLiteDatabase sqLiteDatabase;

    static {
        sqLiteDatabase = SQLiteDatabase.openDatabase(AppParams.LOCATIONDB, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * 获取电话号码的归属地
     *
     * @param phoneNum
     * @return
     */
    public static String getAddress(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) return "空号码";
        switch (phoneNum.length()) {
            case 3:
                return "报警号码";
            case 5:
                return "服务号码";

            case 7:
            case 8:
                return "本地号码";
            case 11:
                Cursor cursor = sqLiteDatabase.rawQuery("select location from mob_location where _id=?", new String[]{phoneNum.substring(0, 7)});
                if (cursor.moveToNext()) {
                    return cursor.getString(0);
                }
                break;
        }
        return "未知";
    }

}
