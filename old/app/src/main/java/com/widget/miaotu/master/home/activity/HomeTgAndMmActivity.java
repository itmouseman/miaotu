package com.widget.miaotu.master.home.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.ImageLoader;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.ToastUtil;
import com.widget.miaotu.common.utils.home.RecycleviewAdapterOfpopup;
import com.widget.miaotu.common.utils.other.ShareLinkInfo;
import com.widget.miaotu.common.utils.other.ShareUtils;
import com.widget.miaotu.common.utils.taglayout.AppBarStateChangeListener;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.CollectionOrCanceBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;
import com.widget.miaotu.http.bean.SpecAllJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.BannerImageHDetailAdapter;
import com.widget.miaotu.master.mvp.HomeTgAndMmControl;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.miaotu.master.mvp.HomeTgAndMmView;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 苗木详情
 */
public class HomeTgAndMmActivity extends MBaseActivity<HomeTgAndMmControl> implements HomeTgAndMmView {

    Toolbar toolbar;

    @BindView(R.id.home_banner_detail1)
    Banner bannerHomeDetail;
    @BindView(R.id.recyclerView_banner_dt_bottom)
    RecyclerView bannerBottom;
    @BindView(R.id.home_detail_appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_home_tg_and_mm_danJia)
    TextView danJia;
    @BindView(R.id.tv_home_tg_and_mm_Name)
    TextView mmName;
    @BindView(R.id.tv_home_tg_and_mm_zhongLei)
    TextView plantType;
    @BindView(R.id.tv_home_tg_and_mm_ChangYongM)
    TextView changYongMing;

    @BindView(R.id.tv_home_tg_and_mm_FenLeiM)
    TextView fenLeiM;

    @BindView(R.id.tv_home_tg_and_mm_GaoDu)
    TextView gaoDu;
    @BindView(R.id.tv_home_tg_and_mm_Guanfu)
    TextView guanfu;
    @BindView(R.id.tv_home_tg_and_mm_FenZhiShu)
    TextView fenZhiShu;
    @BindView(R.id.tv_home_tg_and_mm_KuCun)
    TextView kuCun;

    @BindView(R.id.tv_home_tg_and_mm_pushTime)
    TextView pushTime;
    @BindView(R.id.iv_home_tg_and_mm_describe)
    TextView describe;

    @BindView(R.id.iv_home_tg_and_mm_qyLog)
    ImageView qyLog;
    @BindView(R.id.iv_home_tg_and_mm_qyName)
    TextView qyname;
    @BindView(R.id.iv_home_tg_and_mm_qyLocation)
    TextView qyLocation;
    @BindView(R.id.main3_CollapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_shouchang_mm)
    ImageView ivShouchangMm;
    @BindView(R.id.tv_shouchang_mm)
    TextView tvShouchangMm;

    private String idMiaoMu;
    private LoadingPopupView mLoding;
    private SeedlingDetailJavaBean mSeedlingDetailJavaBean;
    private String mType;
    private String mId;

    private String mUserId;
    private int mColletStatus = 1;

    @Override
    protected HomeTgAndMmControl createControl() {
        return new HomeTgAndMmControl();
    }

