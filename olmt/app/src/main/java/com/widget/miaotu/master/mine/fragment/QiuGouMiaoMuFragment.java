package com.widget.miaotu.master.mine.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.BaseConstants;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.dialog.DeleteBottomPopup;
import com.widget.miaotu.common.utils.dialog.SharePopup;
import com.widget.miaotu.common.utils.other.ShareLinkInfo;
import com.widget.miaotu.common.utils.other.ShareUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.QiuGouHeadsjavaBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.head.HeadStatusBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;
import com.yanzhenjie.permission.AndPermission;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class QiuGouMiaoMuFragment extends BaseFragment {
    private final Activity userInfoNewActivity;
    private final String userId;

    @BindView(R.id.recyclerView_QiuGouMiaoMu)
    RecyclerView recyclerView;

    @BindView(R.id.srl_qiuQouMiaoMu)
    SmartRefreshLayout srl_qiuQouMiaoMu;


    private int mPage = 1;
    private int mPageNum = 10;
    private BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder> mAdapter;

    public QiuGouMiaoMuFragment(String userId, Activity userInfoNewActivity) {
        this.userId = userId;
        this.userInfoNewActivity = userInfoNewActivity;
    }

    @Override
    protected void initViewAndData(View view) {
        srl_qiuQouMiaoMu.setEnableLoadMore(true);
        srl_qiuQouMiaoMu.setEnableRefresh(true);
        srl_qiuQouMiaoMu.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDataList();
            }
        });
        srl_qiuQouMiaoMu.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getDataList();
            }
        });
        intAdapter();

        getDataList();


    }

    private void intAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder>(R.layout.item_fragment_qiugoumiaomu, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, QiuGouMiaoMuJavaBean miaoMuJavaBean) {

                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_1, miaoMuJavaBean.getSeedlingName());
                if (TextUtils.isEmpty(miaoMuJavaBean.getPlantType())) {
                    baseViewHolder.getView(R.id.st_qiuGouMiaoMu_2).setVisibility(View.GONE);
                } else {
                    baseViewHolder.setText(R.id.st_qiuGouMiaoMu_2, miaoMuJavaBean.getPlantType());
                }

                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_3, miaoMuJavaBean.getWantBuyNum() + "株");
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_4, miaoMuJavaBean.getCreateTime());

                Gson gson = new Gson();
                List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(miaoMuJavaBean.getSpec().replaceAll("SpecAllJavaBean", ""), new TypeToken<List<HomeMmSpecJavaBean>>() {
                }.getType());
                if (homeMmSpecJavaBeanList.size() > 1) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                            + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit());

                } else if (homeMmSpecJavaBeanList.size() == 1) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit());

                }

                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_6, "联系人：" + miaoMuJavaBean.getName());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_phone, " " + miaoMuJavaBean.getPhone());
                baseViewHolder.getView(R.id.tv_qiuGouMiaoMu_phone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callPhone() ;
                    }
                });
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_7, "用苗地: " + miaoMuJavaBean.getProvince() + " " + miaoMuJavaBean.getCity());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_8, "产地要求:" + miaoMuJavaBean.getFromProvince() + miaoMuJavaBean.getFromCity() + "货源");
                if (TextUtils.isEmpty(miaoMuJavaBean.getCompanyName())) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, "个人求购");
                } else {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, miaoMuJavaBean.getCompanyName() + " >");
                }

                if (miaoMuJavaBean.getStatus() == 2 || miaoMuJavaBean.getStatus() == 1) {//状态;0:求购中；1：结束求购；2：已删除'
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.VISIBLE);
                } else {
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.GONE);
                }

                if (miaoMuJavaBean.getIsVip() == 1) {
                    baseViewHolder.getView(R.id.iv_qgmu_wing).setVisibility(View.VISIBLE);
                }
                TextView textView = baseViewHolder.getView(R.id.tv_qiugou_delete_dialog);
                textView.setVisibility(View.VISIBLE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //status 状态；0：重新求购;1：结束求购；2：删除求购  	求购苗木id

                        new XPopup.Builder(getFragmentContext())
//                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                                .enableDrag(true)
                                .isThreeDrag(false) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                                .asCustom(new DeleteBottomPopup(getFragmentContext(), new DeleteBottomPopup.CallBackDelete() {
                                    @Override
                                    public void deleteSure() {
                                        showWaiteDialog("正在加载...");
                                        RetrofitFactory.getInstence().API().updateWantBuyStatus(new HeadStatusBean(miaoMuJavaBean.getWantBuyId() + "", "2")).compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(getFragmentContext()) {
                                            @Override
                                            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                                                Logger.e(t.toString());
                                                hideWaiteDialog();
                                                ToastUtils.showShort(t.getMessage());
                                                if (t.getStatus() == 100) {
                                                    mAdapter.remove(miaoMuJavaBean);
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            protected void onFail(Throwable throwable) throws Exception {
                                                Logger.e(throwable.getMessage());
                                                hideWaiteDialog();
                                                ToastUtils.showShort("请求出错");
                                            }
                                        });
                                    }
                                }))
                                .show();


                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
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

                    new XPopup.Builder(getActivity())
                            .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
                            .asConfirm("电话咨询", BaseConstants.CUSTOMER_SERVICE_PHONE,
                                    "取消", "拨打电话",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            IntentUtils.openCall(getFragmentContext(), BaseConstants.CUSTOMER_SERVICE_PHONE);
                                        }
                                    }, null, false)
                            .show();
                })
                .onDenied(permissions -> {

                    ToastUtils.showShort("需要电话权限");
                })
                .start();

    }
    private void getDataList() {

        RetrofitFactory.getInstence().API().getWantBuyList(new QiuGouHeadsjavaBean(0, mPage, mPageNum, "", userId, "", "", 0))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<QiuGouMiaoMuJavaBean>>(getFragmentContext()) {
            @Override
            protected void onSuccess(BaseEntity<List<QiuGouMiaoMuJavaBean>> t) throws Exception {

                if (t.getStatus() == 100) {
                    List<QiuGouMiaoMuJavaBean> qiuGouMiaoMuJavaBeanList = t.getData();
                    if (mPage == 1) {
                        mAdapter.setNewData(qiuGouMiaoMuJavaBeanList);
                        if (qiuGouMiaoMuJavaBeanList.size() < 10) {

                            srl_qiuQouMiaoMu.setNoMoreData(true);
                        }

                        if (qiuGouMiaoMuJavaBeanList.size() == 0) {
                            //数据为空

                            View emptyView = getLayoutInflater().inflate(R.layout.layout_empty, null);
                            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            //添加空视图
                            mAdapter.setEmptyView(emptyView);
                        }

                        return;
                    }

                    if (qiuGouMiaoMuJavaBeanList.size() == 0) {
                        srl_qiuQouMiaoMu.setNoMoreData(true);
                        return;
                    }


                    for (QiuGouMiaoMuJavaBean qiuGouMiaoMuJavaBean : qiuGouMiaoMuJavaBeanList) {
                        mAdapter.addData(qiuGouMiaoMuJavaBean);
                    }
                    srl_qiuQouMiaoMu.finishLoadMore(true);
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

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_qiugoumiaomu;
    }
}
