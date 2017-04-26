package com.xiaoming.yunreader.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * @author Slience_Manager
 * @time 2017/4/21 11:46
 */

public class ProgressDrawable {
    private int[] colors = {
            0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00
            , 0xFF00FFFF, 0xFF0000FF, 0xFF8B00FF};
    private int CIRCLE_BG_LIGHT = 0x00000000;
    private ValueAnimator valueAnimator;
    private static MaterialProgressDrawable mProgress;

    /**
     * 展示自定义的MaterialProgressDrawable
     * @param mContext  context对象
     * @param image     需要显示的image布局容器
     * @param isShow   是否显示view
     * @return
     */
    public void getFooterView(Context mContext, ImageView image, boolean isShow) {

        mProgress = new MaterialProgressDrawable(mContext, image);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        //圈圈颜色,可以是多种颜色
        mProgress.setColorSchemeColors(colors);
        //设置圈圈的各种大小
        mProgress.updateSizes(MaterialProgressDrawable.DEFAULT);

        image.setImageDrawable(mProgress);
        initView(isShow);
    }

    private void initView(boolean isShow) {
        if (valueAnimator == null) {
            valueAnimator = valueAnimator.ofFloat(0f, 1f);
            //设置动画长度
            valueAnimator.setDuration(600);

            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float n = (float) animation.getAnimatedValue();
                    //圈圈的旋转角度
                    mProgress.setProgressRotation(n * 0.5f);
                    //圈圈周长，0f-1F
                    mProgress.setStartEndTrim(0f, n * 0.8f);
                    //箭头大小，0f-1F
                    mProgress.setArrowScale(n);
                    //透明度，0-255
                    mProgress.setAlpha((int) (255 * n));
                }
            });

            valueAnimator.start();

            if (isShow) {
                mProgress.start();
            }
        }
    }

}
