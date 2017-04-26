package com.xiaoming.yunreader.utils;

import android.app.Application;

import org.xutils.x;

/**
 * 初始化Xutils开源框架
 * @author Slience_Manager
 * @time 2017/4/19 18:21
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //
        x.Ext.init(this);
    }
}
