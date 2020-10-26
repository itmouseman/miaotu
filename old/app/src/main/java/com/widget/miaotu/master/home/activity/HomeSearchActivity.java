package com.widget.miaotu.master.home.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.home.search.EditText_Clear;
import com.widget.miaotu.common.utils.home.search.RecordSQLiteOpenHelper;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;
import com.widget.miaotu.master.home.adapter.HomeSearchHistoryAdapter;
import com.widget.miaotu.master.mvp.HomeSearchControl;
import com.widget.miaotu.master.mvp.HomeSearchView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.BindView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 搜索界面
 */
public class HomeSearchActivity extends MBaseActivity<HomeSearchControl> implements HomeSearchView {


    @BindView(R.id.recyclerView_home_search1)
    RecyclerView recyclerViewHistory;
    @BindView(R.id.recyclerView_home_search2)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.editTextSearch)
    EditText_Clear searchView;

    @BindView(R.id.tv_home_searchHistory_del)
    TextView tvClearHistory;

    @BindView(R.id.ll_home_search_history)
    LinearLayout llRecyclerViewHistory;

    @BindView(R.id.ll_home_search_result)
    LinearLayout llRecyclerViewResult;

    @BindView(R.id.iv_search1_back)
    ImageView iv_search1_back;

    @BindView(R.id.srfl_search1)
    SmartRefreshLayout smartRefreshLayout;

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;


    private TreeSet<String> historyList = new TreeSet<>();
    private CommonAdapter<String> commonAdapter;
    private int pageNum = 1;
    private int pageSize = 10;
    private String afterTextChangedTempName = "";
    private HomeSearchHistoryAdapter historyAdapter;


    @Override
    protected HomeSearchControl createControl() {
        return new HomeSearchControl();
    }

    @Override
    protected void initView() {
        //使activity全屏
        QMUIStatusBarHelper.translucent(this);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableLoadMore(false);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mControl.getHostSearchData(afterTextChangedTempName, pageNum, pageSize);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                mControl.getHostSearchData(afterTextChangedTempName, pageNum, pageSize);
            }
        });
        //初始化搜索结果Adapter
        initSearchResultAdapter();

        initHistorySqlite();

        iv_search1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void initSearchResultAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);

        historyAdapter = new HomeSearchHistoryAdapter(new HomeSearchHistoryAdapter.CallbackJump() {
            @Override
            public void jumpSearchDetail(HomeSearchJavaBean homeSearchJavaBean) {
                //跳转到搜索详情页面
                String[] key = {SPConstant.SEARCH_INFO};
                String[] value = {homeSearchJavaBean.getName()};
                IntentUtils.startIntent(HomeSearchActivity.this, HomeSearchDetailActivity.class, key, value);


                boolean hasData = hasData(homeSearchJavaBean.getName());

                if (!hasData) {
                    insertData(homeSearchJavaBean.getName());

                }
            }
        });


        recyclerViewSearch.setAdapter(historyAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 初始化数据库
     */
    private void initHistorySqlite() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);
        // 2. 实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(this);

        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");


        /**
         * "清空搜索历史"按钮
         */
        tvClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();

                historyList.clear();

                queryData("");

            }
        });

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        searchView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    boolean hasData = hasData(searchView.getText().toString().trim());
                    if (!hasData) {
                        insertData(searchView.getText().toString().trim());
                        queryData("");
                    }

                    //跳转到搜索详情
                    String[] key = {SPConstant.SEARCH_INFO};
                    String[] value = {searchView.getText().toString().trim()};
                    IntentUtils.startIntent(HomeSearchActivity.this, HomeSearchDetailActivity.class, key, value);


                }
                return false;
            }
        });


        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                // 获取用户点击列表里的文字,并自动填充到搜索框内
//                TextView textView = (TextView) view.findViewById(android.R.id.text1);
//                String name = textView.getText().toString();
//                searchView.setText(name);
//                Toast.makeText(HomeSearchActivity.this, name, Toast.LENGTH_SHORT).show();
//            }
//        });


        /**
         * 点击返回按键后的事件
         */
//        searchBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 注：由于返回需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
//
//                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
//                Toast.makeText(HomeSearchActivity.this, "返回到上一页", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    /**
     * 关注2：清空数据库
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        //清除历史数据的按钮隐藏
        tvClearHistory.setVisibility(INVISIBLE);
    }


    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }


    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }


    /**
     * 关注1
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {

        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String his = cursor.getString(1);

                historyList.add(his);
            } while (cursor.moveToNext());
        }


        if (cursor != null) {
            while (cursor.moveToNext()) {
                String his = cursor.getString(1);

            }
        }

        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        List<String> list = new ArrayList<String>(historyList);
        commonAdapter = new CommonAdapter<String>(this, R.layout.home_search_history_item, list) {
            @Override
            protected void convert(ViewHolder holder, String searchName, int position) {
                holder.setText(R.id.tv_search_history_item1, searchName);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                        boolean hasData = hasData(searchName);
                        // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                        if (!hasData) {
                            insertData(searchName);
//                            queryData("");
                        }

                        //跳转到搜索详情页面
                        String[] key = {SPConstant.SEARCH_INFO};
                        String[] value = {searchName};
                        IntentUtils.startIntent(HomeSearchActivity.this, HomeSearchDetailActivity.class, key, value);


                    }
                });
            }
        };

        recyclerViewHistory.setAdapter(commonAdapter);

        commonAdapter.notifyDataSetChanged();

        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0) {
            tvClearHistory.setVisibility(VISIBLE);
        } else {
            tvClearHistory.setVisibility(INVISIBLE);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initDetailData() {

        /**
         * 搜索框的文本变化实时监听
         */
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                llRecyclerViewHistory.setVisibility(View.GONE);
                llRecyclerViewResult.setVisibility(VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //清除历史数据
                historyAdapter.clearData();
                historyAdapter.notifyDataSetChanged();
            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                afterTextChangedTempName = searchView.getText().toString();
                smartRefreshLayout.setEnableRefresh(true);
                smartRefreshLayout.setEnableLoadMore(true);



                mControl.getHostSearchData(afterTextChangedTempName, pageNum, pageSize);

                // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
                if (afterTextChangedTempName.equals("")) {
                    tvClearHistory.setVisibility(VISIBLE);
                    llRecyclerViewHistory.setVisibility(VISIBLE);
                    llRecyclerViewResult.setVisibility(View.GONE);
                } else {
                    tvClearHistory.setVisibility(INVISIBLE);
                    llRecyclerViewHistory.setVisibility(View.GONE);
                    llRecyclerViewResult.setVisibility(VISIBLE);
                }
            }
        });


    }

    @Override
    public void getHostSearchDataSuc(List<HomeSearchJavaBean> homeSearchJavaBean) {

        smartRefreshLayout.finishRefresh(true);
        smartRefreshLayout.finishLoadMore(true);

        if (pageNum == 1) {
            historyAdapter.addData(homeSearchJavaBean);

            if (homeSearchJavaBean.size() < 10) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                smartRefreshLayout.setNoMoreData(false);
            }
            return;
        }

        if (homeSearchJavaBean.size() == 0) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
            smartRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
            return;
        }

        if (homeSearchJavaBean.size() > 0) {

            historyAdapter.addData(homeSearchJavaBean);

        }
    }

    @Override
    public void getHostSearchDataFail(String message) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
    }

}