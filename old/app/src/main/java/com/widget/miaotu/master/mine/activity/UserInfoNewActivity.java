package com.widget.miaotu.master.mine.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.taglayout.BaseFragmentAdapter;
import com.widget.miaotu.master.mine.fragment.GongYingMiaoMuFragment;
import com.widget.miaotu.master.mine.fragment.QiuGouMiaoMuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoNewActivity extends BaseActivity {


    @BindView(R.id.vp_user_info)
    ViewPager viewPager;
    @BindView(R.id.textview1)
    TextView textview1;
    @BindView(R.id.textview3)
    TextView textview3;


    private String userId = "0";

    List<Fragment> mFragments;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        userId = getIntent().getStringExtra("userId");

        mFragments = new ArrayList<>();

        mFragments.add(new GongYingMiaoMuFragment(userId + "", this, "1"));

        mFragments.add(new QiuGouMiaoMuFragment(userId, this));//我的求购


        textview1 = findViewById(R.id.textview1);
        textview3 = findViewById(R.id.textview3);


        initViewPager();
    }


    @OnClick({R.id.textview1, R.id.textview3, R.id.imageView63})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView63:
                finish();
                break;
            case R.id.textview1:
                textview1.setTextColor(this.getResources().getColor(R.color.white));
                textview1.setBackgroundColor(this.getResources().getColor(R.color.text_color_33));
                textview3.setTextColor(this.getResources().getColor(R.color.text_color_33));
                textview3.setBackgroundColor(this.getResources().getColor(R.color.white));
                viewPager.setCurrentItem(0);
                break;
            case R.id.textview3:
                textview1.setTextColor(this.getResources().getColor(R.color.text_color_33));
                textview1.setBackgroundColor(this.getResources().getColor(R.color.white));
                textview3.setTextColor(this.getResources().getColor(R.color.white));
                textview3.setBackgroundColor(this.getResources().getColor(R.color.text_color_33));
                viewPager.setCurrentItem(1);
                break;

        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mFragments.size());

        viewPager.setAdapter(adapter);
        MoveImage();
    }


    private void MoveImage() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        textview1.setTextColor(UserInfoNewActivity.this.getResources().getColor(R.color.white));
                        textview1.setBackgroundColor(UserInfoNewActivity.this.getResources().getColor(R.color.text_color_33));
                        textview3.setTextColor(UserInfoNewActivity.this.getResources().getColor(R.color.text_color_33));
                        textview3.setBackgroundColor(UserInfoNewActivity.this.getResources().getColor(R.color.white));
                        break;
                    case 1:
                        textview1.setTextColor(UserInfoNewActivity.this.getResources().getColor(R.color.text_color_33));
                        textview1.setBackgroundColor(UserInfoNewActivity.this.getResources().getColor(R.color.white));
                        textview3.setTextColor(UserInfoNewActivity.this.getResources().getColor(R.color.white));
                        textview3.setBackgroundColor(UserInfoNewActivity.this.getResources().getColor(R.color.text_color_33));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_new;
    }
}