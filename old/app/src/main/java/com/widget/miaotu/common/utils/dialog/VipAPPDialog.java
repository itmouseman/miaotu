package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.widget.miaotu.R;


public class VipAPPDialog extends AppCompatDialog {

    private TextView enter;
    private ImageView cancel;

    public interface OnClickListener {
        void onClick();
    }

    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public VipAPPDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.dialog_vip);
        setLayout();

        cancel = findViewById(R.id.iv_poster_close);
        enter = findViewById(R.id.tv_dialog_exit_app_enter);

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
