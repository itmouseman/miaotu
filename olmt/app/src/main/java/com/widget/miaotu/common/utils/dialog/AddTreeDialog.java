package com.widget.miaotu.common.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.bean.NurseryNameBean;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.home.adapter.BaseAdapter;
import com.widget.miaotu.master.home.adapter.DialogNurseryNameAdapter;

import java.util.List;

public class AddTreeDialog extends Dialog {

    private TextView ivCancle;
    private TextView btn_confirm;
    RecyclerView recyclerView;
    private Context mContext;

    DialogNurseryNameAdapter adapter;

    private List<NurseryNameBean> data ;

    private String id;

    public AddTreeDialog(Context context, List<NurseryNameBean> data) {
        super(context, R.style.MyDialog);
        mContext =context;

        this.data = data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window =getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.dialog_add_tree);
        ivCancle  = (TextView) findViewById(R.id.iv_cancle);//取消
        btn_confirm = (TextView) findViewById(R.id.btn_confirm);//确定
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        adapter = new DialogNurseryNameAdapter(getContext(), data, com.widget.miaotu.R.layout.item_dialog_nurseryname_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                for(int i=0;i<data.size();i++){
                    data.get(i).setIsShow(0);
                }
                data.get(position).setIsShow(1);
                id =  data.get(position).getId();
                adapter.notifyDataSetChanged();
            }
        });

        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SendDemandActivity
                if(AndroidUtils.isNullOrEmpty(id)){
                    AndroidUtils.Toast(mContext,"请先选择苗圃");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("type", "0");
                intent.putExtra("gardenId", id+"");
                intent.setClass(mContext, AddMiaoMuActivity.class);
                mContext.startActivity(intent);
                dismiss();
            }
        });
    }

}
