package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.widget.miaotu.R;


public class DeleteBottomPopup extends BottomPopupView {


    private final CallBackDelete callBackDelete;

    private TextView sure;
    private TextView cance;

    public DeleteBottomPopup(@NonNull Context context, CallBackDelete callBackDelete) {
        super(context);
        this.callBackDelete = callBackDelete;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_delete_bottom;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        sure = findViewById(R.id.tv_dialog_delete_bottom_sure);
        cance = findViewById(R.id.tv_dialog_delete_bottom_cance);

        sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBackDelete != null) {
                    dismiss();
                    callBackDelete.deleteSure();
                }
            }
        });
        cance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    public interface CallBackDelete {
        void deleteSure();

    }


}