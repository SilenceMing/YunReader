package com.xiaoming.yunreader.base.impl;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xiaoming.yunreader.base.BaseMenuDetailPager;

import static com.xiaoming.yunreader.R.id.toolbar;

/**
 * @author Slience_Manager
 * @time 2017/4/18 12:11
 */

public class MusicPager extends BaseMenuDetailPager {

    public MusicPager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("音乐页面");
        return tv;
    }
}
