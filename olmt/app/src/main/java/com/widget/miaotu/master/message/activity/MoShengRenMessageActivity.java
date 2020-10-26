package com.widget.miaotu.master.message.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HerUserInfoBean;
import com.widget.miaotu.http.bean.MessageListMainBean;
import com.widget.miaotu.http.bean.head.HeadHerUserIdBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

/**
 * 陌生人消息
 */
public class MoShengRenMessageActivity extends MBaseActivity {
    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.rlv_moShengRen)
    RecyclerView rlv_moShengRen;
    private BaseQuickAdapter<MessageListMainBean.MessageUserInfo, BaseViewHolder> mAdapter;


    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initView() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("陌生人").setTextColor(Color.parseColor("#EBF9FF"));

        List<MessageListMainBean.MessageUserInfo> moshengrenMessageUserInfoList = (List<MessageListMainBean.MessageUserInfo>) getIntent().getSerializableExtra("UserBeanMoS");

        rlv_moShengRen.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BaseQuickAdapter<MessageListMainBean.MessageUserInfo, BaseViewHolder>(R.layout.item_message, moshengrenMessageUserInfoList) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageListMainBean.MessageUserInfo messageUserInfo) {

                if (!messageUserInfo.getUserIdX().equals("admin")){
                    getOtherInfo(baseViewHolder, messageUserInfo);
                }
                baseViewHolder.setText(R.id.stv_msg_list_num, messageUserInfo.getUnreadMsgCount() + "");
                String content = messageUserInfo.getLastMessage();
                if (!TextUtils.isEmpty(content)) {
                    if (content.substring(0, 3).equals("txt")) {
                        baseViewHolder.setText(R.id.textView_msg_content, JsonUtils.getString("{" + content + "}", "txt"));
                    } else if (content.substring(0, 3).equals("ima")) {
                        baseViewHolder.setText(R.id.textView_msg_content, "[图片消息]");
                    } else if (content.substring(0, 3).equals("voi")) {
                        baseViewHolder.setText(R.id.textView_msg_content, "[语音消息]");
                    } else {
                        baseViewHolder.setText(R.id.textView_msg_content, content);
                    }
                }

                if (messageUserInfo.getMsgTime() != 0) {
                    baseViewHolder.setText(R.id.textView_msg_time, TimeUtils.getFriendlyTimeSpanByNow(messageUserInfo.getMsgTime()));
                }

            }
        };
        rlv_moShengRen.setAdapter(mAdapter);
        if (moshengrenMessageUserInfoList.size() == 0) {
            View emptyView = getLayoutInflater().inflate(R.layout.layout_empty_coment, null);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mAdapter.setEmptyView(emptyView);
        }


    }

    private void getOtherInfo(BaseViewHolder baseViewHolder, MessageListMainBean.MessageUserInfo messageUserInfo) {
        RetrofitFactory.getInstence().API().getHerInfo(new HeadHerUserIdBean(messageUserInfo.getUserIdX())).compose(TransformerUi.setThread()).subscribe(new BaseObserver<HerUserInfoBean>(MoShengRenMessageActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<HerUserInfoBean> t) throws Exception {
                if (t.getStatus() == 100) {

                    GlideUtils.loadUrl(MoShengRenMessageActivity.this, t.getData().getAvatar(), baseViewHolder.getView(R.id.circleImageView));
                    baseViewHolder.setText(R.id.textView_msg_name, t.getData().getNickname());
                    baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String chatUserId = String.valueOf(t.getData().getUserId());
                            String avatarUrl = t.getData().getAvatar();
                            String nickName = t.getData().getNickname();
                            UserCacheManager.save(chatUserId, nickName, avatarUrl);



                            Intent intent = new Intent(MoShengRenMessageActivity.this, ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, messageUserInfo.getUserIdX());
                            intent.putExtra("chartTitle", t.getData().getNickname());
                            startActivity(intent);
                        }
                    });
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moshengren;
    }

    @Override
    protected void initDetailData() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(new HomeUpdateChange(false));
    }
}
