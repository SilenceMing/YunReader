package com.xiaoming.yunreader.base.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaoming.yunreader.R;
import com.xiaoming.yunreader.activity.NewsDetailActivity;
import com.xiaoming.yunreader.base.BaseMenuDetailPager;
import com.xiaoming.yunreader.domain.NewsDataBean;
import com.xiaoming.yunreader.utils.CacheUtils;
import com.xiaoming.yunreader.utils.GlobalAPI;
import com.xiaoming.yunreader.view.ProgressDrawable;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Slience_Manager
 * @time 2017/4/18 19:15
 */

public class NewsDetailPager extends BaseMenuDetailPager {

    private String mType;

    @ViewInject(R.id.vp_newsTop)
    private ViewPager vp_newsTop;

    @ViewInject(R.id.tv_topNewsTitle)
    private TextView tv_topNewsTitle;

    @ViewInject(R.id.indicatorTop)
    private CirclePageIndicator indicatorTop;

    @ViewInject(R.id.lv_newsList)
    private ListView lv_newsList;

    @ViewInject(R.id.sr_refersh)
    private SwipeRefreshLayout sr_refresh;

    @ViewInject(R.id.iv_footer)
    private ImageView iv_footer;

    private ArrayList<NewsDataBean.NewData> mNewDatas;
    private Handler mHandler;
    private NewsDataAdapter mNewsDataAdapter;

    private int[] colors = {
            0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00
            , 0xFF00FFFF, 0xFF0000FF, 0xFF8B00FF};

    private Handler handler;
    private int mPosition;

    public NewsDetailPager(Activity activity, String type) {
        super(activity);
        mType = type;

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news_detail, null);
        x.view().inject(this, view);

        View mHeadView = View.inflate(mActivity, R.layout.item_news_top, null);
        x.view().inject(this, mHeadView);

        View mFooterView = View.inflate(mActivity, R.layout.item_news_footer, null);
        x.view().inject(this, mFooterView);


        lv_newsList.addHeaderView(mHeadView);
        lv_newsList.addFooterView(mFooterView);


        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //判断是否有缓存的数据
        String cacheData = CacheUtils.getCacheData(mType, mActivity);
        if (!TextUtils.isEmpty(cacheData)) {
            processData(cacheData);
        }
        getTopNewsFromServer(mType);

        //定义下拉刷新的业务逻辑
        sr_refresh.setColorSchemeColors(colors); //设置刷新图标的颜色

