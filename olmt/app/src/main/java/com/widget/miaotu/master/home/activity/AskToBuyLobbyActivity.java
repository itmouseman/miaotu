package com.widget.miaotu.master.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.BaseConstants;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AddresSelectUtils;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.home.EditViewWithDel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;
import com.widget.miaotu.http.bean.QiuGouHeadsjavaBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.head.HeadSearchThinkBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.other.login.LoginCodeActivity;
import com.yanzhenjie.permission.AndPermission;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 求购大厅
 */
public class AskToBuyLobbyActivity extends MBaseActivity {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.edit_ask_to_buy1)
    EditViewWithDel editViewWithDel;
    @BindView(R.id.recyclerView_ask_to_buy)
    RecyclerView recyclerViewAskToBuy;
    @BindView(R.id.recyclerView_adk_buy_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.ll_ask_to_buy_location)
    LinearLayout linearLayoutLocation;
    @BindView(R.id.ll_ask_to_buy_fabu)
    LinearLayout linearLayoutFaBu;
    @BindView(R.id.tv_ask_to_buy_location)
    TextView tv_ask_to_buy_location;

    @BindView(R.id.ll_location_and_fabu)
    LinearLayout ll_location_and_fabu;

    @BindView(R.id.srfl_adk_to_buy1)
    SmartRefreshLayout smartRefreshLayout1;

    @BindView(R.id.srfl_adk_to_buy2)
    SmartRefreshLayout smartRefreshLayout2;

    private int mPage = 1;
    private int mPageNum = 10;
    private String mSearchWord = "";
    private String mProvince = "";
    private String mCity = "";
    private int searchPageNum = 1;
    private int searchPageSize = 10;
    private BaseQuickAdapter<HomeSearchJavaBean, BaseViewHolder> mSearchAdapter;
    private int isViP = 0;
    private BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder> mAskToBuyListAdapter;
    private String tempName = "";//搜索时的名字
    private InputMethodManager mInputMethodManager;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initView() {
        initTopBar();

        if (!isLogin()) {
            isViP = 0;
        } else {
            //如果没登录的话报错
            isViP = Integer.parseInt(SPStaticUtils.getString(SPConstant.IS_VIP));
        }

    }

    /**
     * 判断用户是否登录
     */
    public boolean isLogin() {
        return !StringUtils.isEmpty(SPStaticUtils.getString(SPConstant.USER_ID));
    }

    @Override
    protected void initDetailData() {

        initAdapter();

        //搜素框初始化
        searchEditInit();
        //获取求购列表
        getAskToBuyLobbyList();

        //刷新
        refreshListAll();


    }


    private void searchThink() {

        RetrofitFactory.getInstence().API().getSearch(new HeadSearchThinkBean(tempName, "seedling", searchPageNum, searchPageSize))
                .compose(TransformerUi.<BaseEntity<List<HomeSearchJavaBean>>>setThread())
                .subscribe(new BaseObserver<List<HomeSearchJavaBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomeSearchJavaBean>> t) throws Exception {
                        if (t.getStatus() == 100) {
                            List<HomeSearchJavaBean> dataList = t.getData();
                            if (searchPageNum == 1) {
                                mSearchAdapter.setNewData(dataList);
                                if (dataList.size() < 10) {
                                    smartRefreshLayout2.setNoMoreData(true);
                                }
                                if (dataList.size() == 0) {
                                    //布局为空  设置

                                    smartRefreshLayout2.finishRefresh(true);
                                    View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
                                    //添加空视图
                                    mSearchAdapter.setEmptyView(emptyView);

                                }
                                return;
                            }

                            if (dataList.size() == 0) {
                                smartRefreshLayout2.setNoMoreData(true);
                                return;
                            }

                            for (HomeSearchJavaBean homeSearchJavaBean : dataList) {
                                mSearchAdapter.addData(homeSearchJavaBean);
                            }
                            smartRefreshLayout2.finishLoadMore(true);

                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        smartRefreshLayout2.finishRefresh(true);
                        smartRefreshLayout2.finishLoadMore(true);
                        ToastUtils.showShort("网络请求出错");
                    }
                });
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        mTopBar.setTitle("求购大厅").setTextColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask_to_buy_lobby;
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void searchEditInit() {
        //初始化输入法
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        recyclerViewAskToBuy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideSoftKeyboard();
            }
        });

        recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                hideSoftKeyboard();
            }
        });


        editViewWithDel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                smartRefreshLayout1.setVisibility(View.GONE);
                ll_location_and_fabu.setVisibility(View.GONE);
                smartRefreshLayout2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchPageNum = 1;
                mPage = 1;
            }

            @Override
            public void afterTextChanged(Editable editable) {

                tempName = editViewWithDel.getText().toString();
                if (!AndroidUtils.isNullOrEmpty(tempName)) {
                    searchThink();
                } else {
                    mSearchWord = "";
                    smartRefreshLayout1.setVisibility(View.VISIBLE);
                    ll_location_and_fabu.setVisibility(View.VISIBLE);
                    smartRefreshLayout2.setVisibility(View.GONE);
                    getAskToBuyLobbyList();
                }

            }
        });
    }

    private void initAdapter() {
        //求购列表Adapter
        recyclerViewAskToBuy.setLayoutManager(new LinearLayoutManager(AskToBuyLobbyActivity.this));
        mAskToBuyListAdapter = new BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder>(R.layout.item_fragment_qiugoumiaomu, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, QiuGouMiaoMuJavaBean qiuGouMiaoMuJavaBean) {
                Logger.e(qiuGouMiaoMuJavaBean.toString());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_1, qiuGouMiaoMuJavaBean.getSeedlingName());
                if (TextUtils.isEmpty(qiuGouMiaoMuJavaBean.getPlantType())) {
                    baseViewHolder.getView(R.id.st_qiuGouMiaoMu_2).setVisibility(View.GONE);
                } else {
                    baseViewHolder.getView(R.id.st_qiuGouMiaoMu_2).setVisibility(View.VISIBLE);
                    baseViewHolder.setText(R.id.st_qiuGouMiaoMu_2, qiuGouMiaoMuJavaBean.getPlantType());
                }

                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_3, qiuGouMiaoMuJavaBean.getWantBuyNum() + "株");
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_4, qiuGouMiaoMuJavaBean.getCreateTime());

                Gson gson = new Gson();
                List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(qiuGouMiaoMuJavaBean.getSpec().replaceAll("SpecAllJavaBean", ""), new TypeToken<List<HomeMmSpecJavaBean>>() {
                }.getType());
                if (homeMmSpecJavaBeanList.size() > 1) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                            + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit());

                } else if (homeMmSpecJavaBeanList.size() == 1) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit());

                }

                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_6, "联系人：" + qiuGouMiaoMuJavaBean.getName());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_phone, " " + qiuGouMiaoMuJavaBean.getPhone());
                baseViewHolder.getView(R.id.tv_qiuGouMiaoMu_phone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callPhone(qiuGouMiaoMuJavaBean.getPhone());


                    }
                });
                if (qiuGouMiaoMuJavaBean.getStatus() == 2 || qiuGouMiaoMuJavaBean.getStatus() == 1) {//状态;0:求购中；1：结束求购；2：已删除'
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.VISIBLE);
                } else {
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.GONE);
                }


                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_7, "用苗地: " + qiuGouMiaoMuJavaBean.getProvince() + " " + qiuGouMiaoMuJavaBean.getCity());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_8, "产地要求:" + qiuGouMiaoMuJavaBean.getFromProvince() + qiuGouMiaoMuJavaBean.getFromCity() + "货源");
                if (TextUtils.isEmpty(qiuGouMiaoMuJavaBean.getCompanyName())) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, "个人求购");
                } else {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, qiuGouMiaoMuJavaBean.getCompanyName() + " >");
                    baseViewHolder.getView(R.id.tv_qiuGouMiaoMu_9).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String[] key = {SPConstant.COMPANY_ID, SPConstant.USER_ID};
                            String[] value = {qiuGouMiaoMuJavaBean.getCompanyId() + "", "1"};

                            IntentUtils.startIntent(AskToBuyLobbyActivity.this, CompanyDetailActivity.class, key, value);
                        }
                    });
                }

                if (qiuGouMiaoMuJavaBean.getIsVip() == 1) {
                    baseViewHolder.getView(R.id.iv_qgmu_wing).setVisibility(View.VISIBLE);
                }

            }
        };
        recyclerViewAskToBuy.setAdapter(mAskToBuyListAdapter);


        //搜索列表Adapter
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(AskToBuyLobbyActivity.this));
        mSearchAdapter = new BaseQuickAdapter<HomeSearchJavaBean, BaseViewHolder>(R.layout.item_ask_buy_search, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeSearchJavaBean homeSearchJavaBean) {
                baseViewHolder.setText(R.id.tv_item_ask_buy_search, homeSearchJavaBean.getName());
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSearchWord = homeSearchJavaBean.getName();
                        editViewWithDel.setText(mSearchWord);
                        smartRefreshLayout1.setVisibility(View.VISIBLE);
                        ll_location_and_fabu.setVisibility(View.VISIBLE);
                        smartRefreshLayout2.setVisibility(View.GONE);
                        getAskToBuyLobbyList();
                    }
                });
            }
        };
        recyclerViewSearch.setAdapter(mSearchAdapter);

    }


    /**
     * 打电话弹窗
     */
    @SuppressLint("WrongConstant")
    private void callPhone(String phone) {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CALL_PHONE)
                .onGranted(permissions -> {

                    IntentUtils.openCall(AskToBuyLobbyActivity.this, phone);
                })
                .onDenied(permissions -> {

                    ToastUtils.showShort("需要电话权限");
                })
                .start();

    }

    private void refreshListAll() {
        //列表刷新
        smartRefreshLayout1.setEnableRefresh(true);
        smartRefreshLayout1.setEnableLoadMore(true);
        smartRefreshLayout1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getAskToBuyLobbyList();
            }
        });
        smartRefreshLayout1.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getAskToBuyLobbyList();
            }
        });

        //搜索刷新
        smartRefreshLayout2.setEnableRefresh(true);
        smartRefreshLayout2.setEnableLoadMore(true);
        smartRefreshLayout2.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                searchThink();
            }
        });
        smartRefreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                searchPageNum++;
                searchThink();
            }
        });
    }


    @OnClick({R.id.ll_ask_to_buy_location, R.id.ll_ask_to_buy_fabu})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ask_to_buy_location://位置
                AddresSelectUtils.selectPICKview(getWindow().getDecorView().findViewById(android.R.id.content), this, new AddresSelectUtils.SelectAddressCallBack() {
                    @Override
                    public void selectAddressBack(String province, String city, String address) {


                        tv_ask_to_buy_location.setText(address);
                        searchPageNum = 1;
                        mPage = 1;
                        if (city.equals("不限")) {
                            mProvince = "";
                            mCity = "";
                        } else {
                            mProvince = province;
                            mCity = city;
                        }
                        getAskToBuyLobbyList();
                    }
                });
                break;
            case R.id.ll_ask_to_buy_fabu://求购
                if (!isLogin()) {
                    IntentUtils.startIntent(AskToBuyLobbyActivity.this, LoginCodeActivity.class);
                } else {
                    startActivity(new Intent(this, HomeFaBuQiuGouActivity.class));

                }
                break;
        }
    }

    private void getAskToBuyLobbyList() {
//        showWaiteDialog("正在加载中...");
        RetrofitFactory.getInstence().API().getWantBuyList(new QiuGouHeadsjavaBean(2, mPage, mPageNum, mSearchWord, "", mProvince, mCity, isViP))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<QiuGouMiaoMuJavaBean>>(this) {
            @Override
            protected void onSuccess(BaseEntity<List<QiuGouMiaoMuJavaBean>> t) throws Exception {
//                hideWaiteDialog();

                smartRefreshLayout1.finishRefresh(true);
                if (t.getStatus() == 100) {
                    List<QiuGouMiaoMuJavaBean> qiuGouMiaoMuJavaBeanList = t.getData();
                    if (mPage == 1) {
                        mAskToBuyListAdapter.setNewData(qiuGouMiaoMuJavaBeanList);
                        if (qiuGouMiaoMuJavaBeanList.size() < 10) {
                            smartRefreshLayout1.setNoMoreData(true);
                        }
                        if (qiuGouMiaoMuJavaBeanList.size() == 0) {
                            //空太显示
                            View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));

                            //添加空视图
                            mAskToBuyListAdapter.setEmptyView(emptyView);
                        }
                        return;
                    }

                    if (qiuGouMiaoMuJavaBeanList.size() == 0) {
                        smartRefreshLayout1.setNoMoreData(true);
                        return;
                    }

                    for (QiuGouMiaoMuJavaBean qiuGouMiaoMuJavaBean : qiuGouMiaoMuJavaBeanList) {
                        mAskToBuyListAdapter.addData(qiuGouMiaoMuJavaBean);
                    }
                    smartRefreshLayout1.finishLoadMore(true);

                } else {
                    ToastUtils.showShort(t.getMessage());
                }

            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
//                hideWaiteDialog();
                smartRefreshLayout1.finishRefresh(true);
                smartRefreshLayout1.finishLoadMore(true);
                ToastUtils.showShort("请求异常");
            }
        });
    }
}
