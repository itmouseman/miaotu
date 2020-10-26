package com.widget.miaotu.common.utils.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.widget.miaotu.R;


import java.util.ArrayList;


public class SharePopup extends BottomPopupView {


    private final CallBackShareType callBackShareType;
    private LinearLayout shareWx;
    private LinearLayout sharePyq;
    private TextView shareBack;

    public SharePopup(@NonNull Context context,CallBackShareType callBackShareType) {
        super(context);
        this.callBackShareType =callBackShareType;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_share_bottom;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        shareWx = findViewById(R.id.ll_share_bottom_wx);
        sharePyq = findViewById(R.id.ll_share_bottom_pyq);
        shareBack = findViewById(R.id.tv_share_bottom_back);
        shareWx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callBackShareType!=null){
                    callBackShareType.shareWeiXin();
                }
            }
        });
        sharePyq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBackShareType!=null){
                    callBackShareType.sharePengYouQuan();
                }
            }
        });
        shareBack.setOnClickListener(new OnClickListener() {
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

    public interface CallBackShareType {
        void shareWeiXin();

        void sharePengYouQuan();
    }


}