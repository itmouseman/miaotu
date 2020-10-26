package com.widget.miaotu.master.message.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.http.bean.MayBeFriendsBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddMiaoYouAdapter extends BaseQuickAdapter<MayBeFriendsBean, BaseViewHolder> {

    private final Context mContext;
    private AddFirendCallBack mAddFirendCallBack;

    public AddMiaoYouAdapter(Context context) {
        super(R.layout.item_add_miao_you, new ArrayList<>());
        mContext = context;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MayBeFriendsBean mayBeFriendsBean) {

        if (mayBeFriendsBean.getHeadUrl().contains("http")) {
            GlideUtils.loadUrl(mContext, mayBeFriendsBean.getHeadUrl(), baseViewHolder.getView(R.id.rctv_add_miaoyou_head));
        } else {
            GlideUtils.loadUrl(mContext, "http://oss-cn-beijing.aliyuncs.com/miaotu1" + mayBeFriendsBean.getHeadUrl(), baseViewHolder.getView(R.id.rctv_add_miaoyou_head));
        }

        baseViewHolder.setText(R.id.tv_add_miao_you_1, mayBeFriendsBean.getPhone());
        if (mayBeFriendsBean.getIsFriend() == 0 || mayBeFriendsBean.getIsFriend() == 3 || mayBeFriendsBean.getIsFriend() == 4) {
            baseViewHolder.getView(R.id.tv_add_miao_you_2).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_add_miao_you_2, mayBeFriendsBean.getIsFriend() + "个共同好友");//0 3 4 是非好友
        } else {
            baseViewHolder.getView(R.id.tv_add_miao_you_2).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.tv_add_miao_you_3, "手机联系人: " + mayBeFriendsBean.getNickname());
        baseViewHolder.getView(R.id.stv_add_miaoyou).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//加好友
                if (mAddFirendCallBack != null) {
                    mAddFirendCallBack.addFirend(mayBeFriendsBean.getUserId(),baseViewHolder.getLayoutPosition());
                }
            }
        });
    }


    public void setCallBack(AddFirendCallBack addFirendCallBack) {
        mAddFirendCallBack = addFirendCallBack;
    }

    public interface AddFirendCallBack {
        void addFirend(String userId,int position);
    }
}
