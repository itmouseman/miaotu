package com.widget.miaotu.master.mvp;

import android.app.Activity;
import android.util.Log;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.common.utils.other.VerificationCountDownTimer;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.LoginTokenBean;
import com.widget.miaotu.http.bean.PhoneLoginSendNewBean;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.SMSLoginSendBeanNew;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.other.login.HuanXinLogin;
import com.widget.miaotu.master.other.login.LoginCodeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginControl extends MVCControl<LoginView> {

    LoginModel loginModel;
    //倒计时
    public VerificationCountDownTimer verificationCountDownTimer;
    public static boolean isCompany = false;

    public LoginControl() {
        loginModel = new LoginModel(this);
    }

    @Override
    protected void detatchView() {

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

                mView.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.onTimeFinish();
                if (countDownTime != 60000) {
                    setCountDownTimer(60000);
                }
            }
        };
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param loginCodeActivity
     */
    public void sendSMS(String phone, LoginCodeActivity loginCodeActivity) {
        //简单验证手机号格式
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("手机号格式不正确!");
            return;
        }


        try {
            JSONObject root = new JSONObject();
            root.put("phone", phone);
            root.put("sendType", "0");//请求类型 允许值: "0(注册登录)", "1(修改手机号)", "2(修改密码)", "3(重置密码)", "其他待定"
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

            RetrofitFactory.getInstence().API().getSmsCode(requestBody).compose(TransformerUi.setThread()).subscribe(new BaseObserver<String>(loginCodeActivity) {
                @Override
                protected void onSuccess(BaseEntity<String> t) throws Exception {
                    Logger.e(t.getData().toString());
                    if (t.getStatus() == 100) {
                        mView.SendSMSSuccess(t.getMessage());
                    }else {
                        ToastUtils.showShort(t.getMessage());
                    }

                }

                @Override
                protected void onFail(Throwable throwable) throws Exception {
                    mView.SendSMSFail(throwable.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 手机号登录
     *
     * @param phone
     * @param password
     */
    public void loginPhone(Activity activity, String phone, String password) {
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("手机号格式不正确!");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空!");
            return;
        }

        try {
            LoadingUtil.loadingShow(activity, "登录中...");
        } catch (Exception ex) {

        }


        loginModel.loginPhoneNew(new PhoneLoginSendNewBean(password, phone), new BaseObserver(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity t) throws Exception {
                LoadingUtil.dissmiss();

                if (t.getStatus() == 100) {
                    // 保存TOKEN至本地,
                    LoginTokenBean data = (LoginTokenBean) t.getData();
                    // 保存TOKEN至本地,继续请求userInfo
                    SPStaticUtils.put(SPConstant.AUTHORIZATION, data.getOldToken());
                    SPStaticUtils.put(SPConstant.NEW_TOKEN, data.getNewToken());

                    Logger.e(data.toString());

                    loginModel.UserInfo(new TokenBeanNew(SPStaticUtils.getString(SPConstant.NEW_TOKEN)), new BaseObserver(BaseApplication.instance()) {
                        @Override
                        protected void onSuccess(BaseEntity t) throws Exception {

                            if (t.getStatus() == 100) {
                                SMSLoginBeanNew smsLoginBeanNew = (SMSLoginBeanNew) t.getData();
                                //登录环信
                                HuanXinLogin.huanXinLogin(smsLoginBeanNew);
                                if ("1".equals(smsLoginBeanNew.getIsCompany() + "")) {
                                    SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                                    isCompany = true;
                                } else {
                                    SPStaticUtils.put(SPConstant.ISCOMPANY, "0");
                                    isCompany = false;
                                }
                                //是否VIP
                                SPStaticUtils.put(SPConstant.IS_VIP, smsLoginBeanNew.getIsVIP());
                                LoginModel.saveUserInfoNew(smsLoginBeanNew.getUserId() + "", smsLoginBeanNew.getNickname(), smsLoginBeanNew.getNickname(), smsLoginBeanNew.getAvatar(), smsLoginBeanNew.getPhone());

                                mView.SMSLoginSuccess();
                            } else {
                                mView.SMSLoginFail(t.getMessage());
                            }
                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());
                        }

                    });
                } else {
                    mView.SMSLoginFail(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                LoadingUtil.dissmiss();
                Logger.e(throwable.getMessage());

            }

        });
    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param checkCode
     * @param loginCodeActivity
     */
    public void loginSMS(String phone, String checkCode, LoginCodeActivity loginCodeActivity) {
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("手机号格式不正确!");
            return;
        }
        if (StringUtils.isEmpty(checkCode)) {
            ToastUtils.showShort("验证码不能为空!");
            return;
        }
        LoadingUtil.loadingShow(loginCodeActivity, "登录中...");


        loginModel.loginSMSNew(new SMSLoginSendBeanNew(phone, checkCode), new BaseObserver(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity t) throws Exception {
                LoadingUtil.dissmiss();
                LoginTokenBean loginTokenBean = (LoginTokenBean) t.getData();

                // 保存TOKEN至本地,继续请求userInfo
                SPStaticUtils.put(SPConstant.AUTHORIZATION, loginTokenBean.getOldToken());
                SPStaticUtils.put(SPConstant.NEW_TOKEN, loginTokenBean.getNewToken());

                loginModel.UserInfo(new TokenBeanNew(SPStaticUtils.getString(SPConstant.NEW_TOKEN)), new BaseObserver(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity t) throws Exception {
                        if (t.getStatus() == 100) {
                            SMSLoginBeanNew smsLoginBeanNew = (SMSLoginBeanNew) t.getData();
                            //登录环信
                            HuanXinLogin.huanXinLogin(smsLoginBeanNew);
                            if ("1".equals(smsLoginBeanNew.getIsCompany() + "")) {
                                SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                                isCompany = true;
                            } else {
                                SPStaticUtils.put(SPConstant.ISCOMPANY, "0");
                                isCompany = false;
                            }
                            //是否VIP
                            SPStaticUtils.put(SPConstant.IS_VIP, smsLoginBeanNew.getIsVIP());
                            LoginModel.saveUserInfoNew(smsLoginBeanNew.getUserId() + "", smsLoginBeanNew.getNickname(), smsLoginBeanNew.getNickname(), smsLoginBeanNew.getAvatar(), smsLoginBeanNew.getPhone());
                            mView.SMSLoginSuccess();
                        } else {
                            mView.SMSLoginFail(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Logger.e(throwable.getMessage());
                    }

                });
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                LoadingUtil.dissmiss();
            }
        });
    }
}
