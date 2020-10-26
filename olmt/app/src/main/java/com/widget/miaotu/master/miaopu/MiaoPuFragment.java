package com.widget.miaotu.master.miaopu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.dialog.AddTreeDialog;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.MyTouchEvent;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.home.activity.BaseInfoDemandTechAddActivity;
import com.widget.miaotu.master.miaopu.activity.ManagerNurseryActivity;
import com.widget.miaotu.master.other.login.LoginCodeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 苗圃
 */
public class MiaoPuFragment extends BaseFragment {

    @BindView(R.id.rel_11)
    RelativeLayout rel11;
    @BindView(R.id.smartRfL_nursery)
    SmartRefreshLayout smartRfL_nursery;
    @BindView(R.id.empty_nursery)
    LinearLayout empty_nursery;
    @BindView(R.id.stv_add_nursery_2)
    ShapeTextView tv_add_nursery_2;

    List mdata = new ArrayList<NurseryNameBean>();

    @Override
    protected void initViewAndData(View view) {


        //判断苗圃是否存在
        nurseryExist();

        RxBus.getInstance().toObservableSticky(this, HomeUpdateChange.class).subscribe(new Consumer<HomeUpdateChange>() {
            @Override
            public void accept(HomeUpdateChange homeUpdateChange) throws Exception {
                nurseryExist();
            }
        });
    }




    /**
     * 判断苗圃是否存在
     */
    private void nurseryExist() {


        if (!isLogin()) {


            IntentUtils.startIntent(getActivity(), LoginCodeActivity.class);
        } else {
            //判断是否有苗圃 分别进入苗圃页面或添加苗木页面
            showWaiteDialog("正在加载...");

            RetrofitFactory.getInstence().API().gardenList().compose(TransformerUi.setThread())
                    .subscribe(new BaseObserver<List<GardenListBean>>(getFragmentContext()) {
                        @Override
                        protected void onSuccess(BaseEntity<List<GardenListBean>> t) throws Exception {
                            hideWaiteDialog();
                            if (t.getStatus() == 100) {
                                mdata.clear();
                                List<GardenListBean> gardenListBeans = t.getData();
                                if (gardenListBeans != null) {
                                    for (GardenListBean graden : gardenListBeans) {
                                        mdata.add(new NurseryNameBean(graden.getGardenName(), graden.getId() + "", 0));
                                    }
                                    if (mdata.size() == 0) {//没有可添加苗木的苗圃，请先添加苗圃
                                        empty_nursery.setVisibility(View.VISIBLE);
                                        smartRfL_nursery.setVisibility(View.GONE);
                                        tv_add_nursery_2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getFragmentContext(), BaseInfoDemandTechAddActivity.class));
                                            }
                                        });

                                        return;
                                    }

                                    if (mdata.size() == 1) {
                                        empty_nursery.setVisibility(View.GONE);
                                        smartRfL_nursery.setVisibility(View.VISIBLE);

                                        return;
                                    }

                                }
                            }
                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            hideWaiteDialog();
                        }
                    });


        }


    }

    @OnClick({R.id.rel_21, R.id.rel_22, R.id.rel_11, R.id.rel_12, R.id.rel_13, R.id.rel_23, R.id.rel_31, R.id.rel_32,
            R.id.rel_33, R.id.rel_41, R.id.rel_42, R.id.rel_43, R.id.rel_44, R.id.rel_51, R.id.rel_52, R.id.rel_53})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_21://苗木登记
                miaomuJumpType();
                break;
            case R.id.rel_22://苗木管理
                startActivity(new Intent(getContext(), ManagerNurseryActivity.class));
                break;
            case R.id.rel_11://最近浏览
            case R.id.rel_12://参与报价
            case R.id.rel_13://来电记录
            case R.id.rel_23://预览企业
            case R.id.rel_31://营销管理
            case R.id.rel_32://一键更新
            case R.id.rel_33://生成海报
            case R.id.rel_41://企业设置
            case R.id.rel_42://苗圃设置
            case R.id.rel_43://子账号
            case R.id.rel_44://收到评价
            case R.id.rel_51://企业二维码
            case R.id.rel_52://分享企业
            case R.id.rel_53://库存表
                ToastUtils.showShort("暂未开放");

                break;
        }
    }


    /**
     * 苗木登记判断 有苗圃的话直接进入苗木登记 没有的话去添加苗圃
     */
    private void miaomuJumpType() {
        //判断是否有苗圃 分别进入苗圃页面或添加苗木页面
        showWaiteDialog("正在加载...");

        RetrofitFactory.getInstence().API().gardenList().compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<GardenListBean>>(getFragmentContext()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<GardenListBean>> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            mdata.clear();
                            List<GardenListBean> gardenListBeans = t.getData();
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
                                    intent.putExtra("gardenId", String.valueOf(gardenListBeans.get(0).getId()));
                                    intent.setClass(getFragmentContext(), AddMiaoMuActivity.class);
                                    startActivity(intent);

                                    return;
                                }
                                new AddTreeDialog(getContext(), mdata).show();
                            }
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                    }
                });


    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_miao_pu;
    }
}