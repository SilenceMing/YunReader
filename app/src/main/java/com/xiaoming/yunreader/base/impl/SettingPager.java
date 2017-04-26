package com.xiaoming.yunreader.base.impl;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiaoming.yunreader.base.BaseMenuDetailPager;

/**
 * @author Slience_Manager
 * @time 2017/4/18 12:11
 */

public class SettingPager extends BaseMenuDetailPager {

    public SettingPager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("设置页面");
        return tv;
    }
}
