package com.widget.miaotu.master.message.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.rclayout.RCImageView;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HerUserInfoBean;
import com.widget.miaotu.http.bean.head.HeadHerUserIdBean;
import com.widget.miaotu.http.bean.head.HeadUserIdBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.home.activity.CompanyDetailActivity;
import com.yanzhenjie.permission.AndPermission;

import butterknife.BindView;

/**
 * 好友详情界面
 */
public class FriendsDetailsActivity extends MBaseActivity {

    @BindView(R.id.qui_top_bar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.rcv_friend_detail_head)
    RCImageView rcImageViewHead;
    @BindView(R.id.tv_friends_detail_name)
    TextView tvUserName;
    @BindView(R.id.tv_friends_detail_sex)
    ImageView iv_sex;
    @BindView(R.id.stv_friends_detail_rz)
    ShapeTextView stvRenzheng;
    @BindView(R.id.tv_friends_detail_company)
    TextView tvCompany;
    @BindView(R.id.tv_friends_detail_profession)
    TextView tvProfession;
    @BindView(R.id.stv_friends_detail_add_friends)
    ShapeTextView add_friends;
    @BindView(R.id.stv_friends_detail_message)
    ShapeTextView pushMessage;
    @BindView(R.id.stv_friends_detail_call)
    ShapeTextView callPhone;
    @BindView(R.id.ll_friends_jump_company)
    LinearLayout jumpCompany;

    private String userId;

    @Override
    protected MVCControl createControl() {
        return null;
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
        mTopBar.setTitle("").setTextColor(Color.parseColor("#EBF9FF"));


    }

    @Override
    protected boolean isUseFullScreenMode() {
        return  true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_details;
    }

    @Override
    protected void initDetailData() {
        userId = getIntent().getStringExtra("userIdX");
        RetrofitFactory.getInstence().API().getHerInfo(new HeadHerUserIdBean(userId)).compose(TransformerUi.setThread()).subscribe(new BaseObserver<HerUserInfoBean>(FriendsDetailsActivity.this) {
            @Override
            protected void onSuccess(BaseEntity<HerUserInfoBean> t) throws Exception {

                if (t.getStatus() == 100) {
                    HerUserInfoBean herUserInfoBean = t.getData();
                    showTextViewImage(herUserInfoBean.getNickname(), herUserInfoBean.getIsVIP());
                    GlideUtils.loadUrl(FriendsDetailsActivity.this, herUserInfoBean.getAvatar(), rcImageViewHead);
                    if (herUserInfoBean.getSex() == 1) {//男
                        iv_sex.setImageResource(R.mipmap.ic_me_man);
                    } else {
                        iv_sex.setImageResource(R.mipmap.ic_me_woman);
                    }

                    if (herUserInfoBean.getIsPersonalAuth() == -1) {
                        stvRenzheng.setText("未提交");
                    } else if (herUserInfoBean.getIsPersonalAuth() == 1) {
                        stvRenzheng.setText("审核中");
                    } else if (herUserInfoBean.getIsPersonalAuth() == 2) {
                        stvRenzheng.setText("已通过");
                    } else if (herUserInfoBean.getIsPersonalAuth() == 3) {
                        stvRenzheng.setText("未通过");
                    }

                    tvCompany.setText(herUserInfoBean.getCompanyName());
                    tvProfession.setText(herUserInfoBean.getJobTitle());
                    if (herUserInfoBean.getIsFriend() == 1) {
                        add_friends.setVisibility(View.GONE);
                    } else {
                        add_friends.setVisibility(View.VISIBLE);
                        add_friends.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addMyFirend(String.valueOf(herUserInfoBean.getUserId()));
                            }
                        });
                    }
                    pushMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UserCacheManager.save(String.valueOf(herUserInfoBean.getUserId()), herUserInfoBean.getNickname(), herUserInfoBean.getAvatar());
                            Intent intent = new Intent(FriendsDetailsActivity.this, ChatActivity.class);
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, String.valueOf(herUserInfoBean.getUserId()));
                            intent.putExtra("chartTitle",  herUserInfoBean.getNickname());
                            startActivity(intent);

                        }
                    });
                    callPhone.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            callPhone(herUserInfoBean.getPhone());
                        }
                    });
                    jumpCompany.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("userId", String.valueOf(herUserInfoBean.getUserId()));
                            intent.putExtra("company_id", herUserInfoBean.getCompanyId());
                            intent.setClass(FriendsDetailsActivity.this, CompanyDetailActivity.class);
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

    @SuppressLint("WrongConstant")
    private void callPhone(String phone) {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.CALL_PHONE)
                .onGranted(permissions -> {

                    com.widget.miaotu.common.utils.IntentUtils.openCall(FriendsDetailsActivity.this, phone);
                })
                .onDenied(permissions -> {

                    ToastUtils.showShort("需要电话权限");
                })
                .start();

    }

    private void showTextViewImage(String showText, int isVip) {


        SpannableString ss = new SpannableString(showText + "   ");
        if (isVip == 2) {
            int len = ss.length();
            //图片
            Drawable d = ContextCompat.getDrawable(this, (R.mipmap.ic_me_vip));
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            //构建ImageSpan
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            ss.setSpan(span, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        tvUserName.setText(ss);
    }


    private void addMyFirend(String userId) {
        RetrofitFactory.getInstence().API().addFriends(new HeadUserIdBean(userId)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<Object>(FriendsDetailsActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        if (t.getStatus() == 100) {
                            ToastUtils.showShort("请求发送成功,待通过");
                            add_friends.setVisibility(View.GONE);

                        } else {
                            ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.i(throwable.getMessage());
                    }
                });
    }
}
