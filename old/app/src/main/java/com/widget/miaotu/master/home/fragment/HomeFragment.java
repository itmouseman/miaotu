package com.widget.miaotu.master.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ToastUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.MyLocation;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HomeInitJavaBean;
import com.widget.miaotu.http.bean.HomeNineJavaBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.bean.SListByTypeJavaBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.home.activity.BaseInfoDemandTechAddActivity;
import com.widget.miaotu.master.home.activity.HomeFaBuQiuGouActivity;
import com.widget.miaotu.master.home.activity.HomeFenLeiActivity;
import com.widget.miaotu.master.home.activity.HomeSearchActivity;
import com.widget.miaotu.master.home.activity.HomeSearchDetailActivity;
import com.widget.miaotu.master.home.activity.HomeTgAndMmActivity;
import com.widget.miaotu.master.home.adapter.BannerImageAdapter;

import com.widget.miaotu.master.home.adapter.HomeZuixmmAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.master.home.other.HomePagerBottomPopup;
import com.widget.miaotu.master.mine.activity.UserInfoNewActivity;
import com.widget.miaotu.master.other.login.LoginCodeActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.mob.MobSDK.getContext;

/**
 * 首页的Fragment
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.home_banner)
    Banner bannerHome;

    @BindView(R.id.recyclerView_home_item)
    RecyclerView recyclerViewHomeItem;

    @BindView(R.id.recyclerView_home_host)
    RecyclerView recyclerViewHomeHost;

    @BindView(R.id.recyclerView_home_newsTuiGuang)
    RecyclerView recyclerViewHomeNewsTuiGuang;

    @BindView(R.id.recyclerView_home_newsMiaoMu)
    RecyclerView recyclerViewHomeNewsMiaoMu;


    @BindView(R.id.home_refreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.iv_home_fabu)
    ImageView imageViewFabu;
    @BindView(R.id.home_nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.tv_home_location)
    TextView tv_home_location;

    @BindView(R.id.tv_home_zxmm_tab)
    TextView tabZuixinMm;
    @BindView(R.id.tv_home_fujinmiaomu_tab)
    TextView tabFuJinMMu;

    private int page = 1;
    private HomeZuixmmAdapter homeZuixmmAdapter;
    private int mMiaoMuType = 0;
    private String locationLon;
    private String locationLat;


    @Override
    protected void initViewAndData(View view) {


        RxBus.getInstance().toObservableSticky(this, MyLocation.class).subscribe(new Consumer<MyLocation>() {
            @Override
            public void accept(MyLocation myLocation) throws Exception {
                tv_home_location.setText(myLocation.location);
                locationLon = myLocation.getLon();
                locationLat = myLocation.getLat();
            }
        });

        showWaiteDialog("正在加载数据...");
        jiugongge();


        LinearLayoutManager layoutManagerZuiXinMiaoMu = new LinearLayoutManager(getFragmentContext());
        layoutManagerZuiXinMiaoMu.setOrientation(RecyclerView.VERTICAL);
        recyclerViewHomeNewsMiaoMu.setLayoutManager(layoutManagerZuiXinMiaoMu);
        recyclerViewHomeNewsMiaoMu.setNestedScrollingEnabled(false);
        homeZuixmmAdapter = new HomeZuixmmAdapter(getFragmentContext());
        recyclerViewHomeNewsMiaoMu.setAdapter(homeZuixmmAdapter);
        initDataAndUi();

        //刷新界面
        refreshData();

        //
        nScrollListener();

        //设置最新苗木以及附近苗木tab
        miaomuTabLayout();

    }

    private void miaomuTabLayout() {

        //最新苗木
        tabZuixinMm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabZuixinMm.setTextSize(18);
                tabZuixinMm.setTextColor(Color.parseColor("#333333"));

                tabFuJinMMu.setTextSize(15);
                tabFuJinMMu.setTextColor(Color.parseColor("#666666"));

                showWaiteDialog("正在加载中...");
                mMiaoMuType = 0;
                if (homeZuixmmAdapter != null) {
                    homeZuixmmAdapter.clearData();
                    mastNewsAndFujinMiaoMu(mMiaoMuType, page);

                }
            }
        });
        //附近苗木
        tabFuJinMMu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabFuJinMMu.setTextSize(18);
                tabFuJinMMu.setTextColor(Color.parseColor("#333333"));

                tabZuixinMm.setTextSize(15);
                tabZuixinMm.setTextColor(Color.parseColor("#666666"));

                showWaiteDialog("正在加载中...");
                mMiaoMuType = 4;
                if (homeZuixmmAdapter != null) {
                    homeZuixmmAdapter.clearData();
                    mastNewsAndFujinMiaoMu(mMiaoMuType, page);

                }
            }
        });

    }

    private void nScrollListener() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                //Log.e(TAG, "onScrollChange: " + scrollX +"---" + scrollY + "----" +oldScrollX + "---" + oldScrollY );
                //监听滚动状态

                if (scrollY > oldScrollY || scrollY < oldScrollY) {//向下或向下滚动
                    imageViewFabu.setVisibility(View.GONE);
                }


                if (scrollY == 0) {// 滚动到顶
                    imageViewFabu.setVisibility(View.VISIBLE);
                }
//                // 滚动到底
//                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    imageViewFabu.setVisibility(View.VISIBLE);
//                }

            }
        });


    }

    /**
     * 刷新界面内容
     */
    private void refreshData() {
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                showWaiteDialog("正在加载数据...");
                initDataAndUi();

            }
        });
        //滑动监听
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                showWaiteDialog("正在加载数据...");
                mastNewsAndFujinMiaoMu(mMiaoMuType, page);

            }
        });

    }

    @OnClick({R.id.iv_home_fenLei, R.id.rl_homeSearch1, R.id.iv_home_fabu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_fenLei://分类
                IntentUtils.startIntent(getActivity(), HomeFenLeiActivity.class);
                break;

            case R.id.rl_homeSearch1://搜索
                IntentUtils.startIntent(getActivity(), HomeSearchActivity.class);
                break;
            case R.id.iv_home_fabu://发布

                new XPopup.Builder(getContext())
                        .isDestroyOnDismiss(false)
                        .asCustom(new HomePagerBottomPopup(getContext(), new HomePagerBottomPopup.BottomPopupClickCallBack() {
                            @Override
                            public void homeTianJiaMiaoMu(View view) {//添加苗木
                                if (!isLogin()) {
                                    IntentUtils.startIntent(getActivity(), LoginCodeActivity.class);
                                } else {
                                    //判断是否有苗圃 分别进入苗圃页面或添加苗木页面
                                    List mdata = new ArrayList<NurseryNameBean>();
                                    RetrofitFactory.getInstence().API().gardenList().compose(TransformerUi.setThread())
                                            .subscribe(new BaseObserver<List<GardenListBean>>(getFragmentContext()) {
                                                @Override
                                                protected void onSuccess(BaseEntity<List<GardenListBean>> t) throws Exception {
                                                    if (t.getStatus() == 100) {
                                                        List<GardenListBean> gardenListBeans = t.getData();

                                                        Logger.e(gardenListBeans.toString());
                                                        if (gardenListBeans != null) {
                                                            for (GardenListBean graden : gardenListBeans) {
                                                                mdata.add(new NurseryNameBean(graden.getGardenName(), graden.getId() + "", 0));
                                                            }

                                                            if (mdata.size() == 0) {//没有可添加苗木的苗圃，请先添加苗圃
                                                                startActivity(new Intent(getFragmentContext(), BaseInfoDemandTechAddActivity.class));

                                                                return;
                                                            }

                                                            if (mdata.size() == 1) {
                                                                //直接跳转
                                                                Intent intent = new Intent();
                                                                intent.putExtra("type", "0");
                                                                intent.putExtra("id", String.valueOf(gardenListBeans.get(0).getId()));
                                                                intent.setClass(getFragmentContext(), AddMiaoMuActivity.class);
                                                                startActivity(intent);

                                                                return;
                                                            }
                                                        }
                                                    }else {
                                                        ToastUtils.showShort(t.getMessage());
                                                    }
                                                }

                                                @Override
                                                protected void onFail(Throwable throwable) throws Exception {
                                                    ToastUtils.showShort("网络错误");
                                                }
                                            });


                                }

                            }

                            @Override
                            public void homeFaBuQiuGou(View view) {//发布求购
                                startActivity(new Intent(getFragmentContext(), HomeFaBuQiuGouActivity.class));

                            }
                        }))
                        .show();
                break;
        }

    }


    /**
     * 首页Banner
     *
     * @param bannerBeans
     */

    private void initBannerUI(List<HomeInitJavaBean.BannerBean> bannerBeans) {

        if (bannerHome != null) {
            bannerHome.addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setAdapter(new BannerImageAdapter(bannerBeans, new BannerImageAdapter.BannerClickCallBack() {
                        @Override
                        public void jumpWeb(HomeInitJavaBean.BannerBean bannerBean) {
                            if (!TextUtils.isEmpty(bannerBean.getBusiness()) && (bannerBean.getBusiness()).contains("http")) {
                                IntentUtils.gotoWebView(getActivity(), bannerBean.getBusiness(), bannerBean.getId() + "");
                            }

                        }
                    }))
                    .setIndicator(new CircleIndicator(getFragmentContext()))
                    .start();
        }

    }


    /**
     * 首页九宫格分类
     */
    private void jiugongge() {
        //首页九宫格
        List<HomeNineJavaBean> homeNineJavaBeans = new ArrayList<>();
        HomeNineJavaBean reMenHuoDong = new HomeNineJavaBean("热门活动", R.mipmap.home_item1);
        homeNineJavaBeans.add(reMenHuoDong);
        HomeNineJavaBean keChengXuexi = new HomeNineJavaBean("课程学习", R.mipmap.home_item2);
        homeNineJavaBeans.add(keChengXuexi);
        HomeNineJavaBean reDianzixun = new HomeNineJavaBean("热点资讯", R.mipmap.home_item3);
        homeNineJavaBeans.add(reDianzixun);
        HomeNineJavaBean ziZizhuanqu = new HomeNineJavaBean("资材专区", R.mipmap.home_item4);
        homeNineJavaBeans.add(ziZizhuanqu);
        HomeNineJavaBean caigoudating = new HomeNineJavaBean("求购大厅", R.mipmap.home_item5);
        homeNineJavaBeans.add(caigoudating);
        HomeNineJavaBean jingpingzhuanqu = new HomeNineJavaBean("精品专区", R.mipmap.home_item6);
        homeNineJavaBeans.add(jingpingzhuanqu);
        HomeNineJavaBean qinghuozhuanqu = new HomeNineJavaBean("清货专区", R.mipmap.home_item7);
        homeNineJavaBeans.add(qinghuozhuanqu);
        HomeNineJavaBean jizizhuanqu = new HomeNineJavaBean("基质专区", R.mipmap.home_item8);
        homeNineJavaBeans.add(jizizhuanqu);
        recyclerViewHomeItem.setLayoutManager(new GridLayoutManager(getFragmentContext(), 4));
        recyclerViewHomeItem.setAdapter(new CommonAdapter<HomeNineJavaBean>(getFragmentContext(), R.layout.home_nine_item, homeNineJavaBeans) {
            @Override
            protected void convert(ViewHolder holder, HomeNineJavaBean homeNineJavaBean, int position) {
                holder.setText(R.id.tv_home_nine, homeNineJavaBean.getTitle());
                holder.setImageResource(R.id.iv_home_nine, homeNineJavaBean.getImageId());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0://热门活动
                                ToastUtil.showShort("暂无开发", mContext);
//                                IntentUtils.startIntent(getActivity(), ReMenHuoDongActivity.class);
                                break;

                            case 4://求购大厅
                                break;
                            case 5://精品专区
                                break;
                            case 6://清货专区
                                break;
                            case 1://课程学习
                            case 2://热点资讯
                            case 3://资材专区
                            case 7://基质专区
                                ToastUtil.showShort("暂无开发", mContext);
                                break;
                        }
                    }
                });
            }
        });


    }

    /**
     * 热门苗木
     */
    private void remenMiaoMu(List<String> mTitleList) {
        //首页热门苗木

        List<HomeNineJavaBean> homeNineJavaBeans = new ArrayList<>();
        HomeNineJavaBean rmmm1 = new HomeNineJavaBean(mTitleList.get(0), R.mipmap.home_rmmm1);
        homeNineJavaBeans.add(rmmm1);
        HomeNineJavaBean rmmm2 = new HomeNineJavaBean(mTitleList.get(1), R.mipmap.home_rmmm2);
        homeNineJavaBeans.add(rmmm2);
        HomeNineJavaBean rmmm3 = new HomeNineJavaBean(mTitleList.get(2), R.mipmap.home_rmmm3);
        homeNineJavaBeans.add(rmmm3);
        recyclerViewHomeHost.setLayoutManager(new GridLayoutManager(getFragmentContext(), 3));
        recyclerViewHomeHost.setAdapter(new CommonAdapter<HomeNineJavaBean>(getFragmentContext(), R.layout.home_rmmm_item, homeNineJavaBeans) {
            @Override
            protected void convert(ViewHolder holder, HomeNineJavaBean homeNineJavaBean, int position) {
                holder.setText(R.id.tv_home_rmmm, homeNineJavaBean.getTitle());
                holder.setImageResource(R.id.iv_home_rmmm, homeNineJavaBean.getImageId());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //跳转到搜索详情页面
                        String[] key = {SPConstant.SEARCH_INFO};
                        String[] value = {homeNineJavaBean.getTitle()};
                        IntentUtils.startIntent(getActivity(), HomeSearchDetailActivity.class, key, value);
                    }
                });
            }
        });
    }

    /**
     * 最新推广
     */
    private void mastNewsTuiguang(List<HomeInitJavaBean.SeedlingPromoteBean> seedlingPromotes) {
        //最新推广
        LinearLayoutManager layoutManagerTuiguang = new LinearLayoutManager(getFragmentContext());
        layoutManagerTuiguang.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHomeNewsTuiGuang.setLayoutManager(layoutManagerTuiguang);
        recyclerViewHomeNewsTuiGuang.setAdapter(new CommonAdapter<HomeInitJavaBean.SeedlingPromoteBean>(getFragmentContext(), R.layout.home_tuiguang_item, seedlingPromotes) {
            @Override
            protected void convert(ViewHolder holder, HomeInitJavaBean.SeedlingPromoteBean seedlingPromoteBean, int position) {

                holder.setText(R.id.tv_home_tuiguang, seedlingPromoteBean.getName());

                String oldJson = seedlingPromoteBean.getSeedlingUrls();
                Gson gson = new Gson();
                List<ImgUrlJavaBean> imgUrlJavaBean = gson.fromJson(oldJson, new TypeToken<List<ImgUrlJavaBean>>() {
                }.getType());
                GlideUtils.loadUrl(getFragmentContext(), imgUrlJavaBean.get(0).getT_url(), (ImageView) holder.getView(R.id.iv_home_tuiguang));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] key = {SPConstant.TRANSTENT_CONTENT};
                        String[] value = {seedlingPromoteBean.getId() + ""};
                        IntentUtils.startIntent(getActivity(), HomeTgAndMmActivity.class, key, value);
                    }
                });
            }
        });

    }

    /**
     * 最新  附近 苗木
     */
    private void mastNewsAndFujinMiaoMu(int type, int page) {
        //最新苗木

        JSONObject root = new JSONObject();
        try {
            root.put("type", type);
            root.put("page", page);
            root.put("num", "10");
            if (type == 4) {
                root.put("lon", locationLon);
                root.put("lat", locationLat);
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            RetrofitFactory.getInstence().API().getSeedlingsListByType(requestBody).compose(this.<BaseEntity<List<SListByTypeJavaBean>>>setThread())
                    .subscribe(new BaseObserver<List<SListByTypeJavaBean>>(getFragmentContext()) {


                        @Override
                        protected void onSuccess(BaseEntity<List<SListByTypeJavaBean>> t) throws Exception {
                            hideWaiteDialog();
                            if (t.getStatus() == 100) {
                                List<SListByTypeJavaBean> sListByTypeJavaBeanLists = t.getData();

                                smartRefreshLayout.finishRefresh();
                                smartRefreshLayout.finishLoadMore();

                                if (page == 1) {
                                    homeZuixmmAdapter.addData(sListByTypeJavaBeanLists);
                                    if (sListByTypeJavaBeanLists.size() < 10) {
                                        // 这个方法是在最后一页，没有更多数据时调用的，会在页面底部标记没有更多数据
                                        smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                                        // 这个方法最重要，当在最后一页调用完上一个完成加载并标记没有更多数据的方法时，需要将refreshLayout的状态更改为还有更多数据的状态，此时就需要调用此方法，参数为false代表还有更多数据，true代表没有更多数据
                                        smartRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5

                                    }
                                    return;
                                }

                                if (sListByTypeJavaBeanLists.size() == 0) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                                    smartRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
                                    return;
                                }
                                if (sListByTypeJavaBeanLists.size() > 0) {

                                    homeZuixmmAdapter.addData(sListByTypeJavaBeanLists);

                                }

                            } else {
                                smartRefreshLayout.finishRefresh();
                                smartRefreshLayout.finishLoadMore();
                                ToastUtils.showShort(t.getMessage());
                            }


                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            hideWaiteDialog();
                            // 这两个方法是在加载失败时调用的
                            smartRefreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                            smartRefreshLayout.finishLoadMore(false);//结束加载（加载失败）
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initDataAndUi() {


        RetrofitFactory.getInstence().API().getHomeInitInfo()
                .compose(this.<BaseEntity<HomeInitJavaBean>>setThread())
                .subscribe(new BaseObserver<HomeInitJavaBean>(getFragmentContext()) {
                    @Override
                    protected void onSuccess(BaseEntity<HomeInitJavaBean> t) throws Exception {
                        hideWaiteDialog();

                        HomeInitJavaBean homeInitJavaBean = t.getData();

                        ArrayList<String> stringArrayList = new ArrayList<>();


                        for (int i = 0; i < homeInitJavaBean.getNews().size(); i++) {
                            stringArrayList.add(homeInitJavaBean.getNews().get(i).getId() + "");
                        }


                        //首页Banner
                        List<HomeInitJavaBean.BannerBean> bannerBeans = homeInitJavaBean.getBanner();
                        initBannerUI(bannerBeans);


                        //热门苗木
                        List<String> hotTitle = homeInitJavaBean.getHot();
                        remenMiaoMu(hotTitle);

                        List<HomeInitJavaBean.SeedlingPromoteBean> seedlingPromote = homeInitJavaBean.getSeedlingPromote();
                        //最新推广
                        mastNewsTuiguang(seedlingPromote);

                        //最新苗木
                        mastNewsAndFujinMiaoMu(mMiaoMuType, page);

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {

                        hideWaiteDialog();
                        Logger.e(throwable.getMessage());
                    }
                });


    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_home;
    }
}
