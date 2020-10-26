package com.widget.miaotu.master.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.rclayout.RCImageView;
import com.widget.miaotu.common.utils.rxbus.MyTouchEvent;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.common.utils.taglayout.AppBarLayoutStateChangeListener;
import com.widget.miaotu.common.utils.taglayout.BaseFragmentAdapter;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.CompanyInfoMeBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.fragment.AddTreeFragment;
import com.widget.miaotu.master.home.fragment.CompanyInfoFragment;
import com.widget.miaotu.master.home.fragment.WantBuyListNewFragment;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 企业详情
 */
public class CompanyDetailActivity extends MBaseActivity {

    @BindView(R.id.tabs)
    MagicIndicator magicIndicatorUserInfo;
    @BindView(R.id.vp_user_info)
    ViewPager vpUserInfo;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.iv_user_info_close)
    ImageView ivUserInfoClose;
    @BindView(R.id.tv_user_info_title)
    TextView title;
    @BindView(R.id.iv_user_info_share)
    ImageView share;
    @BindView(R.id.srl_user_info)
    SmartRefreshLayout srlUserInfo;
    @BindView(R.id.tv_company_fans_num)
    TextView tvCompanyFansNum;
    @BindView(R.id.tv_company_follow)
    TextView tvCompanyFollow;
    @BindView(R.id.tv_me_real_name)
    TextView tvMeRealName;
    @BindView(R.id.tv_company_main_camp)
    TextView tvCompanyMainCamp;
    @BindView(R.id.iv_company_logo)
    RCImageView ivCompanyLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;


    private ArrayList<Fragment> mFragments;


    private String[] mTitle = new String[3];//{"基本信息", "苗木", "求购"}

    private List<String> mDataList = new ArrayList<>();

    private String userId = "0";
    private String id = "";//企业id
    private int realName;
    private String type = "";
    private String isFollow = "";

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {

        userId = getIntent().getStringExtra(SPConstant.USER_ID);
        id = getIntent().getStringExtra(SPConstant.COMPANY_ID);
//        if(SPStaticUtils.getString("user_id").equals(userId)){
//            //企业人是自己
//            type = "1";
//        }

        refreshData();

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                switch (state) {
                    case EXPANDED:
                        Log.v("CoordinatorLayout", "EXPANDED");
                        break;
                    case COLLAPSED:
//                        ImmersionBar.with(CompanyDetailActivity.this).statusBarColor(R.color.colorFAFAFA).init();
                        StatusBarUtil.setStatusBarColor(CompanyDetailActivity.this, R.color.colorFAFAFA);
                        QMUIStatusBarHelper.setStatusBarLightMode(CompanyDetailActivity.this);
                        title.setText("详情");
                        share.setImageDrawable(getResourceDrawable(R.mipmap.ic_seedling_share));
                        rlUserInfo.setBackground(getResourceDrawable(R.color.colorFAFAFA));
                        ivUserInfoClose.setImageDrawable(getResourceDrawable(R.mipmap.ic_base_back_white));

                        break;
                    case INTERMEDIATE:
//                        ImmersionBar.with(CompanyDetailActivity.this).statusBarColor(R.color.color0ECDBC).init();
                        StatusBarUtil.setStatusBarColor(CompanyDetailActivity.this, R.color.color0ECDBC);
                        QMUIStatusBarHelper.setStatusBarDarkMode(CompanyDetailActivity.this);
                        title.setText("");
                        share.setImageDrawable(getResourceDrawable(R.mipmap.ic_seedling_share));
                        rlUserInfo.setBackgroundColor(Color.parseColor("#00000000"));
                        ivUserInfoClose.setImageDrawable(getResourceDrawable(R.mipmap.ic_base_back_white));

                        break;
                }
            }
        });

    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        srlUserInfo.setPrimaryColors(getResourceColor(R.color.color0ECDBC));
        srlUserInfo.setBackgroundColor(getResourceColor(R.color.color0ECDBC));
        srlUserInfo.setEnableLoadMore(true);
        srlUserInfo.setEnableRefresh(true);
        srlUserInfo.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                RxBus.getInstance().post(new MyTouchEvent(true));
                refreshLayout.finishRefresh(1000);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_detail;
    }

    @Override
    protected void initDetailData() {
        JSONObject root = new JSONObject();

        try {
            root.put("companyId", id);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            RetrofitFactory.getInstence().API().getCompanyInfo(requestBody)
                    .compose(TransformerUi.<BaseEntity<CompanyInfoMeBean>>setThread())
                    .subscribe(new BaseObserver<CompanyInfoMeBean>(BaseApplication.instance()) {
                        @Override
                        protected void onSuccess(BaseEntity<CompanyInfoMeBean> t) throws Exception {

                            Logger.e(t.toString());
                            initDataInfo(t.getData());


                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据填充
     *
     * @param data
     */
    private void initDataInfo(CompanyInfoMeBean data) {
        Glide.with(this).load(data.getLogo()).into(ivCompanyLogo);
        tvCompanyName.setText(data.getName() + "");
        realName = data.getIsAuth();
        if (-1 == data.getIsAuth()) {
            tvMeRealName.setBackground(getResources().getDrawable(R.drawable.bg_green_company_auth_false));
            tvMeRealName.setTextColor(getResourceColor(R.color.text_color_EC));
            if ("1".equals(type)) {
                tvMeRealName.setText("去认证");
            } else {
                tvMeRealName.setText("未认证");
            }
        } else if (1 == data.getIsAuth()) {
            tvMeRealName.setBackground(getResources().getDrawable(R.drawable.bg_green_company_auth_true));
            tvMeRealName.setTextColor(getResourceColor(R.color.white));
            tvMeRealName.setText("审核中");
        } else if (2 == data.getIsAuth()) {
            tvMeRealName.setBackground(getResources().getDrawable(R.drawable.bg_green_company_auth_true));
            tvMeRealName.setTextColor(getResourceColor(R.color.white));
            tvMeRealName.setText("已认证");
        } else if (3 == data.getIsAuth()) {
            tvMeRealName.setBackground(getResources().getDrawable(R.drawable.bg_green_company_auth_false));
            tvMeRealName.setTextColor(getResourceColor(R.color.text_color_EC));
            if ("1".equals(type)) {
                tvMeRealName.setText("去认证");
            } else {
                tvMeRealName.setText("未通过");
            }
        }

        tvCompanyFansNum.setText("粉丝数：" + data.getFollowCount());

        isFollow = data.getIsFollow() + "";

        if ("0".equals(data.getIsFollow() + "")) {
            tvCompanyFollow.setText("关注");
            tvCompanyFollow.setBackground(getResources().getDrawable(R.drawable.bg_green_company_follow_true));
            tvCompanyFollow.setTextColor(getResourceColor(R.color.color00cab8));
        } else {
            tvCompanyFollow.setText("已关注");
            tvCompanyFollow.setBackground(getResources().getDrawable(R.drawable.bg_green_company_follow_false));
            tvCompanyFollow.setTextColor(getResourceColor(R.color.text_color_99));
        }


        if ("1".equals(type)) {
            tvCompanyFollow.setVisibility(View.GONE);
        }
        tvCompanyMainCamp.setText("主营：" + data.getBusiness() + "");


        mFragments = new ArrayList<>();


        mFragments.add(new CompanyInfoFragment(data.getContact() + "", data.getContact_mobile() + "",
                data.getGardenCount() + "", data.getAddress() + data.getAddress_detail(), data.getIntroduction() + "", data.getStyle_photos() + ""));
        //苗木
        mFragments.add(new AddTreeFragment(userId + "", this, "1"));

        //求购
        mFragments.add(new WantBuyListNewFragment(userId, "1", this));
        //{"基本信息", "苗木", "求购"}
        mTitle[0] = "基本信息";
        mTitle[1] = "苗木(" + data.getSeedlingCount() + ")";
        mTitle[2] = "求购(" + data.getSeedlingWantBuyCount() + ")";
        mDataList.addAll(Arrays.asList(mTitle));
        initMagicIndicator();
        initViewPager();


    }


    @OnClick({R.id.iv_user_info_close, R.id.tv_company_follow, R.id.tv_me_real_name})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_me_real_name:
                if ("1".equals(type)) {
                    if (-1 == realName || 3 == realName) {
                        //跳转实名认证
                        Intent intent_real = new Intent();
//                        intent_real.setClass(getContext(), RealCommonActivity.class);
//                        startActivity(intent_real);
                    }
                }
                break;
            case R.id.iv_user_info_close:
                finish();
                break;
            case R.id.tv_company_follow:
//                JSONObject requestParams = new JSONObject();
//                requestParams.put("followId", id);
//                RxHttpUtils.createApi(ApiService.class)
//                        .getCompanyFollow(requestParams)
//                        .compose(Transformer.switchSchedulers())
//                        .subscribe(new DataObserverNew<JSONObject>() {
//                            @Override
//                            protected void onError(String errorMsg) {
//                            }
//                            @Override
//                            protected void onSuccess(JSONObject data) {
//                                if("0".equals(isFollow)){
//                                    //未关注
//                                    isFollow = "1";
//                                    tvCompanyFollow.setText("已关注");
//                                    tvCompanyFollow.setBackground(getResources().getDrawable(R.drawable.bg_green_company_follow_false));
//                                    tvCompanyFollow.setTextColor(getResourceColor(R.color.text_color_99));
//                                }else{
//                                    //已关注
//                                    isFollow = "0";
//                                    tvCompanyFollow.setText("关注");
//                                    tvCompanyFollow.setBackground(getResources().getDrawable(R.drawable.bg_green_company_follow_true));
//                                    tvCompanyFollow.setTextColor(getResourceColor(R.color.color00cab8));
//                                }
//                            }
//                        });
                break;
        }
    }

    private void initMagicIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorGreen05C1B0));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpUserInfo.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 20));
                linePagerIndicator.setColors(getResources().getColor(R.color.colorGreen05C1B0));
                return linePagerIndicator;
            }
        });
        magicIndicatorUserInfo.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(CompanyDetailActivity.this, 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicatorUserInfo);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        vpUserInfo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });

    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mFragments.size());

        vpUserInfo.setAdapter(adapter);

    }


}