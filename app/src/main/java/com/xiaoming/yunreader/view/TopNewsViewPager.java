package com.xiaoming.yunreader.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Slience_Manager
 * @time 2017/4/20 0:10
 */

public class TopNewsViewPager extends ViewPager {
    private int startX;
    private int startY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1. 上下滑动需要拦截
     * 2. 向右滑动并且当前是第一个页面时需要拦截
     * 3. 想左滑动当前是最后一个页面时需要拦截
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //父控件需不需要拦截  TRUE不拦截  FALSE要拦截
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dy) < Math.abs(dx)) {
                    int currentItem = getCurrentItem();
                    // 左右滑动
                    if (dx > 0) {
                        // 右滑
                        if (currentItem == 0) {
                            // 第一个页面。需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        // 左滑
                        int count = getAdapter().getCount();
                        if (currentItem == count - 1) {
                            // 最后一个页面需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    // 上下互动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
