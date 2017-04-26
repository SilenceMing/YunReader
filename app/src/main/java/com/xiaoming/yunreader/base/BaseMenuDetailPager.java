package com.xiaoming.yunreader.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author Slience_Manager
 * @time 2017/4/18 11:55
 */

public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public View mRootView;


    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    //初始化布局
    public abstract View initView(
    );

    //初始化数据
    public void initData() {

    }
}
