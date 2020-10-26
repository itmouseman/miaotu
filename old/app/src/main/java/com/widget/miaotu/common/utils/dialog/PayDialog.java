package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.widget.miaotu.R;

/**
 * 支付开通
 */
public class PayDialog extends AppCompatDialog {
    TextView tvPrice, enter;
    RadioGroup radioGroup;

    public int checkPay = 1;

    public interface OnCheckPayListener {
        void onCheckPay(int type);
    }

    public OnCheckPayListener onCheckPayListener;

    public void setOnCheckPayListener(OnCheckPayListener onCheckPayListener) {
        this.onCheckPayListener = onCheckPayListener;
        show();
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_pay);
        setLayout();
        tvPrice = findViewById(R.id.tv_pay_dialog_price);
        enter = findViewById(R.id.tv_pay_dialog_enter);
        radioGroup = findViewById(R.id.rg_pay_dialog);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_we_chat_pay) {
                    checkPay = 2;
                } else {
                    checkPay = 1;
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckPayListener.onCheckPay(checkPay);
                dismiss();
            }
        });
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(null);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
    }

    public PayDialog setPrice(String price) {
        if (price == null) {
            if (price.trim().length() == 0) {
                price = "数据异常";
            }
        }
        tvPrice.setText("¥:" + price);
        return this;
    }

}
