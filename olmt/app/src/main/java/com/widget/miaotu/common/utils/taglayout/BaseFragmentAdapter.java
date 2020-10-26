package com.widget.miaotu.common.utils.taglayout;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenlaisu on 2018/4/12.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    protected List<Fragment> mFragmentList;

    protected int size;

//    public BaseFragmentAdapter(FragmentManager fm) {
//        this(fm, null, null);
//    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, int size) {
        super(fm);
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        this.mFragmentList = fragmentList;
        this.size = size;
    }

//    public void add(Fragment fragment) {
//        if (isEmpty()) {
//            mFragmentList = new ArrayList<>();
//
//        }
//        mFragmentList.add(fragment);
//    }

    @Override
    public Fragment getItem(int position) {
        //        Logger.i("BaseFragmentAdapter position=" +position);
        return isEmpty() ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return isEmpty() ? 0 : mFragmentList.size();
    }

    public boolean isEmpty() {
        return mFragmentList == null;

    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles[position];
//    }

    /*  @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }*/

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
