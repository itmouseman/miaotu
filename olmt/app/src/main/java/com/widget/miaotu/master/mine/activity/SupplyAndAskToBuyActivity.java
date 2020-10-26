package com.widget.miaotu.master.mine.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.taglayout.BaseFragmentAdapter;
import com.widget.miaotu.master.mine.fragment.GongYingMiaoMuFragment;
import com.widget.miaotu.master.mine.fragment.QiuGouMiaoMuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 供应苗木和求购苗木   我的发布
 */
public class SupplyAndAskToBuyActivity extends BaseActivity {


        @BindView(R.id.vp_Supply_And_AskToBuy)
        ViewPager viewPager;
    @BindView(R.id.textview1)
    ShapeTextView textview1;
    @BindView(R.id.textview3)
    ShapeTextView textview3;


    private String userId = "0";

    List<Fragment> mFragments;

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initView() {
        textview1.setTextColor(this.getResources().getColor(R.color.white));
        textview1.setBackgroundColor(this.getResources().getColor(R.color.text_color_33));
        textview1.setRadius(3,0,3,0);

        textview3.setTextColor(this.getResources().getColor(R.color.text_color_33));
        textview3.setBackgroundColor(this.getResources().getColor(R.color.white));
        textview3.setRadius(0,3,0,3);


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
                textview1.setRadius(3,0,3,0);

                textview3.setTextColor(this.getResources().getColor(R.color.text_color_33));
                textview3.setBackgroundColor(this.getResources().getColor(R.color.white));
                textview3.setRadius(0,3,0,3);

                viewPager.setCurrentItem(0);
                break;
            case R.id.textview3:

                textview1.setTextColor(this.getResources().getColor(R.color.text_color_33));
                textview1.setBackgroundColor(this.getResources().getColor(R.color.white));
                textview1.setRadius(3,0,3,0);

                textview3.setTextColor(this.getResources().getColor(R.color.white));

                textview3.setBackgroundColor(this.getResources().getColor(R.color.text_color_33));
                textview3.setRadius(0,3,0,3);

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
                        textview1.setTextColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.white));
                        textview1.setBackgroundColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.text_color_33));
                        textview1.setRadius(3,0,3,0);

                        textview3.setTextColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.text_color_33));

                        textview3.setBackgroundColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.white));
                        textview3.setRadius(0,3,0,3);

                        break;
                    case 1:

                        textview1.setTextColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.text_color_33));
                        textview1.setBackgroundColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.white));
                        textview1.setRadius(3,0,3,0);

                        textview3.setTextColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.white));

                        textview3.setBackgroundColor(SupplyAndAskToBuyActivity.this.getResources().getColor(R.color.text_color_33));
                        textview3.setRadius(0,3,0,3);
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
        return R.layout.activity_supply_and_askto_buy;
    }
}