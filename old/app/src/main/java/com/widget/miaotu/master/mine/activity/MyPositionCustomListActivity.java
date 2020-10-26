package com.widget.miaotu.master.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.master.mine.adapter.MyPositionCustomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyPositionCustomListActivity extends BaseActivity {

    @BindView(R.id.rv_position_list)
    RecyclerView rvPositionList;
    @BindView(R.id.et_input_customname)
    EditText etInputCustomname;
    @BindView(R.id.iv_position_clear)
    ImageView ivPositionClear;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btn_right;


    private MyPositionCustomAdapter adapter;

    private List<String> data = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("自定义职位");
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存");
        btn_right.setTextColor(Color.parseColor("#03DAC5"));

        //加载数据
        adapter = new MyPositionCustomAdapter(this, data);

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 提交实名认证信息
                if(AndroidUtils.isNullOrEmpty(etInputCustomname.getText().toString().trim())){
                    AndroidUtils.Toast(MyPositionCustomListActivity.this,"添加的自定义职位不能为空");
                }
                //简单化用sp存储
                String custom = SPStaticUtils.getString(SPConstant.POSITION_CUSTOM);
                //保存自定义职位
                if(!AndroidUtils.isNullOrEmpty(custom)){
                    String[] strArr = custom.split(",");
                    if(3==strArr.length){
                        AndroidUtils.Toast(MyPositionCustomListActivity.this,"您已经添加3个自定义职业，请先删除再添加");
                        return;
                    }
                    for(int i=0;i<strArr.length;i++){
                        if(!AndroidUtils.isNullOrEmpty(strArr[i])){
                            if(etInputCustomname.getText().toString().trim().equals(strArr[i])){
                                AndroidUtils.Toast(MyPositionCustomListActivity.this,"该职业已经存在，不能添加相同的职业");
                                return;
                            }
                        }
                    }
                    custom = custom+etInputCustomname.getText().toString().trim()+",";
                }else{
                    custom = etInputCustomname.getText().toString().trim()+",";
                }
                SPStaticUtils.put(SPConstant.POSITION_CUSTOM, custom);
                etInputCustomname.setText("");

                //刷新list
                data.clear();
                if(!AndroidUtils.isNullOrEmpty(custom)){
                    String[] strArr = custom.split(",");
                    for(int i=0;i<strArr.length;i++){
                        if(!AndroidUtils.isNullOrEmpty(strArr[i])){
                            data.add(strArr[i]);
                        }
                    }
                }
                adapter.notifyDataSetChanged();

                AndroidUtils.Toast(MyPositionCustomListActivity.this,"添加成功");
            }
        });
        String custom = SPStaticUtils.getString(SPConstant.POSITION_CUSTOM);
        if(!AndroidUtils.isNullOrEmpty(custom)){
            String[] strArr = custom.split(",");
            for(int i=0;i<strArr.length;i++){
                if(!AndroidUtils.isNullOrEmpty(strArr[i])){
                    data.add(strArr[i]);
                }
            }
        }
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_line));
        rvPositionList.addItemDecoration(divider);
        rvPositionList.setLayoutManager(new LinearLayoutManager(this));
        rvPositionList.setAdapter(adapter);





        if (etInputCustomname.getText().toString().trim().length() > 0){
            ivPositionClear.setVisibility(View.VISIBLE);
            btn_right.setTextColor(Color.parseColor("#00CAB8"));
        }else{
            ivPositionClear.setVisibility(View.GONE);
            btn_right.setTextColor(Color.parseColor("#CCCCCC"));
        }
        etInputCustomname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0){
                    ivPositionClear.setVisibility(View.VISIBLE);
                    btn_right.setTextColor(Color.parseColor("#00CAB8"));
                }else{
                    ivPositionClear.setVisibility(View.GONE);
                    btn_right.setTextColor(Color.parseColor("#CCCCCC"));
                }
            }
        });
        ivPositionClear.setOnClickListener(view -> etInputCustomname.setText(""));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_position_custom_list;
    }
}