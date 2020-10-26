package com.widget.miaotu.master.home.fragment;

import android.annotation.SuppressLint;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;

import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.SpUtil;
import com.widget.miaotu.common.utils.rxbus.MyTouchEvent;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.CompanyInfoMeBean;
import com.widget.miaotu.http.bean.GardenListBean;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 苗木
 *
 * @author tzy
 */

public class AddTreeFragment extends BaseFragment {

    @BindView(R.id.rv_AddTreeF)
    RecyclerView recyclerView;


    private NurseryTreeListAdapter adapter;

    private AppCompatActivity appCompatActivity;

    private int page = 1;

    private int num = 10;
    private int gardenId = 0;

    private List<SeedListGetBean> mData = new ArrayList<>();

    public List<GardenListBean> gardenData = new ArrayList<>();

    private List<NurseryNameBean> data = new ArrayList<>();
    private List<NurseryNameBean> datap = new ArrayList<>();

    String type;
    String userId;


    public AddTreeFragment(String userId, AppCompatActivity appCompatActivity) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
    }

    public AddTreeFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }


    @Override
    protected void initViewAndData(View view) {


        getDiscoverList(gardenId, page, num, userId);


//        smartRefreshLayout.setEnableRefresh(false);
//        smartRefreshLayout.setEnableLoadMore(true);
//        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                page++;
//                getDiscoverList(gardenId, page, num, userId);
//                refreshLayout.finishLoadMore(1000);
//            }
//        });
        RxBus.getInstance().toObservableSticky(this, MyTouchEvent.class).subscribe(new Consumer<MyTouchEvent>() {
            @Override
            public void accept(MyTouchEvent myTouchEvent) throws Exception {

                Logger.e("是否刷新数据" + myTouchEvent.isNeedTouch());
            }
        });
    }


    private void getDiscoverList(int gardenId, int page, int num, String mUserId) {

        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(page,num,mUserId))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {

                        Logger.e(t.getData().toString());

                        adapter = new NurseryTreeListAdapter(getFragmentContext(), R.layout.item_nursery_tree_list, t.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);
                        recyclerView.setNestedScrollingEnabled(false);

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());

                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_company_detail1;
    }
}
