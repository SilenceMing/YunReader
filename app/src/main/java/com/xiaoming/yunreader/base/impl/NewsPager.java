package com.xiaoming.yunreader.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.viewpagerindicator.TabPageIndicator;
import com.xiaoming.yunreader.R;
import com.xiaoming.yunreader.base.BaseMenuDetailPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author Slience_Manager
 * @time 2017/4/18 12:11
 */

public class NewsPager extends BaseMenuDetailPager {

    public String[] Type = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    public String[] Titles = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};


    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;
    @ViewInject(R.id.vp_news_detail)
    private ViewPager vp_news_detail;
    private ArrayList<NewsDetailPager> mNewsDetailPagers;  //新闻页面标签的集合

    public NewsPager(Activity mActivity) {
        super(mActivity);

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news, null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mNewsDetailPagers = new ArrayList<>();
        for (int i = 0; i < Titles.length; i++) {
            NewsDetailPager newsDetailPager = new NewsDetailPager(mActivity, Type[i]);
            mNewsDetailPagers.add(newsDetailPager);
        }

        vp_news_detail.setAdapter(new NewsDetailAdapter());
        indicator.setViewPager(vp_news_detail);

    }

    class NewsDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mNewsDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsDetailPager pager = mNewsDetailPagers.get(position);
            View rootView = pager.mRootView;
            container.addView(rootView);
            pager.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position % Titles.length];
        }
    }
}
