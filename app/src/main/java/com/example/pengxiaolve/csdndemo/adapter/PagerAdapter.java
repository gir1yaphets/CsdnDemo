package com.example.pengxiaolve.csdndemo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pengxiaolve.csdndemo.fragment.MainFragment;

/**
 * Created by pengxiaolve on 16/6/10.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    public static final String[] TITLES = new String[]{"业界", "移动", "研发", "程序员", "云计算"};
    private MainFragment mFragment;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        mFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("NEWSTYPE", position + 1);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }
}
