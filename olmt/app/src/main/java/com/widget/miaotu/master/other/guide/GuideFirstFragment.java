package com.widget.miaotu.master.other.guide;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.widget.miaotu.R;
import com.widget.miaotu.base.BaseFragment;

import butterknife.BindView;

public class GuideFirstFragment extends BaseFragment {


    @BindView(R.id.iv_logo)
    ImageView iv_logo;

    @BindView(R.id.tv_des1)
    TextView tv_des1;

    @BindView(R.id.tv_des2)
    TextView tv_des2;

    @BindView(R.id.tv_des3)
    TextView tv_des3;

    private float per = 0.8f;




    public void scaleFirst(float scale) {
        if (iv_logo == null || tv_des1 == null || tv_des2 == null || tv_des3 == null) {
            return;
        }
        scale = (1 - scale * per);

        iv_logo.setScaleX(scale);
        iv_logo.setScaleY(scale);
        tv_des1.setScaleX(scale);
        tv_des1.setScaleY(scale);
        tv_des2.setScaleX(scale);
        tv_des2.setScaleY(scale);
        tv_des3.setScaleX(scale);
        tv_des3.setScaleY(scale);
    }


    public void translate() {
        iv_logo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.splash_y_translate_in));
        tv_des1.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.splash_x_translate_in));
        tv_des2.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.splash_x_translate_in));
        tv_des3.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.splash_x_translate_in));
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
        return R.layout.fragment_guide_first;
    }
}
