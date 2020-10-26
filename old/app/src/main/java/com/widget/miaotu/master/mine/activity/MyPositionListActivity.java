package com.widget.miaotu.master.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.bumptech.glide.Glide;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.PublicDicInfoBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mine.adapter.PositionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 我的职位类型基础数据展示
 */
public class MyPositionListActivity extends BaseActivity {

    @BindView(R.id.rv_position_list)
    RecyclerView rvPositionList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_right)
    Button btn_right;


    private PositionAdapter adapter;

    //请求数据
    List<PublicDicInfoBean.DataBean> dataBeans = new ArrayList<>();

    boolean isStart=true;

    List<String> listString =  new ArrayList<>();


    @Override
    protected void initData() {


        RetrofitFactory.getInstence().API().userJobTitle().compose(TransformerUi.setThread()).subscribe(new BaseObserver<List<String> >(this) {
            @Override
            protected void onSuccess(BaseEntity<List<String> > t) throws Exception {
                if (t.getStatus()==100){
                    listString.clear();
                    listString.addAll(t.getData());
                    refash();
                }else {
                    LoadingUtil.loadingClose(false, "" + t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                LoadingUtil.loadingClose(false, "出错啦!" );
            }
        });



    }

    @Override
    protected void initView() {

        tvTitle.setText("我的职位");
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存");
        btn_right.setTextColor(Color.parseColor("#03DAC5"));

        dataBeans.add(new PublicDicInfoBean.DataBean(0, 0, "", "职位"));
        for (int j = 0; j < listString.size(); j++) {
            dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", listString.get(j)));
        }
        String custom = SPStaticUtils.getString(SPConstant.POSITION_CUSTOM);
        if(!AndroidUtils.isNullOrEmpty(custom)){
            String[] strArr = custom.split(",");
            for(int i=0;i<strArr.length;i++){
                if(!AndroidUtils.isNullOrEmpty(strArr[i])){
                    dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", ""+strArr[i]));
                }
            }
        }

        dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", "自定义+"));
        rvPositionList.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        adapter = new PositionAdapter(dataBeans);
        rvPositionList.setAdapter(adapter);
        adapter.setOnClickListener(new PositionAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String title, int titleId) {
                if(position==dataBeans.size()-1){
//                    AndroidUtils.Toast(MyPositionListActivity.this,"自定义添加");
                    startActivity(new Intent(MyPositionListActivity.this,MyPositionCustomListActivity.class));
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("titleId", titleId);
                intent.putExtra("position", position);
                // 设置返回码和返回携带的数据
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新加载
        if(isStart){
            isStart = false;
        }else{
            refash();
        }
    }

    private void refash(){
        dataBeans.clear();

        dataBeans.add(new PublicDicInfoBean.DataBean(0, 0, "", "职位"));
        for (int j = 0; j < listString.size(); j++) {
            dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", listString.get(j)));
        }
        String custom = SPStaticUtils.getString(SPConstant.POSITION_CUSTOM);
        if(!AndroidUtils.isNullOrEmpty(custom)){
            String[] strArr = custom.split(",");
            for(int i=0;i<strArr.length;i++){
                if(!AndroidUtils.isNullOrEmpty(strArr[i])){
                    dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", ""+strArr[i]));
                }
            }
        }
        dataBeans.add(new PublicDicInfoBean.DataBean(1, 0, "", "自定义+"));

        adapter.notifyDataSetChanged();

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_position_list;
    }
}