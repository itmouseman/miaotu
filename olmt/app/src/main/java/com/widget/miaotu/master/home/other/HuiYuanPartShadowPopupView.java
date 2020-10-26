package com.widget.miaotu.master.home.other;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.widget.miaotu.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;


/**
 * Description: 自定义局部阴影弹窗
 * Create by dance, at 2018/12/21
 */
public class HuiYuanPartShadowPopupView extends PartShadowPopupView {
    private final Context mContext;
    private HYDoSomeCallBack doSomeCallBack;
    private final ArrayList<String> mList;
    private RecyclerView recycleView;
    private CommonAdapter<String> mAdapter;



    public HuiYuanPartShadowPopupView(@NonNull Context context) {
        super(context);
        mContext = context;
        mList = new ArrayList<>();
        mList.add("会员优先");
        mList.add("关注优先");
        mList.add("最近距离");
        mList.add("最新发布");
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.huiyuan_shadow_popup;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        recycleView = (RecyclerView) findViewById(R.id.recyclerView_huiyuan_shaixuan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommonAdapter<String>(mContext, R.layout.home_search_hy_shaixuan_item, mList) {
            public int currentSelect = 0;
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_YX_item1, s);

                if (currentSelect == holder.getAdapterPosition()) {
                    holder.getView(R.id.iv_YX_item1).setVisibility(VISIBLE);
                    ((TextView) holder.getView(R.id.tv_YX_item1)).setTextColor(Color.parseColor("#03DAC5"));
                } else {
                    holder.getView(R.id.iv_YX_item1).setVisibility(GONE);
                    ((TextView) holder.getView(R.id.tv_YX_item1)).setTextColor(Color.parseColor("#333333"));
                }

                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (doSomeCallBack != null) {
                            currentSelect = holder.getAdapterPosition();
                            notifyDataSetChanged();
                            doSomeCallBack.onPopDismiss(((TextView) holder.getView(R.id.tv_YX_item1)).getText().toString());
                        }
                        dismiss();
                    }
                });
            }
        };
        recycleView.setAdapter(mAdapter);

    }

    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "CustomPartShadowPopupView onShow");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomPartShadowPopupView onDismiss");
    }

    public void setCallbackDoSome(HYDoSomeCallBack doSomeCallBack) {
        this.doSomeCallBack = doSomeCallBack;

    }


    public interface HYDoSomeCallBack {
        void onPopDismiss(String mTitle);
    }
}
