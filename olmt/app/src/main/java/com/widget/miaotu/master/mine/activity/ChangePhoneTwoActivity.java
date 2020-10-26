package com.widget.miaotu.master.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.other.CountDownTimerUtils;

import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.BindMobileJavaBeanOne;
import com.widget.miaotu.http.bean.SendSMSSendBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import butterknife.BindView;

public class ChangePhoneTwoActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.tv_change_phone_describe)
    TextView changePhoneDescribe;

    @BindView(R.id.et_change_phone_code)
    EditText changePhoneCode;

    @BindView(R.id.tv_change_phone_getCode)
    TextView changePhoneGetCode;

    @BindView(R.id.stv_change_phone_two_next)
    ShapeTextView stvChangePhoneNext;
    private String mPhone;


    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initData() {

        initTopBar();

        getPhonCodeForHttp();

        changePhoneGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhonCodeForHttp();
            }
        });
    }


    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("更换绑定手机").setTextColor(Color.parseColor("#EBF9FF"));


        stvChangePhoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下一步
                String phoneCode = changePhoneCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneCode)) {
                    ToastUtils.showShort("请输入验证码!");
                    return;
                }


                changePhone(phoneCode);

            }
        });
    }

    private void changePhone(String phoneCode) {

        RetrofitFactory.getInstence().API().modifyPhone(new BindMobileJavaBeanOne(mPhone, phoneCode))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {

                if (t.getStatus() == 100) {
                    ToastUtils.showLong("修改成功");
                    Intent intent = new Intent();
                    intent.setClass(ChangePhoneTwoActivity.this, MeInfoActivity.class);
                    startActivity(intent);
//                    RxBus.getInstance().post(new EditUserInfoChange(mPhone));
                } else {

                    ToastUtils.showLong(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("请求出错");
            }
        });
    }


    @Override
    protected void initView() {

        mPhone = getIntent().getStringExtra("mPhone");
        changePhoneDescribe.setText(mPhone);
    }


    private void getPhonCodeForHttp() {
        //"0(注册登录)", "1(修改手机号)", "2(修改密码)", "3(重置密码)", "其他待定"

        RetrofitFactory.getInstence().API().getSendSMSNewList(new SendSMSSendBeanNew(mPhone, 1))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                if (t.getStatus() == 100) {

                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(changePhoneGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                } else {
                    ToastUtils.showShort(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("请求出错");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone_two;
    }
}
