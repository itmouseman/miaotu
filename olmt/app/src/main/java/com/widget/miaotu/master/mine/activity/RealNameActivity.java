package com.widget.miaotu.master.mine.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.AndroidUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.AuthSubmitBean;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import butterknife.BindView;

public class RealNameActivity extends MBaseActivity {

    @BindView(R.id.image_first)
    ImageView imageFirst;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.image_second)
    ImageView imageSecond;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.image_third)
    ImageView imageThird;
    @BindView(R.id.tv_third)
    TextView tvThird;
    @BindView(R.id.et_input_name)
    EditText etInputName;
    @BindView(R.id.et_input_identity)
    EditText etInputIdentity;
    @BindView(R.id.btn_first_confirm)
    Button btnFirstConfirm;

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @Override
    protected MVCControl createControl() {
        return null;
    }


    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected void initView() {

        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton( ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("实名认证").setTextColor(Color.parseColor("#EBF9FF"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_me_real_name;
    }

    @Override
    protected void initDetailData() {
        btnFirstConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交实名认证信息
                if (AndroidUtils.isNullOrEmpty(etInputName.getText().toString())) {
                    AndroidUtils.Toast(RealNameActivity.this, "真实姓名不能为空");
                    return;
                }
                if (AndroidUtils.isNullOrEmpty(etInputIdentity.getText().toString())) {
                    AndroidUtils.Toast(RealNameActivity.this, "身份证信息不能为空");
                    return;
                }
                if (!AndroidUtils.personIdValidation(etInputIdentity.getText().toString())) {
                    AndroidUtils.Toast(RealNameActivity.this, "您输入的身份号有误，请检查");
                    return;
                }
//                AndroidUtils.Toast(RealNameActivity.this,"接口暂未开发");
                showWaiteDialog("正在加载...");
                RetrofitFactory.getInstence().API().userAuthSubmit(new AuthSubmitBean(etInputName.getText().toString(), etInputIdentity.getText().toString()))
                        .compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(RealNameActivity.this) {
                    @Override
                    protected void onSuccess(BaseEntity<Object> t) throws Exception {
                        hideWaiteDialog();
                        if (t.getStatus() == 100) {
                            ToastUtils.showShort("实名信息已提交！");
                            SPStaticUtils.put(SPConstant.ISAUTH, "0");
                            finish();
                        } else {
                          ToastUtils.showShort(t.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        ToastUtils.showShort("实名信息失败！");
                    }
                });

            }
        });
    }
}
