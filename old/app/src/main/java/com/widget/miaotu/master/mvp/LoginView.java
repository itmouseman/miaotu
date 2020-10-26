package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;

public interface LoginView extends MVCView {

//    void SMSLoginSuccess(SMSLoginBean data);

    void SMSLoginSuccess();

    void SMSLoginFail(String desc);


//    void PhoneLoginSuccess(PhoneLoginBean data);

    void PhoneLoginFail(String desc);


    void SendSMSSuccess(String data);

    void SendSMSFail(String desc);

    void onTick(long millisUntilFinished);

    void onTimeFinish();

}
