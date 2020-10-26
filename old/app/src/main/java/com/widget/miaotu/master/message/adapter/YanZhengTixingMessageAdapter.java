package com.widget.miaotu.master.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.MsgVerifyBean;
import com.widget.miaotu.http.bean.SysMessageBean;

import java.util.ArrayList;

public class YanZhengTixingMessageAdapter extends BaseQuickAdapter<MsgVerifyBean, BaseViewHolder> {

    private final Context mContext;

    public YanZhengTixingMessageAdapter(Context context) {
        super(R.layout.item_yztx_details_msg, new ArrayList<>());
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgVerifyBean item) {
        Glide.with(mContext).load(item.getHeadUrl()).into((ImageView) helper.getView(R.id.rciv_yztx));
        helper.setText(R.id.tv_yztx_detail_name, item.getNickname());
        TextView tv_yihulue = helper.getView(R.id.tv_yihulue);
        TextView stv_yztx_detail_tongguo = helper.getView(R.id.stv_yztx_detail_tongguo);
        TextView stv_yztx_detail_hulue = helper.getView(R.id.stv_yztx_detail_hulue);
        if (item.getIsPass().intValue() == 1) {//是否通过( 1.待通过 2.已通过 3.已忽略)
            tv_yihulue.setVisibility(View.GONE);
            stv_yztx_detail_tongguo.setVisibility(View.VISIBLE);
            stv_yztx_detail_hulue.setVisibility(View.VISIBLE);
        } else if (item.getIsPass().intValue() == 2) {
            tv_yihulue.setVisibility(View.VISIBLE);
            stv_yztx_detail_tongguo.setVisibility(View.GONE);
            stv_yztx_detail_hulue.setVisibility(View.GONE);
            tv_yihulue.setText("已通过");
        } else if (item.getIsPass().intValue() == 3) {
            tv_yihulue.setVisibility(View.VISIBLE);
            stv_yztx_detail_tongguo.setVisibility(View.GONE);
            stv_yztx_detail_hulue.setVisibility(View.GONE);
            tv_yihulue.setText("已忽略");
        }
    }
}
