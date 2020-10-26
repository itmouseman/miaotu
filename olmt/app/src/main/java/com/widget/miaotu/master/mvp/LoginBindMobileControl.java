package com.widget.miaotu.master.mvp;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.other.VerificationCountDownTimer;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SendSMSSendBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.other.login.ActivityModifyPassword;

public class LoginBindMobileControl extends MVCControl<LoginBindMobileView> {
    LoginBindMobileModel bindMobileModel;
    public static boolean isCompany=false;

    //倒计时
    public VerificationCountDownTimer verificationCountDownTimer;


    public LoginBindMobileControl( ) {
        this.bindMobileModel = new LoginBindMobileModel(this);
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
     * 绑定手机号发送验证码
     *
     * @param phone
     */
    public void sendSMS2(String phone) {
        //简单验证手机号格式
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("手机号格式不正确!");
            return;
        }

        RetrofitFactory.getInstence().API().getSendSMSNewList(new SendSMSSendBeanNew(phone, 1))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                if (t.getStatus() == 100) {
                    mView.SendSMSSuccess(t.getData().toString());
                } else {
                    mView.SendSMSFail(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("请求出错");
            }
        });


    }
}
