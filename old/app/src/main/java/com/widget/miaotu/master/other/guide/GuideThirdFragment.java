package com.widget.miaotu.master.other.guide;

import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.widget.miaotu.MainActivity;
import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;
import com.widget.miaotu.common.utils.other.FastCilckUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideThirdFragment extends BaseFragment {


    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.btn_begin)
    Button btn_enter;

    private float per = 0.8f;



    public void scaleThirdIn(float scale) {
        if (iv_logo == null) {
            return;
        }
        scale = (1 - per) + scale * per;
        iv_logo.setScaleX(scale);
        iv_logo.setScaleY(scale);
        btn_enter.setScaleX(scale);
        btn_enter.setScaleY(scale);
    }


    public void translate() {
        iv_logo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.splash_y_translate_in));
    }

    @OnClick(R.id.btn_begin)
    public void onClick() {
        //  设置防重复点击
        if (FastCilckUtil.isFastClick()) {
            return;
        }
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    protected void initViewAndData(View view) {
        iv_logo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_logo.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                translate();
            }
        });
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_guide_third;
    }
}
