package com.widget.miaotu.master.message.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.util.DateUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.GlideUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public class MessageListAdapter extends BaseQuickAdapter<EMConversation, BaseViewHolder> {

    private final Context mContext;
    private final ItemClickCallBack mItemClickCallBack;

    public MessageListAdapter(int layoutResId, @Nullable List<EMConversation> data, Context context, ItemClickCallBack itemClickCallBack) {
        super(layoutResId, data);
        mContext = context;
        mItemClickCallBack = itemClickCallBack;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, EMConversation emConversation) {
        String username = emConversation.conversationId();
        baseViewHolder.setText(R.id.textView_msg_name, username);
        if (emConversation.getUnreadMsgCount() > 0) {
            // show unread message count
            baseViewHolder.setText(R.id.stv_msg_list_num, String.valueOf(emConversation.getUnreadMsgCount()));

            baseViewHolder.getView(R.id.stv_msg_list_num).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.stv_msg_list_num).setVisibility(View.GONE);
        }


        if (emConversation.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = emConversation.getLastMessage();

            baseViewHolder.setText(R.id.textView_msg_content, EaseSmileUtils.getSmiledText(getContext(), EaseCommonUtils.getMessageDigest(lastMessage, (this.getContext()))));
            baseViewHolder.setText(R.id.textView_msg_time, DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));

        }



//        if (emConversation.getType() == EMConversation.EMConversationType.GroupChat) {
//
//        } else if(emConversation.getType() == EMConversation.EMConversationType.ChatRoom){
//
//        }else{
//            EaseUser user = EaseUserUtils.getUserInfo(username);
//
//            if (!TextUtils.isEmpty(user.getAvatar()) && user.getAvatar() != null && user != null) {
//                GlideUtils.loadUrl(mContext, user.getAvatar(), baseViewHolder.getView(R.id.circleImageView));
//            } else {
////            GlideUtils.loadUrl(mContext,"",baseViewHolder.getView(R.id.circleImageView));
//            }
//        }



        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickCallBack != null) {
                    mItemClickCallBack.itemCLick(emConversation);
                }
            }
        });

    }




    public interface ItemClickCallBack {
        void itemCLick(EMConversation emConversation);
    }

}
