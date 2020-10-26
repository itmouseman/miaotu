package com.widget.miaotu.master.message.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.home.search.EditText_Clear;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HeadSearchInfoBean;
import com.widget.miaotu.http.bean.MayBeFriendsBean;
import com.widget.miaotu.http.bean.head.HeadUserIdBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
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
public class AddMiaoYouActivity extends BaseActivity {

    @BindView(R.id.recyclerView_add_miaoyou)
    RecyclerView recyclerView_add_miaoyou;
    @BindView(R.id.editTextSearch_addMiaoyou)
    EditText_Clear editText_clear;
    @BindView(R.id.recyclerView_add_miaoyou_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.ll_add_miaoyou_order)
    LinearLayout ll_add_miaoyou_order;

    private AddMiaoYouAdapter mAdapter;
    private AddMiaoYouAdapter mAdapter2;

    @Override
    protected void initData() {

        RetrofitFactory.getInstence().API().mayBeFriends().compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<MayBeFriendsBean>>(this) {
            @Override
            protected void onSuccess(BaseEntity<List<MayBeFriendsBean>> t) throws Exception {
                Logger.e(t.toString());
                if (t.getStatus() == 100) {

                    mAdapter.addData(t.getData());
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        });

        //搜索
        editSearchChange();

    }

    /**
     * 文本狂改变
     */
    private void editSearchChange() {


        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
//        editText_clear.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    // 1. 点击搜索按键后，根据输入的搜索字段进行查询
//                    String mSearchResult = editText_clear.getText().toString().trim();
//
//                Logger.e(mSearchResult);
//                }
//                return false;
//            }
//        });

        /**
         * 搜索框的文本变化实时监听
         */
        editText_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ll_add_miaoyou_order.setVisibility(View.GONE);
                recyclerViewSearch.setVisibility(VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = editText_clear.getText().toString();


                searchFriend(tempName);
            }
        });
    }

    private void searchFriend(String tempName) {
        if (!TextUtils.isEmpty(tempName)) {
            RetrofitFactory.getInstence().API().searchUserInfo(new HeadSearchInfoBean(tempName, "1", "10")).compose(TransformerUi.setThread())
                    .subscribe(new BaseObserver<List<MayBeFriendsBean>>(AddMiaoYouActivity.this) {
                        @Override
                        protected void onSuccess(BaseEntity<List<MayBeFriendsBean>> t) throws Exception {
                            Logger.e(t.toString());
                            if (t.getStatus() == 100) {
                                mAdapter2.addData(t.getData());

                            } else {
                                ToastUtils.showShort(t.getMessage());
                            }
                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());
                        }
                    });
        } else {
            ll_add_miaoyou_order.setVisibility(VISIBLE);
            recyclerViewSearch.setVisibility(View.GONE);
        }


    }

    @Override
    protected void initView() {

        mAdapter = new AddMiaoYouAdapter(this);
        recyclerView_add_miaoyou.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_add_miaoyou.setAdapter(mAdapter);
        mAdapter.setCallBack(new AddMiaoYouAdapter.AddFirendCallBack() {
            @Override
            public void addFirend(String userId) {
                //添加好友
                addMyFirend(userId);
            }
        });


        mAdapter2 = new AddMiaoYouAdapter(AddMiaoYouActivity.this);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(AddMiaoYouActivity.this));
        recyclerViewSearch.setAdapter(mAdapter2);
        mAdapter2.setCallBack(new AddMiaoYouAdapter.AddFirendCallBack() {
            @Override
            public void addFirend(String userId) {
                //添加好友
                addMyFirend(userId);
            }
        });
    }

    /**
     * 添加好友
     *
     * @param userId
     */
    private void addMyFirend(String userId) {
        RetrofitFactory.getInstence().API().addFriends(new HeadUserIdBean(userId)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<Object>(AddMiaoYouActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {

                        Logger.e(t.toString());
                        if (t.getStatus() == 100) {
                            ToastUtils.showShort("添加成功");
                            mAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.btn_add_my_back})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_my_back:
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_miaoyou;
    }
}
