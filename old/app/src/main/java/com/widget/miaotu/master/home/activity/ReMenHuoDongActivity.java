package com.widget.miaotu.master.home.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.master.home.adapter.RemenhuodongPagerAdapter;
import com.widget.miaotu.master.home.fragment.ReMenHuodongFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 热门活动
 */
public class ReMenHuoDongActivity extends MBaseActivity {


//    @BindView(R.id.tab_re_men_huo_dong)
//    SlidingTabLayout tabLayout;
    @BindView(R.id.vp_re_men_huo_dong)
    ViewPager viewPager;

    @BindView(R.id.tv_title)
    TextView title;
    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays = {"全部", "进行中", "已结束"};

    @Override
    protected MVCControl createControl() {

        return null;
    }

    @Override
    protected void initView() {
        title.setText("活动列表");


    }

    @OnClick({R.id.btn_back})
    public void onClicked(View view)  {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_re_men_huo_dong;
    }

    @Override
    protected void initDetailData() {
//        initTabAndView();
    }

//    private void initTabAndView() {
//
//
//        mFragments = new ArrayList<>();
//        mFragments.add(new ReMenHuodongFragment("全部"));
//        mFragments.add(new ReMenHuodongFragment("进行中"));
//        mFragments.add(new ReMenHuodongFragment("已结束"));
//
//        RemenhuodongPagerAdapter pagerAdapter = new RemenhuodongPagerAdapter(getSupportFragmentManager(), mFragments, mTitlesArrays);
//        viewPager.setAdapter(pagerAdapter);
//
//        tabLayout.setViewPager(viewPager, mTitlesArrays);//tab和ViewPager进行关联
//    }


}