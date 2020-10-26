package com.widget.miaotu.master.home.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.rxbus.MyTouchEvent;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.QiuGouHeadsjavaBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.bean.WantBuySeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.UserWantBuyListNewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author tzy
 */

public class WantBuyListNewFragment extends BaseFragment {

    String userId;
    String type;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private UserWantBuyListNewAdapter adapter;

    private AppCompatActivity appCompatActivity;

    private int page = 1;

    private int num = 10;

    private List<WantBuySeedListGetBean> mData = new ArrayList<>();

    String status;
    private BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder> mAdapter;
    private SmartRefreshLayout mSrlUserInfo;

    public WantBuyListNewFragment(String userId, String type, AppCompatActivity appCompatActivity) {
        this.userId = userId;
        this.type = type;
        if("1".equals(type)){
            status="0";
//            status="2";
        }else{
            status="1";
        }
        this.appCompatActivity = appCompatActivity;
    }









    private void getAskToBuyLobbyList() {

        RetrofitFactory.getInstence().API().getWantBuyList(new QiuGouHeadsjavaBean(Integer.parseInt(status), page, num, "", userId, "", "", 0))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<QiuGouMiaoMuJavaBean>>(getFragmentContext()) {
            @Override
            protected void onSuccess(BaseEntity<List<QiuGouMiaoMuJavaBean>> t) throws Exception {

                if (mSrlUserInfo!=null){
                    mSrlUserInfo.finishRefresh(true);
                    mSrlUserInfo.finishLoadMore(true);
                }
                if (t.getStatus() == 100) {
                    List<QiuGouMiaoMuJavaBean> qiuGouMiaoMuJavaBeanList = t.getData();
                    if (page == 1) {
                        mAdapter.setNewData(qiuGouMiaoMuJavaBeanList);
                        if (qiuGouMiaoMuJavaBeanList.size() < 10) {
                            if (mSrlUserInfo!=null){
                                mSrlUserInfo.setNoMoreData(true);
                            }
                        }
                        if (qiuGouMiaoMuJavaBeanList.size() == 0) {
                            //空太显示


                            View emptyView=getLayoutInflater().inflate(R.layout.layout_empty, null);
                            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            //添加空视图
                            mAdapter.setEmptyView(emptyView);
//                            ToastUtils.showShort("数据为空");
                        }
                        return;
                    }

                    if (qiuGouMiaoMuJavaBeanList.size() == 0) {

                        if (mSrlUserInfo!=null){
                            mSrlUserInfo.setNoMoreData(true);
                        }
                        return;
                    }

                    for (QiuGouMiaoMuJavaBean qiuGouMiaoMuJavaBean : qiuGouMiaoMuJavaBeanList) {
                        mAdapter.addData(qiuGouMiaoMuJavaBean);
                    }
                    if (mSrlUserInfo!=null){
                        mSrlUserInfo.finishLoadMore(true);
                    }


                } else {
                    ToastUtils.showShort(t.getMessage());
                }

            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                if (mSrlUserInfo!=null){
                    mSrlUserInfo.finishRefresh(true);
                    mSrlUserInfo.finishLoadMore(true);
                }

                ToastUtils.showShort("请求异常");
            }
        });
    }
    @Override
    protected void initViewAndData(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getFragmentContext()));
        mAdapter = new BaseQuickAdapter<QiuGouMiaoMuJavaBean, BaseViewHolder>(R.layout.item_fragment_qiugoumiaomu, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, QiuGouMiaoMuJavaBean qiuGouMiaoMuJavaBean) {
                Logger.e(qiuGouMiaoMuJavaBean.toString());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_1, qiuGouMiaoMuJavaBean.getSeedlingName());
                if (TextUtils.isEmpty(qiuGouMiaoMuJavaBean.getPlantType())) {
                    baseViewHolder.getView(R.id.st_qiuGouMiaoMu_2).setVisibility(View.GONE);
                } else {
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
                if (qiuGouMiaoMuJavaBean.getStatus() == 2 || qiuGouMiaoMuJavaBean.getStatus() == 1) {//状态;0:求购中；1：结束求购；2：已删除'
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.VISIBLE);
                } else {
                    baseViewHolder.getView(R.id.iv_qiugou_miaomu).setVisibility(View.GONE);
                }
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_6, "联系人：" + qiuGouMiaoMuJavaBean.getName());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_phone, " " + qiuGouMiaoMuJavaBean.getPhone());
                baseViewHolder.getView(R.id.tv_qiuGouMiaoMu_phone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("打电话");
                    }
                });
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_7, "用苗地: " + qiuGouMiaoMuJavaBean.getProvince() + " " + qiuGouMiaoMuJavaBean.getCity());
                baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_8, "产地要求:" + qiuGouMiaoMuJavaBean.getFromProvince() + qiuGouMiaoMuJavaBean.getFromCity() + "货源");
                if (TextUtils.isEmpty(qiuGouMiaoMuJavaBean.getCompanyName())) {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, "个人求购");
                } else {
                    baseViewHolder.setText(R.id.tv_qiuGouMiaoMu_9, qiuGouMiaoMuJavaBean.getCompanyName() + " >");
                }

                if (qiuGouMiaoMuJavaBean.getIsVip() == 1) {
                    baseViewHolder.getView(R.id.iv_qgmu_wing).setVisibility(View.VISIBLE);
                }



            }
        };
        recyclerView.setAdapter(mAdapter);


        getAskToBuyLobbyList();

        RxBus.getInstance().toObservableSticky(this, MyTouchEvent.class).subscribe(new Consumer<MyTouchEvent>() {
            @Override
            public void accept(MyTouchEvent myTouchEvent) throws Exception {
                if (myTouchEvent.getTabPosition() == 2) {
                  mSrlUserInfo  =   myTouchEvent.getmSrlUserInfo();
                    if (myTouchEvent.getmTypeLoad() == 1) {//刷新界面

                        getAskToBuyLobbyList();
                    } else {//加载更多
                        page++;
                        getAskToBuyLobbyList();
                    }
                }

            }
        });

    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_want_buy_new;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
