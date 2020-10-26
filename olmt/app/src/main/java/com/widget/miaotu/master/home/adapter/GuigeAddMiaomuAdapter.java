package com.widget.miaotu.master.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.SendDemandSeedBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuigeAddMiaomuAdapter extends BaseQuickAdapter<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean, BaseViewHolder> {
    private final Context mContext;
    private final ViewAndPositionCallBack mViewAndPositionCallBack;
    private final int type;

    public GuigeAddMiaomuAdapter(Context context, int type ,int layoutResId, @Nullable List<SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean> data, ViewAndPositionCallBack viewAndPositionCallBack) {
        super(layoutResId, data);
        mContext = context;
        mViewAndPositionCallBack = viewAndPositionCallBack;
        this.type =  type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean classifyFirstsBean) {

        Logger.e(classifyFirstsBean.toString());
        baseViewHolder.setText(R.id.tv_guige_title, classifyFirstsBean.getName());
        baseViewHolder.setText(R.id.tv_guige_unit, classifyFirstsBean.getUnit());

        if (type==1){
            if (baseViewHolder.getAdapterPosition() == 0 || baseViewHolder.getAdapterPosition() == 1) {
                baseViewHolder.getView(R.id.tv_guige_must_fill).setVisibility(View.VISIBLE);
            } else {
                baseViewHolder.getView(R.id.tv_guige_must_fill).setVisibility(View.GONE);
            }
        }else {
            baseViewHolder.getView(R.id.tv_guige_must_fill).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(classifyFirstsBean.getInterval())) {
            baseViewHolder.setText(R.id.et_guige_1, classifyFirstsBean.getInterval().substring(0, classifyFirstsBean.getInterval().indexOf("-")) + "");
            baseViewHolder.setText(R.id.et_guige_2, classifyFirstsBean.getInterval().substring(classifyFirstsBean.getInterval().indexOf("-") + 1) + "");
        }



        if (mViewAndPositionCallBack != null) {
            mViewAndPositionCallBack.viewBack(baseViewHolder, classifyFirstsBean, baseViewHolder.getAdapterPosition());
        }

    }

    public interface ViewAndPositionCallBack {
        void viewBack(BaseViewHolder baseViewHolder, SendDemandSeedBean.ClassifyFirstsBean.ClassifySecondsBean.SpecListsBean classifyFirstsBean, int position);
    }
}
