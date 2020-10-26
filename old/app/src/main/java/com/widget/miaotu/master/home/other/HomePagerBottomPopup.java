package com.widget.miaotu.master.home.other;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.widget.miaotu.R;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.common.utils.ToastUtil;
import com.widget.miaotu.master.home.activity.AddMiaoMuActivity;
import com.widget.miaotu.master.other.login.LoginCodeActivity;


;


/**
 * Description: 自定义带有ViewPager的Bottom弹窗
 * Create by dance, at 2019/5/5
 */
public class HomePagerBottomPopup extends BottomPopupView {
    private final BottomPopupClickCallBack mBottomPopupClickCallBack;

    public HomePagerBottomPopup(@NonNull Context context, BottomPopupClickCallBack bottomPopupClickCallBack) {
        super(context);
        mBottomPopupClickCallBack = bottomPopupClickCallBack;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.home_pager_bottompop;
    }


    @Override
    protected void onCreate() {
        super.onCreate();

        //添加苗木
        findViewById(R.id.ll_home_tianjiamiaomu).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomPopupClickCallBack != null) {
                    mBottomPopupClickCallBack.homeTianJiaMiaoMu(view);
                }

                dismiss();
            }
        });
        //发布求购
        findViewById(R.id.ll_home_fabuqiugou).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomPopupClickCallBack != null) {
                    mBottomPopupClickCallBack.homeFaBuQiuGou(view);
                }

               dismiss();
            }
        });
        findViewById(R.id.iv_home_fabuquxiao).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }


    public interface BottomPopupClickCallBack {
        void homeTianJiaMiaoMu(View view);

        void homeFaBuQiuGou(View view);
    }

}
