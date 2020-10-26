package com.widget.miaotu.master.home.activity;

import android.graphics.Color;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.taglayout.BaseFragmentAdapter;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.bean.GardenHeadlinesBean;
import com.widget.miaotu.master.home.fragment.FarmingTechnology;
import com.widget.miaotu.master.home.fragment.GardenHeadlinesFragment;
import com.widget.miaotu.master.home.fragment.GardenIPFragment;
import com.widget.miaotu.master.home.fragment.TopUnionFragment;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mob.MobSDK.getContext;

/**
 * author : heyang
 * <p>
 * date   : 2020/8/169:50
 * desc   :热点资讯
 * version: 1.0
 */
public class HotSpotInfomationActivity extends MBaseActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.tabs_hot_spot_information)
    QMUITabSegment tabSegment;

    @BindView(R.id.vp_hot_spot_information)
    ViewPager viewPager;
    private ArrayList<Fragment> mFragments;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        mTopBar.setTitle("热点资讯").setTextColor(Color.parseColor("#FFFFFF"));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_spot_information;
    }

    @Override
    protected void initDetailData() {
        initTabAndPager();
    }



    private void initTabAndPager() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("page", "0")
                .add("num", "10")
                .add("programId", "1")
                .build();
        Request request = new Request.Builder().url(ApiService.OLD_URl + "zmh/Information/selectInfoList").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Logger.i(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resuylt = response.body().string();
                GardenHeadlinesBean gardenHeadlinesBean = JSON.parseObject(resuylt, GardenHeadlinesBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyUI(gardenHeadlinesBean);
                    }
                });
            }
        });


    }

    private void notifyUI(GardenHeadlinesBean gardenHeadlinesBean) {

        mFragments = new ArrayList<>();

        mFragments.add(new GardenHeadlinesFragment(  this));//园林头条


        for (GardenHeadlinesBean.ContentBean contentBean : gardenHeadlinesBean.getContent()) {
            if (contentBean.getProgramName().trim().equals("园林IP")) {
                mFragments.add(new GardenIPFragment(contentBean.getProgramId(), this));//园林IP
            }
            if (contentBean.getProgramName().trim().equals("种养技术")) {
                mFragments.add(new FarmingTechnology(contentBean.getProgramId(), this));//种养技术
            }
            if (contentBean.getProgramName().trim().equals("百强联盟")) {
                mFragments.add(new TopUnionFragment(contentBean.getProgramId(), this));//百强联盟
            }
        }

        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mFragments.size());

        viewPager.setAdapter(adapter);

        QMUITabBuilder builder = tabSegment.tabBuilder();
        builder.setSelectColor(Color.parseColor("#00CAB8"));
        builder.setNormalColor(Color.parseColor("#999999"));
        tabSegment.addTab(builder.setText("园林头条").build(getContext()));
        tabSegment.addTab(builder.setText("园林IP").build(getContext()));
        tabSegment.addTab(builder.setText("种养技术").build(getContext()));
        tabSegment.addTab(builder.setText("百强联盟").build(getContext()));
        tabSegment.setupWithViewPager(viewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
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
                tabSegment.clearSignCountView(index);
            }
        });
    }
}
