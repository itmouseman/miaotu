package com.widget.miaotu.master.other.login;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.BindMobileJavaBeanOne;
import com.widget.miaotu.http.bean.BindMobileJavaBeanTwo;
import com.widget.miaotu.http.bean.LoginTokenBean;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mvp.LoginBindMobileControl;
import com.widget.miaotu.master.mvp.LoginBindMobileView;
import com.widget.miaotu.master.mvp.LoginModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定页面
 *
 * @author tzy
 */
public class BindMobileActivity extends MBaseActivity<LoginBindMobileControl> implements LoginBindMobileView {

    /**
     * 手机号
     */
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    /**
     * 验证码
     */
    @BindView(R.id.et_login_check_code)
    EditText etLoginCheckCode;
    /**
     * 获取验证码
     */
    @BindView(R.id.tv_login_get_check_code)
    TextView loginGetCheckCode;
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.bt_login)
    TextView btLogin;


    private String wxId = "";

    private String type = "";


    @Override
    protected void initDetailData() {
        //  初始化验证码倒计时
        mControl.initCountDownTimer();
    }

    @Override
    protected LoginBindMobileControl createControl() {
        return new LoginBindMobileControl();
    }

    @Override
    protected void initView() {
        wxId = getIntent().getStringExtra("wxId");
        type = getIntent().getStringExtra("type");

        Logger.e("wxId = " + wxId + "---------" + type);
        if ("1".equals(type)) {
            tvLoginTitle.setText("绑定新手机号");
            btLogin.setText("绑定");
        }

        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loginGetCheckCode.setTextColor(Color.parseColor("#00CAB8"));
                loginGetCheckCode.setClickable(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!AndroidUtils.isNullOrEmpty(etLoginPhone.getText().toString().trim())) {
                    loginGetCheckCode.setTextColor(Color.parseColor("#00CAB8"));
                    loginGetCheckCode.setClickable(true);
                } else {
                    loginGetCheckCode.setTextColor(Color.parseColor("#A2A2A2"));
                    loginGetCheckCode.setClickable(false);
                }

            }
        });
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_mobile;
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.bt_login, R.id.tv_login_get_check_code, R.id.iv_login})
    public void onClick(View view) {
        //  手机号登录
        String phone = etLoginPhone.getText().toString().trim();
        String checkCode = etLoginCheckCode.getText().toString().trim();
        switch (view.getId()) {
            case R.id.bt_login:
                showWaiteDialog("正在加载中....");
                RetrofitFactory.getInstence().API().bindPhone(new BindMobileJavaBeanTwo(phone, wxId, checkCode))
                        .compose(TransformerUi.setThread()).subscribe(new BaseObserver<LoginTokenBean>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<LoginTokenBean> t) throws Exception {
                        Logger.e(t.toString());
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {

                            ToastUtils.showLong("绑定成功");

                            // 保存TOKEN至本地,
                            LoginTokenBean data = (LoginTokenBean) t.getData();
                            // 保存TOKEN至本地,继续请求userInfo
                            SPStaticUtils.put(SPConstant.AUTHORIZATION, data.getOldToken());
                            SPStaticUtils.put(SPConstant.NEW_TOKEN, data.getNewToken());


                            RetrofitFactory.getInstence().API().getUserInfo(new TokenBeanNew(data.getNewToken()))
                                    .compose(TransformerUi.setThread()).subscribe(new BaseObserver<SMSLoginBeanNew>(BaseApplication.instance()) {
                                @Override
                                protected void onSuccess(BaseEntity<SMSLoginBeanNew> t) throws Exception {
                                    if (t.getStatus() == 100) {
                                        // 保存TOKEN至本地,继续请求userInfo
                                        SMSLoginBeanNew smsLoginBeanNew = (SMSLoginBeanNew) t.getData();

                                        //登录环信
                                        HuanXinLogin.huanXinLogin(smsLoginBeanNew);

                                        if ("1".equals(smsLoginBeanNew.getIsCompany())) {
                                            SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                                        } else {
                                            SPStaticUtils.put(SPConstant.ISCOMPANY, "0");
                                        }
                                        //是否VIP
                                        SPStaticUtils.put(SPConstant.IS_VIP, smsLoginBeanNew.getIsVIP());
                                        LoginModel.saveUserInfoNew(smsLoginBeanNew.getUserId() + "", smsLoginBeanNew.getNickname(), smsLoginBeanNew.getNickname(), smsLoginBeanNew.getAvatar(), smsLoginBeanNew.getPhone());

                                        BindMobileActivity.this.finish();
                                    } else {

                                        ToastUtils.showLong(t.getMessage() + "，请重试！");
                                    }
                                }

                                @Override
                                protected void onFail(Throwable throwable) throws Exception {
                                    Logger.e(throwable.getMessage());
                                }
                            });


                        } else {
                            // 绑定手机号
                            ToastUtils.showLong("绑定失败，请重试！");
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        hideWaiteDialog();
                        ToastUtils.showShort("请求出错");
                    }
                });
                break;
            //  验证码获取
            case R.id.tv_login_get_check_code:
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showShort("手机号格式不正确!");
                    return;
                }

                showWaiteDialog("获取验证码中...");
                mControl.sendSMS2(phone);
                break;
            case R.id.iv_login:
                finish();
                break;

            default:
                break;
        }
    }


    /**
     * 登录失败回调
     *
     * @param desc
     */
    @Override
    public void PhoneLoginFail(String desc) {
        hideWaiteDialog();
        ToastUtils.showShort(desc);
    }

    /**
     * 验证码发送成功回调
     *
     * @param data
     */
    @Override
    public void SendSMSSuccess(String data) {

        hideWaiteDialog();
        ToastUtils.showShort("验证码发送成功!");
        //开启验证码倒计时
        mControl.verificationCountDownTimer.timerStart(true);
    }

    /**
     * 验证码发送失败回调
     *
     * @param desc
     */
    @Override
    public void SendSMSFail(String desc) {
        hideWaiteDialog();
        ToastUtils.showShort(desc);
    }

    /**
     * 验证码倒计时回调
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        if (loginGetCheckCode != null) {
            loginGetCheckCode.setEnabled(false);
            loginGetCheckCode.setText((millisUntilFinished / 1000) + "S");
        }

    }

    /**
     * 设置验证码倒计时结束操作
     */
    @Override
    public void onTimeFinish() {
        if (loginGetCheckCode!=null){
            loginGetCheckCode.setEnabled(true);
            loginGetCheckCode.setText("重新获取");
        }

    }


    /**
     * 短信登录成功
     *
     * @param
     */
    @Override
    public void SMSLoginSuccess() {

        hideWaiteDialog();
        ToastUtils.showShort("登录成功!");
        finish();
    }

    /**
     * 短信登录失败
     *
     * @param desc
     */
    @Override
    public void SMSLoginFail(String desc) {
        hideWaiteDialog();
        ToastUtils.showShort(desc);
    }


}
