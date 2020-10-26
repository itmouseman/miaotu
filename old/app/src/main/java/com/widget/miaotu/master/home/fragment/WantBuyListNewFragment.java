package com.widget.miaotu.master.home.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;

import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.bean.WantBuySeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;
import com.widget.miaotu.master.home.adapter.UserWantBuyListNewAdapter;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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






    @Override
    public void onResume() {
        super.onResume();
//        page = 1;
//        mData.clear();
//        adapter.getData().clear();
//        getDiscoverList();
    }

    private void getDiscoverList() {

        JSONObject root = new JSONObject();
        Logger.e( "-----" + page + "---" + num);


        /**
         * status : 求购苗木的状态标识，0：查询求购中的；1：查询结束求购的;2:首页求购列表
         * page : 1
         * num : 10
         * searchWord : 首页搜索时需要传
         */


        try {
            root.put("status", status);
            root.put("searchWord", "");
            root.put("page", page);
            root.put("num", num);
            root.put("userId",userId);
//            root.put("province", num);
//            root.put("city", Integer.parseInt(mUserId));
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            RetrofitFactory.getInstence().API().getWantBuySeedList(requestBody)
                    .compose(TransformerUi.<BaseEntity<List<WantBuySeedListGetBean>>>setThread())
                    .subscribe(new BaseObserver<List<WantBuySeedListGetBean>>(BaseApplication.instance()) {
                        @Override
                        protected void onSuccess(BaseEntity<List<WantBuySeedListGetBean>> t) throws Exception {
                            adapter = new UserWantBuyListNewAdapter(getFragmentContext(),R.layout.item_user_want_buy_list_new, t.getData());
                            adapter.setOtherData(type);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(adapter);
//                            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                                @Override
//                                public void onLoadMoreRequested() {
//                                    page++;
//                                    getDiscoverList();
//                                }
//                            }, recyclerView);
//                            if (page == 1) {
//                                adapter.setNewData(data);
//                                if (data.size() < 10) {
//                                    adapter.loadMoreEnd();
//                                }
//                                return;
//                            }
//                            if (data.size() == 0) {
//                                adapter.loadMoreEnd();
//                                return;
//                            }
//                            for (WantBuySeedListGetBean partyListBean : data) {
//                                adapter.addData(partyListBean);
//                            }
//                            adapter.loadMoreComplete();

                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    @Override
    protected void initViewAndData(View view) {
        getDiscoverList();

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
