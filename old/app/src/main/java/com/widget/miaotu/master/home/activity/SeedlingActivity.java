package com.widget.miaotu.master.home.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.greedao.SeedlingInfoDao;
import com.widget.miaotu.common.utils.other.LetterBar;
import com.widget.miaotu.common.utils.ui.StickHeaderDecoration;
import com.widget.miaotu.http.bean.SeedlingInfo;
import com.widget.miaotu.master.home.adapter.RecyclerViewAdapter;
import com.widget.miaotu.master.home.adapter.SeedlingAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SeedlingActivity extends MBaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private LetterBar letter_bar;
    @BindView(R.id.rl_seedling_two)
    RelativeLayout rl_seedling_two;
    @BindView(R.id.rl_seedling)
    RelativeLayout rl_seedling;
    @BindView(R.id.rv_list_two)
    RecyclerView rv_list_two;
    @BindView(R.id.editText13)
    EditText editText13;
    @BindView(R.id.imageView60)
    ImageView imageView60;

    private SeedlingAdapter adapter;

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {

        mRecyclerView = findViewById(R.id.rv_list);

        final List<SeedlingInfo> beanList = BaseApplication.getmDaoSession().loadAll(SeedlingInfo.class);
//        final List<Bean> beanList = getInfo(this);


        mAdapter = new RecyclerViewAdapter(this, beanList);
        mRecyclerView.addItemDecoration(new StickHeaderDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(SeedlingInfo bean) {
                Intent intent = new Intent();
                intent.putExtra("info", (new Gson()).toJson(bean));
                //回传结果码，我这边也是给1，大于等于0即可，值随意
                setResult(4, intent);
                //setResult之后必须调用finish方法
                finish();
            }
        });
        imageView60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        letter_bar = findViewById(R.id.letter_bar);

        //自定义字母
        letter_bar.setLetters(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "H", "J", "K", "L", "M"
                , "N", "O", "P", "Q", "R", "S", "T", "X", "Y", "Z"));

        //触摸后的监听回调
        letter_bar.setOnLetterChangeListener(new LetterBar.OnLetterChangeListner() {
            @Override
            public void onLetterChanged(String letter) {
                Log.d("SLL-onLetterChanged:", letter);
                move(getItemPosition(beanList, letter));
            }

            @Override
            public void onLetterChoosed(String letter) {
                Log.d("SLL-onLetterChoosed:", letter);
                move(getItemPosition(beanList, letter));
            }
        });

        adapter = new SeedlingAdapter(new ArrayList<SeedlingInfo>());
        rv_list_two.addItemDecoration(new StickHeaderDecoration(this));
        rv_list_two.setLayoutManager(new LinearLayoutManager(this));
        rv_list_two.setAdapter(adapter);
        adapter.setOnCheckListener(new SeedlingAdapter.OnCheckListener() {
            @Override
            public void Click(SeedlingInfo info) {
                Intent intent = new Intent();
                intent.putExtra("info", (new Gson()).toJson(info));
                //回传结果码，我这边也是给1，大于等于0即可，值随意
                setResult(4, intent);
                //setResult之后必须调用finish方法
                finish();
            }
        });

        editText13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    rl_seedling.setVisibility(View.VISIBLE);
                    rl_seedling_two.setVisibility(View.GONE);
                } else {
                    rl_seedling.setVisibility(View.GONE);
                    rl_seedling_two.setVisibility(View.VISIBLE);
                    adapter.setNewData(BaseApplication.getmDaoSession().queryBuilder(SeedlingInfo.class)
                            .where(SeedlingInfoDao.Properties.BaseName.like("%" + s + "%")).list());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void move(int position) {
        if (position != -1) {
            mRecyclerView.scrollToPosition(position);
            LinearLayoutManager mLayoutManager =
                    (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mLayoutManager.scrollToPositionWithOffset(position, 0);
        }
    }


    private int getItemPosition(List<SeedlingInfo> beans, String zm) {
        for (int i = 0; i < beans.size(); i++) {
            if (zm.equals(beans.get(i).getBeginLetter())) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seedling_list;
    }

    @Override
    protected void initDetailData() {

    }
}
