package com.widget.miaotu.master.mine.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeMmSpecJavaBean;
import com.widget.miaotu.http.bean.QiuGouHeadsjavaBean;
import com.widget.miaotu.http.bean.QiuGouMiaoMuJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mine.activity.UserInfoNewActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;

public class QiuGouMiaoMuFragment extends BaseFragment {
    private final Activity userInfoNewActivity;
    private final String userId;

    @BindView(R.id.recyclerView_QiuGouMiaoMu)
    RecyclerView recyclerView;
    @BindView(R.id.ll_mmqg_empty)
    LinearLayout ll_mmqg_empty;

    public QiuGouMiaoMuFragment(String userId, Activity userInfoNewActivity) {
        this.userId = userId;
        this.userInfoNewActivity = userInfoNewActivity;
    }

    @Override
    protected void initViewAndData(View view) {

        RetrofitFactory.getInstence().API().getWantBuyList(new QiuGouHeadsjavaBean(0, 1, 10, "", userId, "", "", 0))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<QiuGouMiaoMuJavaBean>>(getFragmentContext()) {
            @Override
            protected void onSuccess(BaseEntity<List<QiuGouMiaoMuJavaBean>> t) throws Exception {
                Logger.e(t.toString());
                List<QiuGouMiaoMuJavaBean> qiuGouMiaoMuJavaBeanList = t.getData();

                if (qiuGouMiaoMuJavaBeanList.size() > 0) {
                    ll_mmqg_empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new CommonAdapter<QiuGouMiaoMuJavaBean>(getFragmentContext(), R.layout.item_fragment_qiugoumiaomu, qiuGouMiaoMuJavaBeanList) {
                        @Override
                        protected void convert(ViewHolder holder, QiuGouMiaoMuJavaBean miaoMuJavaBean, int position) {

                            holder.setText(R.id.tv_qiuGouMiaoMu_1, miaoMuJavaBean.getSeedlingName());
                            holder.setText(R.id.st_qiuGouMiaoMu_2, miaoMuJavaBean.getPlantType());
                            holder.setText(R.id.tv_qiuGouMiaoMu_3, miaoMuJavaBean.getWantBuyNum() + "株");
                            holder.setText(R.id.tv_qiuGouMiaoMu_4, miaoMuJavaBean.getCreateTime());

                            Logger.e(miaoMuJavaBean.getSpec().toString());
                            Gson gson = new Gson();
                            String m = miaoMuJavaBean.getSpec().toString().replace("\'", "\"");
                            List<HomeMmSpecJavaBean> homeMmSpecJavaBeanList = gson.fromJson(m, new TypeToken<List<HomeMmSpecJavaBean>>() {
                            }.getType());
                            if (homeMmSpecJavaBeanList.size() > 1) {
                                holder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit()
                                        + " " + homeMmSpecJavaBeanList.get(1).getSpecName() + " " + homeMmSpecJavaBeanList.get(1).getInterval() + homeMmSpecJavaBeanList.get(1).getUnit());

                            } else if (homeMmSpecJavaBeanList.size() == 1) {
                                holder.setText(R.id.tv_qiuGouMiaoMu_5, "采购规格: " + homeMmSpecJavaBeanList.get(0).getSpecName() + " " + homeMmSpecJavaBeanList.get(0).getInterval() + homeMmSpecJavaBeanList.get(0).getUnit());

                            }

                            holder.setText(R.id.tv_qiuGouMiaoMu_6, "联系人：" + miaoMuJavaBean.getCompanyName());
                            holder.setText(R.id.tv_qiuGouMiaoMu_7, "用苗地: ");
                            holder.setText(R.id.tv_qiuGouMiaoMu_8, "产地要求:");
                            holder.setText(R.id.tv_qiuGouMiaoMu_9, miaoMuJavaBean.getCompanyName());
                            holder.getView(R.id.iv_qgmu_wing);
//

                        }
                    });
                } else {
                    recyclerView.setVisibility(View.GONE);
                    ll_mmqg_empty.setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                Logger.e(throwable.getMessage());
            }
        });
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_qiugoumiaomu;
    }
}
