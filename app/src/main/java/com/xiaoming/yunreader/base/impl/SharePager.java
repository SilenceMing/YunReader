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

public class SharePager extends BaseMenuDetailPager {
    private Toolbar mToolbar;
    public SharePager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("分享页面");
        return tv;
    }
}
