package com.widget.miaotu.master.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.AddFeedBackSeedBean;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class FeedBackActivity extends MBaseActivity {

    @BindView(R.id.et_feed_back_content)
    EditText etFeedBackContent;
    @BindView(R.id.et_feed_back_phone)
    EditText etFeedBackPhone;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

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
        mTopBar.setTitle("意见反馈").setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @OnClick(R.id.btn_feed_back)
    public void onClick() {
        String phone = etFeedBackPhone.getText().toString().trim();
        String content = etFeedBackContent.getText().toString().trim();
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("手机号格式不正确!");
            return;
        }
        if (StringUtils.isEmpty(content)) {
            ToastUtils.showShort("内容不能为空!");
            return;
        }
        RetrofitFactory.getInstence().API().getAddFeedBackList(new AddFeedBackSeedBean("", phone, content)).compose(TransformerUi.setThread())
                .subscribe(new BaseObserver<Object>(this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        Logger.e(t.toString());
                        ToastUtils.showShort("提交成功!");
                        finish();
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        ToastUtils.showShort("提交失败!");
                    }
                });


    }

    @Override
    protected void initDetailData() {

    }
}
