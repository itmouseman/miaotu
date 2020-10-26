package com.widget.miaotu.master.miaopu.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.taglayout.BaseFragmentAdapter;
import com.widget.miaotu.master.mine.fragment.QiuGouMiaoMuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.mob.MobSDK.getContext;

/**
 * 苗木管理界面
 */
public class ManagerNurseryActivity extends MBaseActivity {

    @BindView(R.id.tabs_manager_nursery)
    QMUITabSegment nurseryTabSegment;

    @BindView(R.id.pager_manager_nursery)
    ViewPager nurseryViewPage;
    @BindView(R.id.btn_back)
    ImageButton btn_back;
    @BindView(R.id.tv_title)
    TextView tv_title;


    List<Fragment> mFragments;


    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_nursery;
    }

    @Override
    protected void initDetailData() {
        initTopBar();
        initTabAndPager();
    }


    private void initTopBar() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("苗木管理");


    }

    private void initTabAndPager() {

        mFragments = new ArrayList<>();

        mFragments.add(new ManagerGyinmiaomuListFragment("", this, "1"));//供应苗木

        mFragments.add(new QiuGouMiaoMuFragment("", this));//求购苗木


        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mFragments.size());

        nurseryViewPage.setAdapter(adapter);

        QMUITabBuilder builder = nurseryTabSegment.tabBuilder();
        builder.setSelectColor(Color.parseColor("#00CAB8"));
        builder.setNormalColor(Color.parseColor("#999999"));

        nurseryTabSegment.addTab(builder.setText("供应苗木").build(getContext()));
        nurseryTabSegment.addTab(builder.setText("求购苗木").build(getContext()));
        nurseryTabSegment.setupWithViewPager(nurseryViewPage, false);
        nurseryTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        nurseryTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
            }

            @Override
            public void onDoubleTap(int index) {
                nurseryTabSegment.clearSignCountView(index);
            }
        });
    }


}
