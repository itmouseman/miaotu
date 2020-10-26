package com.widget.miaotu.common.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.widget.miaotu.R;


public class TakePhotoDialog extends Dialog {

    private TextView takePhoto;

    private TextView gallery;
    private TextView tv_dialog_back;

    private OnTakeClickListener onTakeClickListener;

    public interface OnTakeClickListener {
        void onClick(int type);
    }

    public void setOnTakeClickListener(OnTakeClickListener onTakeClickListener) {
        this.onTakeClickListener = onTakeClickListener;
        show();
    }

    public TakePhotoDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_take_photo);
        setLayout();
        takePhoto = findViewById(R.id.tv_dialog_take_photo);
        gallery = findViewById(R.id.tv_dialog_gallery);
        tv_dialog_back = findViewById(R.id.tv_dialog_back);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakeClickListener.onClick(1);
                dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakeClickListener.onClick(2);
                dismiss();
            }
        });
        tv_dialog_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
