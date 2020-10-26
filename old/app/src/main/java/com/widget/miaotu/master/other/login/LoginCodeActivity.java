package com.widget.miaotu.master.other.login;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.CommonWebViewActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.StatusBarUtil;
import com.widget.miaotu.common.utils.ThirdLoginUtil;
import com.widget.miaotu.common.utils.other.FastCilckUtil;
import com.widget.miaotu.common.utils.other.LoadingUtil;
import com.widget.miaotu.common.utils.rxbus.HomeUpdateChange;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.master.mvp.LoginControl;
import com.widget.miaotu.master.mvp.LoginView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static com.mob.MobSDK.getContext;

/**
 * 手机验证码登录
 */

public class LoginCodeActivity extends MBaseActivity<LoginControl> implements LoginView {


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
    /**
     * 标题
     */
    @BindView(R.id.tv_login_title)
    TextView loginTitle;
    /**
     * 登录类型
     */
    @BindView(R.id.tv_login_type)
    TextView tvLoginType;
    @BindView(R.id.rb_price)
    CheckBox rbPrice;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.image_login_type)
    ImageView imageLoginType;

    /**
     * 登录模式(1.验证码登录2.手机登录)
     */
    private int loginType = LOGIN_SMS;

    /**
     * 验证码登录
     */
    private final static int LOGIN_SMS = 1;

    /**
     * 手机号登录
     */
    private final static int LOGIN_PHONE = 2;


    @Override
    protected LoginControl createControl() {
        return new LoginControl();
    }

    @Override
    protected void initView() {
        StatusBarUtil.fullScreen(this);

    }

    @Override
    protected void initDetailData() {

        mControl.initCountDownTimer();
    }


    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_login_forget_pwd, R.id.bt_login, R.id.tv_login_get_check_code, R.id.ll_login,
            R.id.iv_login, R.id.tv_fwxy, R.id.ll_wechat})
    public void onClick(View view) {
        //  手机号登录
        String phone = etLoginPhone.getText().toString().trim();
        String password = etLoginCheckCode.getText().toString().trim();
        switch (view.getId()) {
            //  忘记密码
            case R.id.tv_login_forget_pwd:
                if (AndroidUtils.isNullOrEmpty(phone)) {
                    AndroidUtils.Toast(getContext(), "请先输入手机号");
                    return;
                }
                if (phone.length() < 11) {
                    AndroidUtils.Toast(getContext(), "请输入正确的手机号");
                    return;
                }
                Intent intent5 = new Intent();
                intent5.putExtra("phone", phone);
                intent5.setClass(this, ActivityModifyPassword.class);
                startActivity(intent5);

                finish();
                break;
            //  登录
            case R.id.bt_login:
                //  设置防重复点击
                if (FastCilckUtil.isFastClick()) {
                    return;
                }
                if (!rbPrice.isChecked()) {
                    AndroidUtils.Toast(getContext(), "请先同意用户服务协议");
                    return;
                }
                //  密码登录
                if (loginType == LOGIN_PHONE) {
                    mControl.loginPhone(this, phone, password);
                    return;
                }
                //  验证码登录
                if (loginType == LOGIN_SMS) {
                    mControl.loginSMS(phone, password, LoginCodeActivity.this);
                    return;
                }
                break;
            //  验证码获取
            case R.id.tv_login_get_check_code:
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showShort("手机号格式不正确!");
                    return;
                }
                LoadingUtil.loadingShow(this, "获取验证码中...");

                mControl.sendSMS(phone, this);
                break;
            //  设置登录模式
            case R.id.ll_login:
                setLoginType();
                break;
            case R.id.iv_login:
                finish();
                break;
            case R.id.tv_fwxy:
                //加入会员
                String[] key = {SPConstant.WEB_URL, "用户注册协议"};
                String[] value = {ApiService.REGISTER_AGREEMENT, SPConstant.WEB_TITLE};
                IntentUtils.startIntent(LoginCodeActivity.this, CommonWebViewActivity.class, key, value);

                break;
            case R.id.ll_wechat:
                new ThirdLoginUtil(this).thirdPartAuthorize(ShareSDK.getPlatform(Wechat.NAME));

                break;
            default:
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_code;
    }


    /**
     * 设置登录模式
     */
    private void setLoginType() {
        etLoginCheckCode.setText("");
        if (loginType == LOGIN_SMS) {
            loginType = LOGIN_PHONE;
            tvLoginForgetPwd.setVisibility(View.VISIBLE);
            etLoginCheckCode.setHint("密码");
            loginGetCheckCode.setVisibility(View.GONE);
            etLoginCheckCode.setHint("密码登录");
            etLoginCheckCode.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            loginTitle.setText("密码登录");
            tvLoginType.setText("验证码登录");
            imageLoginType.setImageResource(R.mipmap.ic_login_code);
        } else if (loginType == LOGIN_PHONE) {
            loginType = LOGIN_SMS;
            tvLoginForgetPwd.setVisibility(View.INVISIBLE);
            etLoginCheckCode.setHint("验证码");
            loginGetCheckCode.setVisibility(View.VISIBLE);
            etLoginCheckCode.setHint("验证码登录");
            etLoginCheckCode.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginTitle.setText("手机验证码登录");
            tvLoginType.setText("密码登录");
            imageLoginType.setImageResource(R.mipmap.ic_login_password);
        }

    }

    @Override
    public void SMSLoginSuccess() {
        LoadingUtil.loadingClose(true, "登录成功!");

        RxBus.getInstance().post(new HomeUpdateChange(true));

        finish();
    }

    @Override
    public void SMSLoginFail(String desc) {
        ToastUtils.showShort(desc);
    }

    @Override
    public void PhoneLoginFail(String desc) {

    }

    @Override
    public void SendSMSSuccess(String data) {
        LoadingUtil.loadingClose(true, "验证码发送成功!");
        //开启验证码倒计时
        mControl.verificationCountDownTimer.timerStart(true);
    }

    @Override
    public void SendSMSFail(String desc) {
        LoadingUtil.dissmiss();
    }


    /**
     * 验证码倒计时回调
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        if (loginGetCheckCode!=null){
            loginGetCheckCode.setEnabled(false);
            loginGetCheckCode.setText((millisUntilFinished / 1000) + "S");
        }

    }

    /**
     * 设置验证码倒计时结束操作
     */
    @Override
    public void onTimeFinish() {
        if (loginGetCheckCode != null) {
            loginGetCheckCode.setEnabled(true);
            loginGetCheckCode.setText("重新获取");
        }
    }
}