package com.widget.miaotu.common.utils;

import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.LoginTokenBean;
import com.widget.miaotu.http.bean.SMSLoginBeanNew;
import com.widget.miaotu.http.bean.TokenBeanNew;
import com.widget.miaotu.http.bean.WeXinLoginJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mvp.LoginModel;
import com.widget.miaotu.master.other.login.BindMobileActivity;
import com.widget.miaotu.master.other.login.HuanXinLogin;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;

/**
 * Created by jbwl on 2018/10/16 12:08.
 */

public class ThirdLoginUtil {
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_AUTH_COMPLETE = 5;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_CANCEL = 3;
    private int openType;
    private String wxOpenId;
    private String weiboOpenId;
    private String qqOpendId;
    private Activity mcontext;


    public ThirdLoginUtil(Activity context) {
        mcontext = context;
    }

    /**
     * 加载框
     */
//    public void buildProgressDialog(String msg) {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(mCon);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        }
//        progressDialog.setMessage(msg);
//        progressDialog.setCancelable(true);
//        progressDialog.show();
//    }
//
//    public void cancelProgressDialog() {
//        if (progressDialog != null)
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//    }

    /**
     * 第三方授权
     *
     * @param plat
     */

    public void thirdPartAuthorize(final Platform plat) {

        if (plat == null) {
            return;
        }
        if (plat.isAuthValid()) {//如果授权有效
            plat.removeAccount(true);
        }
        plat.SSOSetting(false);//默认是客户端授权
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                Logger.e("微信登录回调成功");
                PlatformDb db = platform.getDb();
                if (db != null) {
                    String wxId = db.getUserId();
                    String userIcon = db.getUserIcon();
                    String userName = db.getUserName();
                    //用户授权成功之后取调用后台登录接口
                    thirdPartLogin(userIcon, userName, plat.getName(), wxId);
                }
            }

            @Override
            public void onError(Platform platform, int action, Throwable throwable) {

                ToastUtils.showShort("微信登录失败");
                Logger.e("微信登录回调失败");

            }

            @Override
            public void onCancel(Platform platform, int action) {
                ToastUtils.showShort("微信登录取消");

            }
        });
        plat.showUser(null);//必须要加的要不然不行！这个才是授权的！
    }

    /**
     * 第三方登录
     */

    public void thirdPartLogin(final String userIcon, final String userName, String plat, final String wxId) {
        mcontext.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RetrofitFactory.getInstence().API().wxLogin(new WeXinLoginJavaBean(wxId)).compose(TransformerUi.setThread()).subscribe(new BaseObserver<LoginTokenBean>(mcontext) {
                    @Override
                    protected void onSuccess(BaseEntity<LoginTokenBean> t) throws Exception {
                        Logger.e("微信登录成功啦！"+t.toString());
                        if (t.getStatus() == 100) {
                            // 保存TOKEN至本地,继续请求userInfo
                            LoginTokenBean loginTokenBean = t.getData();
                            SPStaticUtils.put(SPConstant.AUTHORIZATION, loginTokenBean.getOldToken());
                            SPStaticUtils.put(SPConstant.NEW_TOKEN, loginTokenBean.getNewToken());
                            RetrofitFactory.getInstence().API()
                                    .getUserInfo(new TokenBeanNew(loginTokenBean.getNewToken()))
                                    .compose(TransformerUi.setThread())
                                    .subscribe(new BaseObserver<SMSLoginBeanNew>(mcontext) {
                                        @Override
                                        protected void onSuccess(BaseEntity<SMSLoginBeanNew> t) throws Exception {
                                            Logger.e(t.getData().toString());
                                            if (t.getStatus() == 100) {
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

                                                //刷新我的页面
                                                RxBus.getInstance().post(new HomeUpdateChange(false));
                                                mcontext.finish();
                                            } else {

                                                ToastUtils.showShort(t.getMessage());
                                            }

                                        }

                                        @Override
                                        protected void onFail(Throwable throwable) throws Exception {
                                            Logger.e(t.getMessage());
                                        }
                                    });
                        } else if (t.getStatus() == 10016){
                            // 绑定手机号
                            Intent intent = new Intent();
                            intent.putExtra("wxId", wxId);
                            intent.putExtra("type", "1");
                            intent.setClass(mcontext, BindMobileActivity.class);
                            mcontext.startActivity(intent);
                            mcontext.finish();
                        }else {
                            ToastUtils.showShort(t.getMessage());
                        }

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        ToastUtils.showLong("微信登录出错");
                    }
                });


            }
        });


    }


}
