package com.xiaoming.yunreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.net.ssl.SSLHandshakeException;

/**
 * SharedPreference封装类
 * @author Slience_Manager
 * @time 2017/4/19 23:15
 */

public class SharedPreferenceUtils {
    public static boolean getBoolean(Context mContext, String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context mContext, String key, Boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, defValue).commit();
    }

    public static String getString(Context mContext, String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void setString(Context mContext, String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, defValue).commit();
    }
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static void setInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,defValue).commit();
    }
}
