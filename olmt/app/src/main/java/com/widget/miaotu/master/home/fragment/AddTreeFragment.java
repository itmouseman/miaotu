package com.widget.miaotu.master.home.fragment;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.rxbus.MyTouchEvent;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HeadSeedlingListBean;
import com.widget.miaotu.http.bean.SeedListGetBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.adapter.NurseryTreeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


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


    String type;
    String userId;
    private SmartRefreshLayout mSrlUserInfo;


    public AddTreeFragment(String userId, AppCompatActivity appCompatActivity, String type) {
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
    }


    @Override
    protected void initViewAndData(View view) {

        adapter = new NurseryTreeListAdapter(getFragmentContext(), R.layout.item_nursery_tree_list, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        getDiscoverList(gardenId, page, num, userId);

        RxBus.getInstance().toObservableSticky(this, MyTouchEvent.class).subscribe(new Consumer<MyTouchEvent>() {
            @Override
            public void accept(MyTouchEvent myTouchEvent) throws Exception {
                if (myTouchEvent.getTabPosition() == 1) {
                    mSrlUserInfo  =   myTouchEvent.getmSrlUserInfo();
                    if (myTouchEvent.getmTypeLoad() == 1) {//刷新界面

                        getDiscoverList(gardenId, page, num, userId);
                    } else {//加载更多
                        page++;
                        getDiscoverList(gardenId, page, num, userId);
                    }
                }

            }
        });
    }


    private void getDiscoverList(int gardenId, int page, int num, String mUserId) {
        showWaiteDialog("正在加载中...");
        RetrofitFactory.getInstence().API().getSeedlingList(new HeadSeedlingListBean(page, num, mUserId))
                .compose(TransformerUi.<BaseEntity<List<SeedListGetBean>>>setThread())
                .subscribe(new BaseObserver<List<SeedListGetBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<SeedListGetBean>> t) throws Exception {
                        hideWaiteDialog();
                        Logger.e(t.getData().toString());
                        if (mSrlUserInfo!=null){
                            mSrlUserInfo.finishRefresh(true);
                            mSrlUserInfo.finishLoadMore(true);
                        }

                        if (t.getStatus() == 100) {
                            if (page == 1) {
                                adapter.setNewData(t.getData());
                                if (t.getData().size()<10){
                                    if (mSrlUserInfo!=null){
                                        mSrlUserInfo.setNoMoreData(true);
                                    }
                                }
                                if (t.getData().size() == 0) {
                                    //空太显示
                                    View emptyView=getLayoutInflater().inflate(R.layout.layout_empty, null);
                                    emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
                                    //添加空视图
                                    adapter.setEmptyView(emptyView);
//                                    ToastUtils.showShort("数据为空");
                                }

                                return;
                            }

                            if (t.getData().size()== 0) {
                                if (mSrlUserInfo!=null){
                                    mSrlUserInfo.setNoMoreData(true);
                                }
                                return;
                            }

                            for (SeedListGetBean seedListGetBean : t.getData()) {
                                adapter.addData(seedListGetBean);
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
                        Logger.e(throwable.getMessage());
                        hideWaiteDialog();
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
