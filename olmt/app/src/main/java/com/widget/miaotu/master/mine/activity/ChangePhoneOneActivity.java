package com.widget.miaotu.master.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseActivity;
import com.widget.miaotu.common.utils.ShapeTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class ChangePhoneOneActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.et_change_phone_num)
    EditText editTextPhone;
    @BindView(R.id.stv_change_phone_one_next)
    ShapeTextView changePhoneNext;

    @Override
    protected void initData() {
        initTopBar();
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return  true;
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mTopBar.setTitle("修改密码").setTextColor(Color.parseColor("#EBF9FF"));


        changePhoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下一步

                String mPhone = editTextPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhone)) {
                    ToastUtils.showShort("手机号码不为空!");
                    return;
                }

                if (isMobileNO(mPhone)) {
                    ToastUtils.showShort("请输入正确的手机号码!");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("mPhone", mPhone);
                intent.setClass(ChangePhoneOneActivity.this, ChangePhoneTwoActivity.class);
                startActivity(intent);
            }
        });
    }

    public static boolean isMobileNO(String mobile) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone_one;
    }
}
