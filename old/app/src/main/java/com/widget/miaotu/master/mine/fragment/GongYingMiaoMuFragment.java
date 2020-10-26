package com.widget.miaotu.master.mine.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的页面进入 ->  供应苗木
 */
public class GongYingMiaoMuFragment extends BaseFragment {


    @BindView(R.id.recyclerView_GongYingMm_Fg)
    RecyclerView recyclerView;
    @BindView(R.id.ll_mmgy_empty)
    LinearLayout ll_mmgy_empty;


    private final AppCompatActivity appCompatActivity;
    String type;
    String userId;
    private NurseryTreeListAdapter adapter;


    public GongYingMiaoMuFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }


    @Override
    protected void initViewAndData(View view) {
        getGardenList(1, 10);
    }


    private void getGardenList(int page, int num) {

        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(page,num))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {

                        Logger.e(t.getData().toString());
                        if (t.getData().size() > 0) {
                            ll_mmgy_empty.setVisibility(View.GONE);
                            adapter = new NurseryTreeListAdapter(getFragmentContext(), R.layout.item_nursery_tree_list, t.getData());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);
                        } else {//数据为空
                            ll_mmgy_empty.setVisibility(View.VISIBLE);
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
        return R.layout.fragment_gongying_miaomu;
    }
}
