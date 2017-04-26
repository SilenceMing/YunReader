package com.xiaoming.yunreader.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.xiaoming.yunreader.R;
import com.xiaoming.yunreader.base.BaseMenuDetailPager;
import com.xiaoming.yunreader.base.impl.MusicPager;
import com.xiaoming.yunreader.base.impl.NewsPager;
import com.xiaoming.yunreader.base.impl.PhotosPager;
import com.xiaoming.yunreader.base.impl.SettingPager;
import com.xiaoming.yunreader.base.impl.SharePager;
import com.xiaoming.yunreader.base.impl.ZDPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<BaseMenuDetailPager> mPagers;
    private FrameLayout mFlContent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //声明定义，使用Toolbar控件替换Actionbar
        setSupportActionBar(mToolbar);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);

        //底部悬浮弹窗
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "底部浮动弹窗", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        //侧滑菜单
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //设置侧滑事件
        drawer.setDrawerListener(toggle);
        //将ActionBarDrawerToggle与DrawerLayout的状态同步
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //策划菜单的点击事件
        navigationView.setNavigationItemSelectedListener(this);

        initMenuDetailPager();
    }

    /**
     * 初始化Fragment
     */
    private void initMenuDetailPager() {
        mPagers = new ArrayList<>();
        mPagers.add(new NewsPager(MainActivity.this));
        mPagers.add(new ZDPager(MainActivity.this));
        mPagers.add(new PhotosPager(MainActivity.this));
        mPagers.add(new MusicPager(MainActivity.this));
        mPagers.add(new SettingPager(MainActivity.this));
        mPagers.add(new SharePager(MainActivity.this));

        setCurrentDetailPager(0,"热点新闻");
    }

    //设置点击Back键的事件
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;(只在第一次初始化菜单时调用)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //在onCreateOptionsMenu执行后，菜单被显示前调用；如果菜单已经被创建，则在菜单显示前被调用。 同样的，
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //策划菜单的点击事件的实现
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_news) {
            setCurrentDetailPager(0,"热点新闻");
        } else if (id == R.id.nav_zd) {
            setCurrentDetailPager(1,"知乎豆瓣");
        } else if (id == R.id.nav_photos) {
            setCurrentDetailPager(2,"精品美图");
        } else if (id == R.id.nav_music) {
            setCurrentDetailPager(3,"流行音乐");
        } else if (id == R.id.nav_settings) {
            setCurrentDetailPager(4,"设置");
        } else if (id == R.id.nav_share) {
            setCurrentDetailPager(5,"分享");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //点击侧滑菜单后关闭策划菜单
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setCurrentDetailPager(int position,String toolbarTitle) {
        BaseMenuDetailPager pager = mPagers.get(position);
        View rootView = pager.mRootView;
        mFlContent.removeAllViews();
        mFlContent.addView(rootView);
        pager.initData();
        mToolbar.setTitle(toolbarTitle);

    }


}
