package com.widget.miaotu.master.miaopu;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
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
    RelativeLayout empty_nursery;
    @BindView(R.id.tv_add_nursery_2)
    TextView tv_add_nursery_2;

    List mdata = new ArrayList<NurseryNameBean>();

    @Override
    protected void initViewAndData(View view) {

//        StatusBarUtil.setStatusBarColor(getActivity(), getResources().getColor(R.color.colorAccent));

        //判断苗圃是否存在
        nurseryExist();
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

                        }
                    });


        }


    }

    @OnClick({R.id.rel_21, R.id.rel_22})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_21://苗木登记
                miaomuJumpType();
                break;
            case R.id.rel_22://苗木管理
                startActivity(new Intent(getContext(), ManagerNurseryActivity.class));
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
                                    intent.putExtra("id", String.valueOf(gardenListBeans.get(0).getId()));
                                    intent.setClass(getFragmentContext(), AddMiaoMuActivity.class);
                                    startActivity(intent);

                                    return;
                                }

                            }
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {

                    }
                });


    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_miao_pu;
    }
}