package com.widget.miaotu.master.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RemenhuodongPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> mFragments;
    private final String[] mTitlesArrays;

    public RemenhuodongPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> fragments, String[] titlesArrays) {
        super(supportFragmentManager);
        mFragments = fragments;
        mTitlesArrays =titlesArrays;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlesArrays[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
