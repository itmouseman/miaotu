package com.widget.miaotu.master.other.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.other.VerificationCountDownTimer;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.ChangePassWordJavaBean;
import com.widget.miaotu.http.bean.SendSMSSendBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import butterknife.BindView;

/**
 * 修改密码 忘记密码  找回密码
 */
public class ActivityModifyPassword extends BaseActivity {

    @BindView(R.id.et_login_phone_mp)
    EditText etPhone;
    @BindView(R.id.et_login_check_code_mp)
    EditText etCheckCode;
    @BindView(R.id.et_login_new_password)
    EditText etPassword;
    @BindView(R.id.tv_login_get_check_code_mp)
    TextView tvSendCheckCode;

    String phone;


    //倒计时
    public VerificationCountDownTimer verificationCountDownTimer;


    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        phone = getIntent().getStringExtra("phone");
        etPhone.setText(phone);
        initCountDownTimer();

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initReset();
            }
        });
        findViewById(R.id.tv_login_get_check_code_mp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("联系方式不能为空!");
                    return;
                }


                RetrofitFactory.getInstence().API().getSendSMSNewList(new SendSMSSendBeanNew(etPhone.getText().toString().trim(), 3))
                        .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(ActivityModifyPassword.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        if (t.getStatus() == 100) {
                            phone = etPhone.getText().toString().trim();
                            verificationCountDownTimer.timerStart(true);
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
        });
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }


    private void initReset() {
        if (etPassword.getText().toString().trim().length() >= 6 || etPassword.getText().toString().trim().length() <= 12) {
            if (etCheckCode.getText().toString().trim().length() == 0) {
                ToastUtils.showShort("验证码不能为空!");
                return;
            }
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请先获取验证码！");
                return;
            }
            if (etPassword.getText().toString().trim().length() == 0) {
                ToastUtils.showShort("新密码不能为空!");
                return;
            }


            RetrofitFactory.getInstence().API().resetPassword(new ChangePassWordJavaBean(phone, etPassword.getText().toString().trim(), etCheckCode.getText().toString().trim()))
                    .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(ActivityModifyPassword.this) {
                @Override
                protected void onSuccess(BaseEntity<Object> t) throws Exception {
                    Logger.e(t.getData().toString());
                    if (t.getStatus() == 100) {
                        ToastUtils.showShort("密码修改成功!");
                        finish();
                    } else {
                        ToastUtils.showShort(t.getMessage());
                    }
                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    ToastUtils.showLong("网络异常，请重试！");
                }
            });

        } else {
            ToastUtils.showShort("请设置合法的密码！");
        }
    }


    /**
     * 初始化短信倒计时
     */
    public void initCountDownTimer() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN &&
                VerificationCountDownTimer.curMillis + 60000 > System.currentTimeMillis()) {
            setCountDownTimer(VerificationCountDownTimer.curMillis + 60000 - System.currentTimeMillis());
            verificationCountDownTimer.timerStart(false);
        } else {
            setCountDownTimer(60000);
        }
    }


    /**
     * 倒计时监听
     *
     * @param countDownTime
     */
    public void setCountDownTimer(final long countDownTime) {

        verificationCountDownTimer = new VerificationCountDownTimer(countDownTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                if (tvSendCheckCode != null) {
                    tvSendCheckCode.setEnabled(false);
                    tvSendCheckCode.setText((millisUntilFinished / 1000) + "S");
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (tvSendCheckCode != null) {
                    tvSendCheckCode.setEnabled(true);
                    tvSendCheckCode.setText("重新获取");
                    if (countDownTime != 60000) {
                        setCountDownTimer(60000);
                    }
                }

            }
        };


    }


}
