package com.widget.miaotu.master.other.guide;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.master.other.guide.GuideFirstFragment;
import com.widget.miaotu.master.other.guide.GuideSecondFragment;
import com.widget.miaotu.master.other.guide.GuideThirdFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;

    private List<Fragment> list;




    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initViewPager();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }




    /**
     * 方法描述: 欢迎页适配器
     * 创建人:
     * 创建时间:
     * 修改人:
     * 修改备注:
     */
    FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    };


    /**
     * 方法描述: 初始化欢迎页
     * 创建人:
     * 创建时间:
     * 修改人:
     * 修改备注:
     */
    public void initViewPager() {

        list = new ArrayList<>();

        list.add(new GuideFirstFragment());
        list.add(new GuideSecondFragment());
        list.add(new GuideThirdFragment());

        vpGuide.setAdapter(fragmentPagerAdapter);
        vpGuide.setOffscreenPageLimit(2);
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    ((GuideFirstFragment) list.get(0)).scaleFirst(positionOffset);
                    ((GuideSecondFragment) list.get(1)).scaleSecondIn(positionOffset);
                } else if (position == 1) {
                    ((GuideSecondFragment) list.get(1)).scaleSecondOut(positionOffset);
                    ((GuideThirdFragment) list.get(2)).scaleThirdIn(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
