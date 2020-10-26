package com.widget.miaotu.master.home.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.ImageLoader;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.home.RecycleviewAdapterOfpopup;
import com.widget.miaotu.common.utils.other.ShareLinkInfo;
import com.widget.miaotu.common.utils.other.ShareUtils;
import com.widget.miaotu.common.utils.rxbus.MiaoMuDataChage;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.common.utils.taglayout.AppBarStateChangeListener;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.CollectionOrCanceBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;
import com.widget.miaotu.http.bean.SpecAllJavaBean;
import com.widget.miaotu.http.bean.head.HeadStatusBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.BannerImageHDetailAdapter;
import com.widget.miaotu.master.message.activity.ChatActivity;
import com.widget.miaotu.master.mvp.HomeTgAndMmControl;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.miaotu.master.mvp.HomeTgAndMmView;
import com.yanzhenjie.permission.AndPermission;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


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

    @BindView(R.id.rcy_guige_home_tg_and_mm)
    RecyclerView recycleViewGuiGe;

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

    @BindView(R.id.rl_home_tg_and_mm_from_other)
    RelativeLayout bottomFromHome;
    @BindView(R.id.rl_home_tg_and_mm_from_gongying)
    LinearLayout bottomFromGongYing;

    private String idMiaoMu;
    private LoadingPopupView mLoding;
    private SeedlingDetailJavaBean mSeedlingDetailJavaBean;

    private int mColletStatus = 1;
    private String mComFrom;

    @Override
    protected HomeTgAndMmControl createControl() {
        return new HomeTgAndMmControl();
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
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
                //分享
                String title = mSeedlingDetailJavaBean.getSeedlingName();
                String titleUrl = "";
                String content = mSeedlingDetailJavaBean.getDescribe();
                String imageUrl = mSeedlingDetailJavaBean.getHeadUrl();
                String url = ApiService.DEMAND_DETAIL_SHARE_URL + 12;

                String oldJson = mSeedlingDetailJavaBean.getSeedlingUrls();
                Gson gson = new Gson();
                List<ImgUrlJavaBean> imgUrlJavaBean = gson.fromJson(oldJson, new TypeToken<List<ImgUrlJavaBean>>() {
                }.getType());
                String contentImgs = "";
                if (imgUrlJavaBean.size() > 0) {
                    contentImgs = imgUrlJavaBean.get(0).getT_url();
                } else {
                    contentImgs = imageUrl;
                }

                ShareLinkInfo shareLinkInfo = new ShareLinkInfo(title
                        , titleUrl, content, contentImgs, url);

                ShareUtils.shareMiniProgram(mSeedlingDetailJavaBean.getId(), shareLinkInfo);
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
        mComFrom = getIntent().getStringExtra("comFrom");//0是从供应苗木过来的需要添加删除
        idMiaoMu = getIntent().getStringExtra(SPConstant.TRANSTENT_CONTENT);

        if (mComFrom.equals("0")) {//来自苗木供应界面
            bottomFromHome.setVisibility(View.GONE);
            bottomFromGongYing.setVisibility(View.VISIBLE);
        } else {
            bottomFromGongYing.setVisibility(View.GONE);
            bottomFromHome.setVisibility(View.VISIBLE);
        }

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
//        danJia.setText(seedlingDetailJavaBean.getPrice() == 0 ? "面议" : Html.fromHtml("￥<font color='#ED710C'><big><big>" + seedlingDetailJavaBean.getPrice() + "</big></big></font>/株"));
        danJia.setText(seedlingDetailJavaBean.getPrice() == 0 ? "面议" : "￥" + seedlingDetailJavaBean.getPrice() + "/株");

        //名字
        mmName.setText(seedlingDetailJavaBean.getSeedlingName());
        //种类
        if (TextUtils.isEmpty(seedlingDetailJavaBean.getPlantType())) {
            plantType.setVisibility(View.GONE);
        } else {
            plantType.setVisibility(View.VISIBLE);
            plantType.setText(seedlingDetailJavaBean.getPlantType());
        }

        //常用名
        changYongMing.setText("常用名 : " + seedlingDetailJavaBean.getCommonNames());
        //分类
        fenLeiM.setText("分类 : " + seedlingDetailJavaBean.getFirstClassify());

        if (!TextUtils.isEmpty(seedlingDetailJavaBean.getSpec())) {

            Gson gson = new Gson();
            List<SpecAllJavaBean> specAllJavaBeanList = gson.fromJson(seedlingDetailJavaBean.getSpec(), new TypeToken<List<SpecAllJavaBean>>() {
            }.getType());
            //高度
            recycleViewGuiGe.setLayoutManager(new LinearLayoutManager(this));
            recycleViewGuiGe.setAdapter(new BaseQuickAdapter<SpecAllJavaBean, BaseViewHolder>(R.layout.item_guige_home_tg_and_mm, specAllJavaBeanList) {
                @Override
                protected void convert(@NotNull BaseViewHolder baseViewHolder, SpecAllJavaBean bean) {
                    baseViewHolder.setText(R.id.item_tv_guige_name, bean.getSpecName());
                    baseViewHolder.setText(R.id.item_tv_guige_value, bean.getInterval() + bean.getUnit());
                }
            });


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
        qyname.setText(seedlingDetailJavaBean.getCompanyName());
        //企业地址
        qyLocation.setText(seedlingDetailJavaBean.getCompanyAddress());


        //是否收藏
        if (seedlingDetailJavaBean.getIsCollection() == 1) {//收藏状态；1：收藏；0：没有收藏
            mColletStatus = 0;
            ivShouchangMm.setImageResource(R.mipmap.iv_detail_shouchang);
            tvShouchangMm.setText("已收藏");
        } else {
            mColletStatus = 1;
            ivShouchangMm.setImageResource(R.mipmap.iv_detail_ushouchang);
            tvShouchangMm.setText("收藏");
        }

    }

    @OnClick({R.id.rl_qiYe_enter, R.id.tv_home_tg_and_mm_call, R.id.tv_home_tg_and_mm_sendMessage, R.id.ll_home_tg_and_mm_shouChang,
            R.id.ll_home_tg_and_mm_qiye, R.id.stv_delete_miaomu_gongying,R.id.stv_edit_miaomu_gongying})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_qiYe_enter:
            case R.id.ll_home_tg_and_mm_qiye:
                //企业

                Intent intent4 = new Intent();
                intent4.putExtra("userId", String.valueOf(mSeedlingDetailJavaBean.getUserId()));
                intent4.putExtra("company_id", mSeedlingDetailJavaBean.getCompanyId());
                intent4.setClass(this, CompanyDetailActivity.class);
                startActivity(intent4);
                break;
            case R.id.tv_home_tg_and_mm_call:
                callPhone();
                break;
            case R.id.tv_home_tg_and_mm_sendMessage:
                if (mSeedlingDetailJavaBean != null) {
                    UserCacheManager.save(String.valueOf(mSeedlingDetailJavaBean.getUserId()), mSeedlingDetailJavaBean.getNickname(), mSeedlingDetailJavaBean.getHeadUrl());
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, String.valueOf(mSeedlingDetailJavaBean.getUserId()));
                    intent.putExtra("chartTitle", mSeedlingDetailJavaBean.getNickname());
                    startActivity(intent);
                }

                break;
            case R.id.ll_home_tg_and_mm_shouChang:
                collectSeed(mColletStatus);
                break;
            case R.id.stv_delete_miaomu_gongying://删除该苗木
                deletMiaoMu(idMiaoMu, "4");
                break;
            case R.id.stv_xiajia_miaomu_gongying://下架该苗木
                deletMiaoMu(idMiaoMu, "3");
                break;
            case R.id.stv_edit_miaomu_gongying://编辑
                if (mSeedlingDetailJavaBean != null) {
                    Intent intent1 = new Intent(this, AddMiaoMuActivity.class);
                    intent1.putExtra("type", "1");
                    intent1.putExtra("gardenId", "");
                    intent1.putExtra("seedlingId", String.valueOf(mSeedlingDetailJavaBean.getId()));

                    startActivity(intent1);
                }

                break;

        }

    }


    /**
     * 删除苗木
     */
    private void deletMiaoMu(String id, String status) {//	1:上架；3：下架，4：删除

        RetrofitFactory.getInstence().API().optionSeedlingStatus(new HeadStatusBean(id, status)).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(HomeTgAndMmActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                Logger.e(t.toString());
                if (t.getStatus() == 100) {
                    ToastUtils.showShort("操作成功");
                    RxBus.getInstance().post(new MiaoMuDataChage(true));
                    finish();
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络请求异常");
            }
        });
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

                    new XPopup.Builder(this)
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

                    ToastUtils.showShort("需要电话权限");
                })
                .start();


    }


}