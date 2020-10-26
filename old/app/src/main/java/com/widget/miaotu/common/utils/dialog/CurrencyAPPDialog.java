package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.widget.miaotu.R;


public class CurrencyAPPDialog extends AppCompatDialog {

    private TextView enter, cancel,title,content;

    public interface OnClickListener {
        void onClick();
    }

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public CurrencyAPPDialog(Context context, int theme, String s1, String s2, String s3, String s4, int color1, int color2) {
        super(context, theme);
        setContentView(R.layout.dialog_currency_app);
        setLayout();
        title = findViewById(R.id.tv_dialog_title);
        content = findViewById(R.id.tv_dialog_content);
        title.setText(s1);
        content.setText(s2);
        cancel = findViewById(R.id.tv_dialog_exit_app_cancel);
        enter = findViewById(R.id.tv_dialog_exit_app_enter);
        enter.setText(s3);
        enter.setTextColor(color1);
        cancel.setText(s4);
        cancel.setTextColor(color2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick();
                dismiss();
            }
        });
        show();
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(null);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
    }
}
