package com.widget.miaotu.master.other.login;

import com.bytedance.sdk.account.common.utils.Md5Utils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;

public class HuanXinLogin {

    public static void huanXinLogin(SMSLoginBeanNew smsLoginBeanNew) {
        //data.userId.toString()
//        EMClient.getInstance().login(73281, MD5Util.md5Encode(data.userId.toString()), )


        EMClient.getInstance().login(smsLoginBeanNew.getUserId(), Md5Utils.hexDigest(smsLoginBeanNew.getUserId()), new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.e("环信登录成功");
            }

            @Override
            public void onError(int i, String s) {
                Logger.e("环信登录失败" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
