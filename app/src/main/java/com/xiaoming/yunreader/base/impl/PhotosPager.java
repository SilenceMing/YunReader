package com.xiaoming.yunreader.base.impl;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xiaoming.yunreader.base.BaseMenuDetailPager;

/**
 * @author Slience_Manager
 * @time 2017/4/18 12:11
 */

public class PhotosPager extends BaseMenuDetailPager {



    public PhotosPager(Activity mActivity) {
        super(mActivity);

    }

    @Override
    public View initView() {
        TextView tv = new TextView(mActivity);
        tv.setText("图片页面");
        return tv;
    }


}
