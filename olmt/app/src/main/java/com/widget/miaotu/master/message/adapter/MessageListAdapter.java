package com.widget.miaotu.master.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;

import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.master.message.activity.FriendsDetailsActivity;
import com.widget.miaotu.master.message.activity.TongXunLuActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends BaseQuickAdapter<MessageListMainBean.MessageUserInfo, BaseViewHolder> {


    private final Context mContext;

    public MessageListAdapter(Context context) {
        super(R.layout.item_message, new ArrayList<>());
        mContext = context;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageListMainBean.MessageUserInfo item) {
        Glide.with(mContext).load(item.getHeadUrl()).into((ImageView) baseViewHolder.getView(R.id.circleImageView));
        if (item.getUnreadMsgCount() > 0) {
            baseViewHolder.getView(R.id.stv_msg_list_num).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.stv_msg_list_num, item.getUnreadMsgCount() + "");
        } else {
            baseViewHolder.getView(R.id.stv_msg_list_num).setVisibility(View.GONE);
        }

        if (item.getMsgTime() != 0) {
            baseViewHolder.setText(R.id.textView_msg_time, TimeUtils.getFriendlyTimeSpanByNow(item.getMsgTime()));
        }

        baseViewHolder.setText(R.id.textView_msg_name, item.getNickname());

        String content = item.getLastMessage();
        if (!TextUtils.isEmpty(content)) {
            if (content.substring(0, 3).equals("txt")) {
                baseViewHolder.setText(R.id.textView_msg_content, JsonUtils.getString("{" + content + "}", "txt"));
            } else if (content.substring(0, 3).equals("ima")) {
                baseViewHolder.setText(R.id.textView_msg_content, "[图片]");
            } else if (content.substring(0, 3).equals("voi")) {
                baseViewHolder.setText(R.id.textView_msg_content, "[语音]");
            } else if (content.substring(0,3).equals("loc")){
                baseViewHolder.setText(R.id.textView_msg_content, "[位置]");
            } else {
                baseViewHolder.setText(R.id.textView_msg_content, content);
            }
        }

        baseViewHolder.getView(R.id.circleImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("userIdX", item.getUserIdX());
                intent.setClass(mContext, FriendsDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


}