    @Override
    protected void initView() {

        StatusBarUtil.fullScreen(this);


        toolbar = (Toolbar) findViewById(R.id.main3_toolbar);
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main3_CollapsingToolbarLayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
        toolbar.setTitleMarginStart(300);
        toolbar.setNavigationIcon(R.drawable.ic_back_detail);
        //左上角返回箭头的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //通过设置toolbar进行监听,在setSupportActionBar(Toolbar toolbar)之前设置可能会失 效.
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                ToastUtil.showShort("分享", HomeTgAndMmActivity.this);
//String title, String titleUrl, String content, String imageUrl, String url
                String title = mSeedlingDetailJavaBean.getCommonNames();
                String titleUrl = "";
                String content = mSeedlingDetailJavaBean.getDescribe();
                String imageUrl = mSeedlingDetailJavaBean.getHeadUrl();
                String url = ApiService.DEMAND_DETAIL_SHARE_URL + 12;
                ShareLinkInfo shareLinkInfo = ShareLinkInfo.create(title
                        , titleUrl, content, imageUrl, url);
                ShareUtils.shareLink(WechatMoments.NAME, shareLinkInfo);

                return false;
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle("详情");
        collapsingToolbarLayout.setExpandedTitleTextAppearance(0);//设置收缩钱字体大小,这个style是自己写的
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TitleTextSize);//设置收缩后字体大小
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(0, 0, 0, 0));//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                } else {
                    //中间状态
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //写一个menu的资源文件.然后创建就行了.
        getMenuInflater().inflate(R.menu.toobar_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_tg_and_mm;
    }

    @Override
    protected void initDetailData() {
        mType = getIntent().getStringExtra(SPConstant.ADDTREE_TYPE);
        mId = getIntent().getStringExtra(SPConstant.GARDEN_ID);
//        mSeedid = getIntent().getStringExtra(Key_Name.SEED_ID);
        mUserId = getIntent().getStringExtra(SPConstant.USER_ID);
        idMiaoMu = getIntent().getStringExtra(SPConstant.TRANSTENT_CONTENT);
        mLoding = (LoadingPopupView) new XPopup.Builder(HomeTgAndMmActivity.this)
                .asLoading("正在加载中...")
                .show();
        mControl.getDataHomeTgAndMmDetail(idMiaoMu);

    }

    @Override
    public void getDataHomeTgAndMmDetailSuc(SeedlingDetailJavaBean seedlingDetailJavaBean) {
        mLoding.dismiss();
        mSeedlingDetailJavaBean = seedlingDetailJavaBean;

        if (!TextUtils.isEmpty(seedlingDetailJavaBean.getSeedlingUrls())) {

            Gson gson = new Gson();
            List<ImgUrlJavaBean> imgUrlJavaBeanList = gson.fromJson(seedlingDetailJavaBean.getSeedlingUrls(), new TypeToken<List<ImgUrlJavaBean>>() {
            }.getType());
            //填充图片数据
            initBannerUIDetail(imgUrlJavaBeanList);
        }


        //填充其他数据
        initOtherUIDetail(seedlingDetailJavaBean);


    }

    /**
     * 填充图片数据
     *
     * @param bannerBeans
     */
    private void initBannerUIDetail(List<ImgUrlJavaBean> bannerBeans) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < bannerBeans.size(); i++) {
            list.add(bannerBeans.get(i).getT_url());
        }

        if (bannerHomeDetail != null) {
            bannerHomeDetail.addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setAdapter(new BannerImageHDetailAdapter(bannerBeans, new BannerImageHDetailAdapter.BannerClicickHDetail() {
                        @Override
                        public void itemBannerClick(BannerImageHDetailAdapter.BannerDetailViewHolder holder, int position) {


                            new XPopup.Builder(holder.itemView.getContext()).asImageViewer(holder.imageView, position, list,
                                    new OnSrcViewUpdateListener() {
                                        @Override
                                        public void onSrcViewUpdate(final ImageViewerPopupView popupView, final int position) {
                                            bannerHomeDetail.getViewPager2().setCurrentItem(position, false);
                                            //一定要post，因为setCurrentItem内部实现是RecyclerView.scrollTo()，这个是异步的
                                            bannerHomeDetail.getViewPager2().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //由于ViewPager2内部是包裹了一个RecyclerView，而RecyclerView始终维护一个子View
                                                    RecyclerView rv = (RecyclerView) bannerHomeDetail.getViewPager2().getChildAt(0);
                                                    //再拿子View，就是ImageView
                                                    popupView.updateSrcView((ImageView) rv.getChildAt(0));
                                                }
                                            });
                                        }
                                    }, new ImageLoader())
                                    .show();

                        }
                    }))
                    .setIndicator(new CircleIndicator(HomeTgAndMmActivity.this))
                    .start();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        bannerBottom.setLayoutManager(linearLayoutManager);


        bannerBottom.setAdapter(new RecycleviewAdapterOfpopup(this, R.layout.adapter_image2, list));

    }

    /**
     * 填充其他数据
     */
    private void initOtherUIDetail(SeedlingDetailJavaBean seedlingDetailJavaBean) {

        //价格
        danJia.setText(seedlingDetailJavaBean.getPrice() == 0 ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedlingDetailJavaBean.getPrice() + "</big></big></font>/株"));
        //名字
        mmName.setText(seedlingDetailJavaBean.getSeedlingName());
        //种类
        plantType.setText(seedlingDetailJavaBean.getPlantType());
        //常用名
        changYongMing.setText("常用名 : " + seedlingDetailJavaBean.getCommonNames());
        //分类
        fenLeiM.setText("分类 : " + seedlingDetailJavaBean.getFirstClassify());

        if (!TextUtils.isEmpty(seedlingDetailJavaBean.getSpec())) {

            Gson gson = new Gson();
            List<SpecAllJavaBean> specAllJavaBeanList = gson.fromJson(seedlingDetailJavaBean.getSpec(), new TypeToken<List<SpecAllJavaBean>>() {
            }.getType());
            //高度
            for (int i = 0; i < specAllJavaBeanList.size(); i++) {
                if (specAllJavaBeanList.get(i).getSpecName().equals("高度")) {
                    gaoDu.setText(specAllJavaBeanList.get(i).getInterval() + specAllJavaBeanList.get(i).getUnit());
                } else if (specAllJavaBeanList.get(i).getSpecName().equals("冠幅")) {
                    guanfu.setText(specAllJavaBeanList.get(i).getInterval() + specAllJavaBeanList.get(i).getUnit());
                } else if (specAllJavaBeanList.get(i).getSpecName().equals("分枝数")) {
                    fenZhiShu.setText(specAllJavaBeanList.get(i).getInterval() + specAllJavaBeanList.get(i).getUnit());
                } else if (specAllJavaBeanList.get(i).getSpecName().equals("袋重")) {

                }
            }

        }

        //库存
        kuCun.setText(seedlingDetailJavaBean.getRepertory() + "株");

        //发布时间
        pushTime.setText(seedlingDetailJavaBean.getCreateTime());

        //描述
        describe.setText(seedlingDetailJavaBean.getDescribe());

        //企业图标
        GlideUtils.loadUrl(this, seedlingDetailJavaBean.getLogo(), qyLog);
        //企业名字
        qyname.setText(seedlingDetailJavaBean.getCommonNames());
        //企业地址
        qyLocation.setText(seedlingDetailJavaBean.getCompanyAddress());

        //是否收藏
        if (seedlingDetailJavaBean.getIsCollection()==1){//收藏状态；1：收藏；0：没有收藏
            mColletStatus = 0;
            ivShouchangMm.setImageResource(R.mipmap.iv_detail_shouchang);
            tvShouchangMm.setText("已收藏");
        }else {
            mColletStatus = 1;
            ivShouchangMm.setImageResource(R.mipmap.iv_detail_ushouchang);
            tvShouchangMm.setText("收藏");
        }

    }

    @OnClick({R.id.rl_qiYe_enter, R.id.tv_home_tg_and_mm_call, R.id.tv_home_tg_and_mm_sendMessage, R.id.ll_home_tg_and_mm_shouChang, R.id.ll_home_tg_and_mm_qiye})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_qiYe_enter:
                ToastUtil.showShort("企业", this);

                String[] key = {SPConstant.COMPANY_ID, SPConstant.USER_ID};
                String[] value = {mSeedlingDetailJavaBean.getCompanyId() + "", mSeedlingDetailJavaBean.getUserId() + ""};

                IntentUtils.startIntent(this, CompanyDetailActivity.class, key, value);
                break;
            case R.id.tv_home_tg_and_mm_call:
                callPhone();
                break;
            case R.id.tv_home_tg_and_mm_sendMessage:
                ToastUtil.showShort("发消息", this);
                break;
            case R.id.ll_home_tg_and_mm_shouChang:
                collectSeed(mColletStatus);
                break;
            case R.id.ll_home_tg_and_mm_qiye:
                ToastUtil.showShort("底部的企业", this);
                break;
        }

    }

    /**
     * 收藏 取消苗木
     */
    private void collectSeed(int status) {

        RetrofitFactory.getInstence().API().updateCollectSeedling(new CollectionOrCanceBean(Integer.parseInt(idMiaoMu), status))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(HomeTgAndMmActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                if (t.getStatus() == 100) {
                    if (status == 1) {
                        mColletStatus = 0;
                        ivShouchangMm.setImageResource(R.mipmap.iv_detail_shouchang);
                        tvShouchangMm.setText("已收藏");
                        ToastUtils.showShort("收藏成功");
                    } else {
                        mColletStatus = 1;
                        ivShouchangMm.setImageResource(R.mipmap.iv_detail_ushouchang);
                        tvShouchangMm.setText("收藏");
                        ToastUtils.showShort("取消收藏成功");
                    }

                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络异常");
            }
        });
    }


    /**
     * 打电话弹窗
     */
    @SuppressLint("WrongConstant")
    private void callPhone() {


        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CALL_PHONE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.

                    new XPopup.Builder(this)
//                        .hasBlurBg(true)
//                         .dismissOnTouchOutside(false)
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
//                        .isLightStatusBar(true)
                            .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                            .asConfirm("电话咨询", mSeedlingDetailJavaBean.getMobile(),
                                    "取消", "拨打电话",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            IntentUtils.openCall(HomeTgAndMmActivity.this, mSeedlingDetailJavaBean.getMobile());
                                        }
                                    }, null, false)
                            .show();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    ToastUtil.showShort("需要电话权限", HomeTgAndMmActivity.this);
                })
                .start();


    }


}