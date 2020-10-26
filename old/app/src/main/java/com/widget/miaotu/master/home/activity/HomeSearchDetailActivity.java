package com.widget.miaotu.master.home.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.home.search.EditText_Clear;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.head.HeadSearchDetailBean;
import com.widget.miaotu.master.home.adapter.HomeSearchDetailAdapter;
import com.widget.miaotu.master.home.other.FenLeiPartShadowPopupView;
import com.widget.miaotu.master.home.other.HuiYuanPartShadowPopupView;
import com.widget.miaotu.master.home.other.ShaixuanPopupView;
import com.widget.miaotu.master.mvp.HomeSearchDetailControl;
import com.widget.miaotu.master.mvp.HomeSearchDetailView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class HomeSearchDetailActivity extends MBaseActivity<HomeSearchDetailControl> implements HomeSearchDetailView {


    @BindView(R.id.recyclerView_home_searchDetail)
    RecyclerView recyclerViewDetail;
    @BindView(R.id.editTextSearchDetail)
    EditText_Clear editText_clear;

    @BindView(R.id.iv_search_detail_top1)
    ImageView topImageView1;
    @BindView(R.id.iv_search_detail_top2)
    ImageView topImageView2;
    @BindView(R.id.iv_search_detail_top3)
    ImageView topImageView3;

    @BindView(R.id.tv_search_detail_top1)
    TextView topTextView1;
    @BindView(R.id.tv_search_detail_top2)
    TextView topTextView2;

    @BindView(R.id.ll_search_detail_top2)
    LinearLayout linearLayoutTop2;

    @BindView(R.id.srfLayout_SearDetail)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.iv_search_detail_back)
    ImageView iv_search_detail_back;


    private String searchWord = "";
    private HuiYuanPartShadowPopupView huiyuanPopupView;
    private FenLeiPartShadowPopupView fenleiPopupView;
    private ShaixuanPopupView shaixuanPopupView;


    private HomeSearchDetailAdapter homeSearchDetailAdapter;
    private int priority = 0; //优先搜索
    private String longitude = "";
    private String latitude = "";
    private String classifyName = "";//分类名称
    private String diameterFloor = "0";
    private String diameterUpper = "0";
    private String heightFloor = "0";
    private String heightUpper = "0";
    private String crownFloor = "0";
    private String crownUpper = "0";
    private String province = "";
    private String city = "";
    private int type = 3;
    private String plantType = "";
    private int companyStatus = 3;
    private int page = 1;
    private int numble = 10;


    @Override
    protected HomeSearchDetailControl createControl() {
        return new HomeSearchDetailControl();
    }

    @Override
    protected void initView() {
        //使activity全屏
//        QMUIStatusBarHelper.translucent(this);
        StatusBarUtil.fullScreen(this);


        longitude = String.valueOf(SPStaticUtils.getString(SPConstant.LONGITUDE));
        latitude = String.valueOf(SPStaticUtils.getString(SPConstant.LATITUDE));


        searchWord = getIntent().getStringExtra(SPConstant.SEARCH_INFO);
        editText_clear.setText(searchWord);

        //初始化Adapter
        initAdapter();

        //创建分类下拉列表
        showFenLeiPartShadow(linearLayoutTop2);


        //搜索文本框监听
        searEditCallBack();
    }

    private void initAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewDetail.setLayoutManager(linearLayoutManager);
        homeSearchDetailAdapter = new HomeSearchDetailAdapter();
        recyclerViewDetail.setAdapter(homeSearchDetailAdapter);


    }

    private void searEditCallBack() {


        editText_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logger.e("输入框数据变动  = " + s);
                //清除历史数据
                homeSearchDetailAdapter.clearData();

                clearChangeState();
                homeSearchDetailAdapter.notifyDataSetChanged();
            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                searchWord = editText_clear.getText().toString();


                showWaiteDialog("正在加载...");

                requestData();

                if (shaixuanPopupView!=null){
                    shaixuanPopupView.setDataChange(searchWord);

                }
            }
        });

        iv_search_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 改变常亮的值
     */
    private void clearChangeState() {

        priority = 0; //优先搜索
        classifyName = "";//分类名称
        diameterFloor = "0";
        diameterUpper = "0";
        heightFloor = "0";
        heightUpper = "0";
        crownFloor = "0";
        crownUpper = "0";
        province = "";
        city = "";
        type = 3;
        plantType = "";
        companyStatus = 3;
        page = 1;
        numble = 10;
    }

    private void requestData() {
        Logger.e(classifyName);
        HeadSearchDetailBean headSearchDetailBean = new HeadSearchDetailBean(0, searchWord, TextUtils.isEmpty(classifyName) ? null : classifyName, page, numble,
                longitude, latitude, priority, type == 3 ? null : type,
                Double.parseDouble(diameterUpper) == 0 ? null : Double.parseDouble(diameterFloor), Double.parseDouble(diameterUpper) == 0 ? null : Double.parseDouble(diameterUpper),
                Double.parseDouble(heightUpper) == 0 ? null : Double.parseDouble(heightFloor), Double.parseDouble(heightUpper) == 0 ? null : Double.parseDouble(heightUpper),
                Double.parseDouble(crownUpper) == 0 ? null : Double.parseDouble(crownFloor), Double.parseDouble(crownUpper) == 0 ? null : Double.parseDouble(crownUpper),

                TextUtils.isEmpty(province) ? null : province, TextUtils.isEmpty(city) ? null : city, TextUtils.isEmpty(plantType) ? null : plantType, companyStatus == 3 ? null : companyStatus
        );
        mControl.getDetailData(headSearchDetailBean);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search_detail;
    }


    @OnClick({R.id.ll_search_detail_top1, R.id.ll_search_detail_top2, R.id.ll_search_detail_top3})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search_detail_top1://会员优先
                topImageView1.setImageResource(R.mipmap.iv_top_up);
                topTextView1.setTextColor(Color.parseColor("#03DAC5"));
                topImageView2.setImageResource(R.mipmap.iv_top_down);
                topTextView2.setTextColor(Color.parseColor("#666666"));
                showHuiYuanPartShadow(view);
                break;
            case R.id.ll_search_detail_top2://分类
                topImageView2.setImageResource(R.mipmap.iv_top_up);
                topTextView2.setTextColor(Color.parseColor("#03DAC5"));
                topImageView1.setImageResource(R.mipmap.iv_top_down);
                topTextView1.setTextColor(Color.parseColor("#666666"));
                if (fenleiPopupView != null) {

                    fenleiPopupView.show();

                }
                break;
            case R.id.ll_search_detail_top3://筛选
                clearChangeState();
                if (shaixuanPopupView!=null){

                    new XPopup.Builder(this)
                            .popupPosition(PopupPosition.Right)//右边
                            .hasStatusBarShadow(true) //启用状态栏阴影
                            .asCustom(shaixuanPopupView)
                            .show();
                }



                break;
        }

    }


    /**
     * 会员优选弹窗
     *
     * @param v
     */
    private void showHuiYuanPartShadow(final View v) {
        if (huiyuanPopupView == null) {
            huiyuanPopupView = (HuiYuanPartShadowPopupView) new XPopup.Builder(this)
                    .atView(v)
                    .autoOpenSoftInput(true)
                    .setPopupCallback(new SimpleCallback() {
                        @Override
                        public void onShow() {

                        }

                        @Override
                        public void onDismiss() {
                            topImageView1.setImageResource(R.mipmap.iv_top_down);
                            topImageView2.setImageResource(R.mipmap.iv_top_down);
                            topTextView1.setTextColor(Color.parseColor("#666666"));
                            topTextView2.setTextColor(Color.parseColor("#666666"));
                        }
                    })
                    .asCustom(new HuiYuanPartShadowPopupView(this));

            huiyuanPopupView.setCallbackDoSome(new HuiYuanPartShadowPopupView.HYDoSomeCallBack() {
                @Override
                public void onPopDismiss(String mTitle) {
                    topTextView1.setText(mTitle);

                    //	优先搜索（0:会员优先；1：关注优先；2：最近距离；3：最新发布），当搜索苗木时需要传,默认会员优先
                    showWaiteDialog("正在加载数据...");
                    switch (mTitle) {
                        case "会员优先":
                            priority = 0;
                            homeSearchDetailAdapter.clearData();
                            requestData();
                            break;
                        case "关注优先":
                            priority = 1;
                            homeSearchDetailAdapter.clearData();
                            requestData();
                            break;
                        case "最近距离":
                            priority = 2;
                            homeSearchDetailAdapter.clearData();
                            requestData();
                            break;
                        case "最新发布":
                            priority = 3;
                            homeSearchDetailAdapter.clearData();
                            requestData();
                            break;
                    }

                }
            });
        }

        huiyuanPopupView.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fenleiPopupView == null) {
            fenleiPopupView.onDestroy();
        }
    }

    /**
     * 分类筛选
     *
     * @param v
     */
    private void showFenLeiPartShadow(View v) {
        if (fenleiPopupView == null) {
            fenleiPopupView = (FenLeiPartShadowPopupView) new XPopup.Builder(this)
                    .atView(v)
                    .autoOpenSoftInput(true)
                    .setPopupCallback(new SimpleCallback() {
                        @Override
                        public void onShow() {
//                            ToastUtil.showLong("显示了", HomeSearchDetailActivity.this);
                        }

                        @Override
                        public void onDismiss() {
                            topImageView1.setImageResource(R.mipmap.iv_top_down);
                            topImageView2.setImageResource(R.mipmap.iv_top_down);
                            topTextView1.setTextColor(Color.parseColor("#666666"));
                            topTextView2.setTextColor(Color.parseColor("#666666"));
                        }
                    })
                    .asCustom(new FenLeiPartShadowPopupView(this));

            fenleiPopupView.setCallbackDoSome(new FenLeiPartShadowPopupView.DoSomeCallBack() {
                @Override
                public void onPopItemClick(String title) {


                    if (!TextUtils.isEmpty(title)) {
                        if (title.equals("全部")) {
                            classifyName = "";
                            topTextView2.setText("全部");
                        } else {
                            classifyName = title;
                            topTextView2.setText(classifyName);
                        }

                        showWaiteDialog("正在加载...");

                        homeSearchDetailAdapter.clearData();


                        requestData();

                    }
                }
            });
        }


    }


    @Override
    protected void initDetailData() {

        showWaiteDialog("正在加载...");


        requestData();


        //创建右侧筛选侧滑
        shaixuanPopupView = new ShaixuanPopupView(this, new ShaixuanPopupView.DataChangeCallBack() {
            @Override
            public void selectChangeData(String sxsearchWord, String sxdiameterFloor, String sxdiameterUpper, String sxheightFloor, String sxheightUpper, String sxcrownFloor, String sxcrownUpper, String sxprovince, String sxcity, int sxtype, String sxplantType, int sxcompanyStatus) {
                searchWord = sxsearchWord;
                diameterFloor = sxdiameterFloor;
                diameterUpper = sxdiameterUpper;
                heightFloor = sxheightFloor;
                heightUpper = sxheightUpper;
                crownFloor = sxcrownFloor;
                crownUpper = sxcrownUpper;
                province = sxprovince;
                city = sxcity;
                type = sxtype;
                plantType = sxplantType;
                companyStatus = sxcompanyStatus;
                showWaiteDialog("正在加载...");


                homeSearchDetailAdapter.clearData();

                requestData();
            }
        });



        //刷新数据
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                homeSearchDetailAdapter.clearData();
                requestData();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;

                requestData();
            }
        });

    }

    @Override
    public void getDetailInfoSuc(HomeSearchDetailJavaBean homeSearchDetailJavaBean) {
        hideWaiteDialog();
        smartRefreshLayout.finishRefresh(true);
        smartRefreshLayout.finishLoadMore(true);

        if (page == 1) {

            homeSearchDetailAdapter.addData(HomeSearchDetailActivity.this, homeSearchDetailJavaBean);
            if (homeSearchDetailJavaBean.getSeedlingBaseInfos().size() < 10) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                smartRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
            }
            if (fenleiPopupView != null) {
                fenleiPopupView.cleadListData();
                fenleiPopupView.setDataChange(homeSearchDetailJavaBean);
            }
            return;
        }

        if (homeSearchDetailJavaBean.getSeedlingBaseInfos().size() == 0) {

            smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
            smartRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
            if (fenleiPopupView != null) {
                fenleiPopupView.cleadListData();
                fenleiPopupView.setDataChange(homeSearchDetailJavaBean);
            }
            homeSearchDetailAdapter.notifyDataSetChanged();
            return;
        }
        if (homeSearchDetailJavaBean.getSeedlingBaseInfos().size() > 0) {

            if (fenleiPopupView != null) {
                fenleiPopupView.cleadListData();
                fenleiPopupView.setDataChange(homeSearchDetailJavaBean);
            }
            homeSearchDetailAdapter.addData(HomeSearchDetailActivity.this, homeSearchDetailJavaBean);

        }


    }
}