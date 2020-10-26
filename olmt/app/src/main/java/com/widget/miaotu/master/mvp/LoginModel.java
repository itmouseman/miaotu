package com.widget.miaotu.master.mvp;

import com.blankj.utilcode.util.SPStaticUtils;
import com.hyphenate.easeui.cache.UserCacheManager;
import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.LoginTokenBean;
import com.widget.miaotu.http.bean.PhoneLoginSendNewBean;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.SMSLoginSendBeanNew;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

public class LoginModel extends MVCModel<LoginControl> {

    public LoginModel(LoginControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void loginPhoneNew(PhoneLoginSendNewBean bean, BaseObserver baseObserver) {
        RetrofitFactory.getInstence().API().getPhoneLoginNewList(bean)
                .compose(TransformerUi.<BaseEntity<LoginTokenBean>>setThread())
                .subscribe(baseObserver);
    }

    public void UserInfo(TokenBeanNew tokenBeanNew, BaseObserver baseObserver) {
        RetrofitFactory.getInstence().API().getUserInfo(tokenBeanNew)
                .compose(TransformerUi.<BaseEntity<SMSLoginBeanNew>>setThread())
                .subscribe(baseObserver);
    }

    public void loginSMSNew(SMSLoginSendBeanNew smsLoginSendBeanNew, BaseObserver baseObserver) {
        RetrofitFactory.getInstence().API().getSMSLoginNewList(smsLoginSendBeanNew)
                .compose(TransformerUi.<BaseEntity<LoginTokenBean>>setThread())
                .subscribe(baseObserver);
    }


    /**
     * 保存5.0用户基础信息
     */
    public static void saveUserInfoNew(String userId, String userName, String nickname,
                                       String avatar, String mobile) {

        // 登录成功，将当前用户的环信ID、备注、昵称和头像、性别保存在本地
        UserCacheManager.save(userId, userName, avatar);


        //保存用户ID
        SPStaticUtils.put(SPConstant.USER_ID, userId);
        //保存用户名
        SPStaticUtils.put(SPConstant.USER_NAME, userName);
        //联系方式
        SPStaticUtils.put(SPConstant.MOBILE, mobile);
        //保存昵称
        SPStaticUtils.put(SPConstant.NICK_NAME, nickname);
        //头像
        SPStaticUtils.put(SPConstant.AVATAR, avatar);
        //获取是否有企业
        SPStaticUtils.put(SPConstant.ISCOMPANY, "0");

//        //是否VIP
//        SPStaticUtils.put(SPConstant.IS_VIP, isVip);
//        //公司
//        SPStaticUtils.put(SPConstant.POSITION, position);
//        //职位
//        SPStaticUtils.put(SPConstant.POSITION_TYPE, positionType);
//        //邀请码
//        SPStaticUtils.put(SPConstant.INVITE_CODE, invite_code);


        //地址
        SPStaticUtils.put(SPConstant.ADDRESS, "");
        //环信账号
        SPStaticUtils.put(SPConstant.HX_USER_NAME, "");
        //环信密码
        SPStaticUtils.put(SPConstant.HX_PASS_WORD, "");
        //邮箱
        SPStaticUtils.put(SPConstant.EMAIL, "");
        //职位类型ID
        SPStaticUtils.put(SPConstant.POSITION_TYPE_ID, "");
        //头衔标签
        SPStaticUtils.put(SPConstant.TITLE_LABEL, "");
        //所属部门
        SPStaticUtils.put(SPConstant.DEPARTMENT, "");

    }


}
