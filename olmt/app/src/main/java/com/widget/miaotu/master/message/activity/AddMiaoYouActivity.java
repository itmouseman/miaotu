package com.widget.miaotu.master.message.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.home.search.EditText_Clear;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HeadSearchInfoBean;
import com.widget.miaotu.http.bean.MayBeFriendsBean;
import com.widget.miaotu.http.bean.head.HeadUserIdBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.home.activity.HomeSearchActivity;
import com.widget.miaotu.master.message.adapter.AddMiaoYouAdapter;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 添加苗有Activity
 */
public class AddMiaoYouActivity extends MBaseActivity {

    @BindView(R.id.recyclerView_add_miaoyou)
    RecyclerView recyclerView_add_miaoyou;
    @BindView(R.id.editTextSearch_addMiaoyou)
    EditText_Clear editText_clear;
    @BindView(R.id.recyclerView_add_miaoyou_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.ll_add_miaoyou_order)
    LinearLayout ll_add_miaoyou_order;
    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.ll_mobile_contact)
    LinearLayout ll_mobile_contact;


    private AddMiaoYouAdapter mAdapter;
    private AddMiaoYouAdapter mAdapter2;
    private int mSearchPage = 1;
    private int mSearchPageNum = 20;


    @Override
    protected void initDetailData() {


        showWaiteDialog("数据正在加载中...");
        RetrofitFactory.getInstence().API().mayBeFriends().compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<MayBeFriendsBean>>(this) {
            @Override
            protected void onSuccess(BaseEntity<List<MayBeFriendsBean>> t) throws Exception {
                hideWaiteDialog();
                if (t.getStatus() == 100) {
                    mAdapter.setNewInstance(t.getData());

                    mAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                            Intent intent = new Intent();
                            intent.putExtra("userIdX", t.getData().get(position).getUserId());
                            intent.setClass(AddMiaoYouActivity.this, FriendsDetailsActivity.class);
                            startActivity(intent);
                        }
                    });
                    if (t.getData().size() == 0) {
                        View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
                        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        mAdapter.setEmptyView(emptyView);
                    }
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort("网络错误");
            }
        });

        //搜索
        editSearchChange();
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    private void editSearchChange() {
        editText_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ll_add_miaoyou_order.setVisibility(View.GONE);
                recyclerViewSearch.setVisibility(VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mAdapter2.notifyDataSetChanged();
            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {

                String tempName = editText_clear.getText().toString();
                if (!TextUtils.isEmpty(tempName)) {
                    searchFriend(tempName);
                } else {
                    ll_add_miaoyou_order.setVisibility(VISIBLE);
                    recyclerViewSearch.setVisibility(View.GONE);
                }

            }
        });
    }

    private void searchFriend(String tempName) {
        RetrofitFactory.getInstence().API().searchUserInfo(new HeadSearchInfoBean(tempName, mSearchPage, mSearchPageNum)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<List<MayBeFriendsBean>>(AddMiaoYouActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<List<MayBeFriendsBean>> t) throws Exception {
                        Logger.e(t.toString());
                        if (t.getStatus() == 100) {
                            mAdapter2.setNewInstance(t.getData());
                            if (t.getData().size() == 0) {
                                View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
                                emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                                mAdapter2.setEmptyView(emptyView);
                            }
                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                    }
                });


    }

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("新的苗友").setTextColor(Color.parseColor("#EBF9FF"));


        mAdapter = new AddMiaoYouAdapter(this);
        recyclerView_add_miaoyou.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_add_miaoyou.setAdapter(mAdapter);
        mAdapter.setCallBack(new AddMiaoYouAdapter.AddFirendCallBack() {
            @Override
            public void addFirend(String userId, int position) {
                //添加好友
                addMyFirend(userId, position, 1);
            }
        });


        mAdapter2 = new AddMiaoYouAdapter(AddMiaoYouActivity.this);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(AddMiaoYouActivity.this));
        recyclerViewSearch.setAdapter(mAdapter2);
        mAdapter2.setCallBack(new AddMiaoYouAdapter.AddFirendCallBack() {
            @Override
            public void addFirend(String userId, int position) {
                //添加好友
                addMyFirend(userId, position, 2);
            }
        });


        //手机联系人
        ll_mobile_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.startIntent(AddMiaoYouActivity.this, MobileContactListActivity.class);
            }
        });
    }

    /**
     * 添加好友
     *
     * @param userId
     */
    private void addMyFirend(String userId, int position, int type) {
        showWaiteDialog("正在发送...");
        RetrofitFactory.getInstence().API().addFriends(new HeadUserIdBean(userId)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<Object>(AddMiaoYouActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            ToastUtils.showShort("请求发送成功,待通过");
                            if (type == 1) {
                                mAdapter.removeAt(position);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter2.removeAt(position);
                                mAdapter2.notifyDataSetChanged();
                            }

                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                        Logger.i(throwable.getMessage());
                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_miaoyou;
    }
}
