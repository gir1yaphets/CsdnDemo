package com.example.pengxiaolve.csdndemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.pengxiaolve.csdndemo.R;
import com.example.pengxiaolve.csdndemo.adapter.PagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {

    private TabPageIndicator mTabIndicator;
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        mTabIndicator.setViewPager(mViewPager, 0);

        //test src
//        new Thread() {
//            @Override
//            public void run() {
//                String urlString = UrlUtils.generateUrl(Constant.NEWS_TYPE_YEJIE, 0);
//                String htmlString = HttpConnect.doGet(urlString);
//                ArrayList<NewsItemBean> newsItems = HtmlParse.htmlParse(htmlString);
//            }
//        }.start();
    }


}