        sr_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
        lv_newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                NewsDataBean.NewData newData = mNewDatas.get(position + 3);
                intent.putExtra("newsUrl", newData.url);
                mActivity.startActivity(intent);
            }
        });


    }

    /**
     * SwipeRefreshLayou刷新方法
     */
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //重新请求网络数据
                        getTopNewsFromServer(mType);
                        //通知Adapter进行数据更新
                        mNewsDataAdapter.notifyDataSetChanged();
                        //更新完毕后，关闭下拉菜单
                        sr_refresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void getTopNewsFromServer(final String type) {
        RequestParams params = new RequestParams(GlobalAPI.NEWS_URL);
        params.addQueryStringParameter("type", mType);
        params.addQueryStringParameter("key", GlobalAPI.KEY);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                //设置数据缓存
                CacheUtils.setCacheData(type, result, mActivity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        NewsDataBean newsDataBean = gson.fromJson(result, NewsDataBean.class);
        mNewDatas = newsDataBean.result.data;
        vp_newsTop.setAdapter(new TopNewsDataAdapter());
        indicatorTop.setViewPager(vp_newsTop);
        indicatorTop.setSnap(true); //设置快照方式

        //事件要设置给indicatorTop
        indicatorTop.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //更新新闻标题 (但只在滑动的时候更新标题。则需要手动设置第一页标题)
                String title = mNewDatas.get(position).title;
                tv_topNewsTitle.setText(title);

                Message msg = new Message();
                msg.obj = position;
                handler.sendMessage(msg);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    mPosition = (int) msg.obj;
                }
            };
        }

        vp_newsTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                NewsDataBean.NewData newData = mNewDatas.get(mPosition);
                intent.putExtra("newsUrl", newData.url);
                mActivity.startActivity(intent);
            }
        });


        //手动设置第一页的标题
        tv_topNewsTitle.setText(mNewDatas.get(0).title);
        //默认第一个选中，（解决页面销毁后重新初始化时，IndicatorTop仍然保留上次位置
        indicatorTop.onPageSelected(0);
        mNewsDataAdapter = new NewsDataAdapter();
        lv_newsList.setAdapter(mNewsDataAdapter);

        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    int currentItem = vp_newsTop.getCurrentItem();
                    currentItem++;
                    if (currentItem > 3) {
                        currentItem = 0;
                    }
                    vp_newsTop.setCurrentItem(currentItem);
                    //继续发送延时三秒的消息
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
            };
            //发送三秒延时消息
            mHandler.sendEmptyMessageDelayed(0, 3000);
            vp_newsTop.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //鼠标按下  删除所有的广播消息
                            mHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            //鼠标抬起，再次发送广播消息
                            mHandler.sendEmptyMessageDelayed(0, 3000);


                            break;
                        case MotionEvent.ACTION_CANCEL:
                            //当出现冲突事件时，再次发送广播消息
                            mHandler.sendEmptyMessageDelayed(0, 3000);


                            break;
                        case MotionEvent.ACTION_BUTTON_PRESS:

                            Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                            NewsDataBean.NewData newData = mNewDatas.get(mPosition);
                            intent.putExtra("newsUrl", newData.url);
                            mActivity.startActivity(intent);

                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 顶部头条新闻适配器
     */
    class TopNewsDataAdapter extends PagerAdapter {
        private ImageOptions options;

        public TopNewsDataAdapter() {
            options = new ImageOptions.Builder().build();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            String ImgUrl = mNewDatas.get(position).thumbnail_pic_s;
            x.image().bind(imageView, ImgUrl, options);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //新闻列表适配器
    class NewsDataAdapter extends BaseAdapter {
        private ImageOptions options;

        public NewsDataAdapter() {
            options = new ImageOptions.Builder().build();
        }

        @Override
        public int getCount() {
            return mNewDatas.size() - 4;
        }

        @Override
        public NewsDataBean.NewData getItem(int position) {
            int index = position + 4;
            return mNewDatas.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            NewsDataBean.NewData mNewData = getItem(position);

            //生成样式随机数
            int style = new Random().nextInt(100) % 10;
            if (convertView == null) {
                if (style < 5) {
                    convertView = View.inflate(mActivity, R.layout.item_news_style1, null);
                } else {
                    if(TextUtils.isEmpty(mNewData.thumbnail_pic_s02) || TextUtils.isEmpty(mNewData.thumbnail_pic_s03)){
                        convertView = View.inflate(mActivity, R.layout.item_news_style1, null);
                    }else{
                        convertView = View.inflate(mActivity, R.layout.item_news_style2, null);
                    }
                }
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.iv_icon1 = (ImageView) convertView.findViewById(R.id.iv_icon1);
                viewHolder.iv_icon2 = (ImageView) convertView.findViewById(R.id.iv_icon2);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_author_name = (TextView) convertView.findViewById(R.id.tv_author_name);
                viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.tv_title.setText(mNewData.title);
            viewHolder.tv_author_name.setText(mNewData.author_name);
            viewHolder.tv_date.setText(mNewData.date);
            x.image().bind(viewHolder.iv_icon, mNewData.thumbnail_pic_s, options);
            x.image().bind(viewHolder.iv_icon1, mNewData.thumbnail_pic_s02, options);
            x.image().bind(viewHolder.iv_icon2, mNewData.thumbnail_pic_s03, options);

            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView iv_icon;
        public ImageView iv_icon1;
        public ImageView iv_icon2;
        public TextView tv_title;
        public TextView tv_author_name;
        public TextView tv_date;
    }


}
