package com.xiaoming.yunreader.utils;

import android.content.Context;

/**
 * 网络缓存工具类
 * @author Slience_Manager
 * @time 2017/4/19 23:27
 */

public class CacheUtils {
    /**
     * 写入缓存数据
     * @param url
     * @param json
     * @param mContext
     */
    public static void setCacheData(String url,String json,Context mContext){
        SharedPreferenceUtils.setString(mContext,url,json);
    }

    /**
     * 读取缓存数据
     * @param url
     * @param mContext
     * @return
     */
    public static String getCacheData(String url,Context mContext){
        return SharedPreferenceUtils.getString(mContext,url,null);
    }
}
