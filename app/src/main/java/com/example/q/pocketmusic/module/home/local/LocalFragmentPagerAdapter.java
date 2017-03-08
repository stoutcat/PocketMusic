package com.example.q.pocketmusic.module.home.local;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by Cloud on 2016/11/17.
 */

public class LocalFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabAttrList;
    private List<Fragment> fragments;
    private Context context;


    public LocalFragmentPagerAdapter(Context context, FragmentManager fm, List<String> toolbarAttrList, List<Fragment> fragments) {
        super(fm);
        this.tabAttrList = toolbarAttrList;
        this.fragments = fragments;
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabAttrList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  tabAttrList.get(position);
    }
}