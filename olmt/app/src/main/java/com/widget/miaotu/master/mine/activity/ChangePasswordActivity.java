package com.widget.miaotu.master.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.ShapeTextView;
import com.widget.miaotu.common.utils.other.CountDownTimerUtils;
import com.widget.miaotu.common.utils.other.VerificationCountDownTimer;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.ChangePassWordJavaBean;
import com.widget.miaotu.http.bean.SendSMSSendBeanNew;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.tv_change_password_phone)
    TextView tvPhoneNum;
    @BindView(R.id.tv_change_password_getCode)
    TextView tvGetCode;
    @BindView(R.id.stv_change_password_queding)
    ShapeTextView stvQueDing;
    @BindView(R.id.et_change_password_code)
    EditText editTextCode;
    @BindView(R.id.et_change_password_mm1)
    EditText passWord1;
    @BindView(R.id.et_change_password_mm2)
    EditText passWord2;
    private String phone;
    private VerificationCountDownTimer verificationCountDownTimer;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initTopBar();
        phone = getIntent().getStringExtra("phone");
        tvPhoneNum.setText(phoneNumXinHao(phone));


    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    public String phoneNumXinHao(String str) {
        // {}里面的数字可以用来指定前后缀的长度
        String regex = "(\\w{4})(.*)(\\w{4})";
        Matcher m = Pattern.compile(regex).matcher(str);
        if (m.find()) {
            String rep = m.group(2);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rep.length(); i++) {
                sb.append("*");
            }
            return str.replaceAll(rep, sb.toString());
        }
        return "";
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton( ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("修改密码").setTextColor(Color.parseColor("#EBF9FF"));

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取验证码
                getPhonCodeForHttp();
            }
        });

        stvQueDing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确认修改
                sureChange();
            }
        });
    }


    private void sureChange() {
        if ((passWord1.getText().toString().trim().length() >= 6 || passWord1.getText().toString().trim().length() <= 12) && (passWord2.getText().toString().trim().length() >= 6 || passWord2.getText().toString().trim().length() <= 12)) {
            if (editTextCode.getText().toString().trim().length() == 0) {
                ToastUtils.showShort("验证码不能为空!");
                return;
            }
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showShort("请先获取验证码！");
                return;
            }
            if (passWord1.getText().toString().trim().length() == 0 || passWord2.getText().toString().trim().length() == 0) {
                ToastUtils.showShort("密码不能为空!");
                return;
            }
            if (!(passWord1.getText().toString().trim().equals(passWord2.getText().toString().trim()))) {
                ToastUtils.showShort("两次密码输入不一致 !");
                return;
            }


            RetrofitFactory.getInstence().API().modifyPassword(new ChangePassWordJavaBean(  passWord1.getText().toString().trim(), editTextCode.getText().toString().trim()))
                    .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(ChangePasswordActivity.this) {
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

    private void getPhonCodeForHttp() {
        //"0(注册登录)", "1(修改手机号)", "2(修改密码)", "3(重置密码)", "其他待定"
        RetrofitFactory.getInstence().API().getSendSMSNewList(new SendSMSSendBeanNew(phone, 2))
                .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                if (t.getStatus() == 100) {

                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }
}
