package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;

public interface LoginBindMobileView  extends MVCView {


    void SMSLoginSuccess();

    void SMSLoginFail(String desc);




    void PhoneLoginFail(String desc);


    void SendSMSSuccess(String data);

    void SendSMSFail(String desc);

    void onTick(long millisUntilFinished);

    void onTimeFinish();
}
