package com.widget.miaotu.master.message.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;


import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.http.bean.SysMessageBean;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SystemMessageAdapter extends BaseQuickAdapter<SysMessageBean, BaseViewHolder> {

    private final Context mContext;

    public SystemMessageAdapter(Context context) {
        super(R.layout.item_sys_details_msg, new ArrayList<>());
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SysMessageBean item) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
//        try {
//            Date format = sdf.parse(item.getCreate_time());
//            helper.setText(R.id.textView192, TimeUtils.getFriendlyTimeSpanByNow(format));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        helper.setText(R.id.textView192, item.getCreate_time());

        if (StringUtils.isEmpty(item.getCover())) {
            helper.getView(R.id.sys_cover).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.sys_cover).setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCover()).into((ImageView) helper.getView(R.id.sys_cover));
        }

        helper .setText(R.id.sys_title, item.getTitle())
                .setText(R.id.sys_content, item.getContent());


    }
}
