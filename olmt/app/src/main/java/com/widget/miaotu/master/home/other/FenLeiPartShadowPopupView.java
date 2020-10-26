package com.widget.miaotu.master.home.other;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.master.home.activity.HomeSearchDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Description: 自定义局部阴影弹窗
 * Create by dance, at 2018/12/21
 */
public class FenLeiPartShadowPopupView extends PartShadowPopupView {

    private final Context mContext;
    private DoSomeCallBack doSomeCallBack;
    private RecyclerView recycleView;
    private CommonAdapter<HomeSearchDetailJavaBean.ClassifyCountInfoBean> mAdapter;

    private List<HomeSearchDetailJavaBean.ClassifyCountInfoBean> mListInfo = new ArrayList<>();

    public FenLeiPartShadowPopupView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.fenlei_shadow_popup;
    }


    public void  cleadListData(){
        mListInfo.clear();
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        recycleView = (RecyclerView) findViewById(R.id.recyclerView_fenlei_shaixuan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
//        List<HomeSearchDetailJavaBean.ClassifyCountInfoBean> list = new ArrayList(mListInfo);
        mAdapter = new CommonAdapter<HomeSearchDetailJavaBean.ClassifyCountInfoBean>(mContext, R.layout.home_search_fl_shaixuan_item,mListInfo ) {
            public int currentSelect = 0;

            @Override
            protected void convert(ViewHolder holder, HomeSearchDetailJavaBean.ClassifyCountInfoBean s, int position) {
                holder.setText(R.id.tv_home_search_flsx_item1, s.getClassifyName());
                if (s.getCount()==0){
                    holder.getView(R.id.tv_home_search_flsx_item2).setVisibility(GONE);
                }else {
                    holder.getView(R.id.tv_home_search_flsx_item2).setVisibility(VISIBLE);
                    holder.setText(R.id.tv_home_search_flsx_item2, s.getCount() + "");

                }
                if (currentSelect == holder.getAdapterPosition()) {
                    ((TextView) holder.getView(R.id.tv_home_search_flsx_item1)).setTextColor(Color.parseColor("#03DAC5"));
                    ((TextView) holder.getView(R.id.tv_home_search_flsx_item2)).setTextColor(Color.parseColor("#03DAC5"));
                } else {
                    ((TextView) holder.getView(R.id.tv_home_search_flsx_item1)).setTextColor(Color.parseColor("#333333"));
                    ((TextView) holder.getView(R.id.tv_home_search_flsx_item2)).setTextColor(Color.parseColor("#333333"));
                }


                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentSelect = holder.getAdapterPosition();
                        notifyDataSetChanged();
                        if (doSomeCallBack != null) {
                            doSomeCallBack.onPopItemClick(((TextView) holder.getView(R.id.tv_home_search_flsx_item1)).getText().toString());
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


    }

    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

    public void setCallbackDoSome(DoSomeCallBack doSomeCallBack) {
        this.doSomeCallBack = doSomeCallBack;
    }

    public void setDataChange(List<HomeSearchDetailJavaBean.ClassifyCountInfoBean> classifyCountInfoBeanList) {
        for (int i = 0; i < classifyCountInfoBeanList.size(); i++) {
            mListInfo.add(classifyCountInfoBeanList.get(i));
//            if (classifyCountInfoBeanList.get(i).getCount() > 0) {
//                mListInfo.add(classifyCountInfoBeanList.get(i));
//            }
        }

    }


    public interface DoSomeCallBack {
        void onPopItemClick(String mTilte);
    }
}
