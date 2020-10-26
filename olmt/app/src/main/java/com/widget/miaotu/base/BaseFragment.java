package com.widget.miaotu.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.StringUtils;
import com.widget.miaotu.BaseApplication;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.widget.miaotu.common.constant.SPConstant;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;
    private Activity mainActivity;
    private Unbinder bind;
    private KProgressHUD mWaiteDialog;

    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    protected void showWaiteDialog(String msg) {
        if (mWaiteDialog == null) {
            mWaiteDialog = KProgressHUD.create(mainActivity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f).setCancellable(false);
        }
        mWaiteDialog.setLabel(msg).show();
    }

    protected void hideWaiteDialog() {
        if (mWaiteDialog != null) {
            mWaiteDialog.dismiss();
        }
    }


    public Context getFragmentContext() {
        if (mainActivity == null) {
            return BaseApplication.instance();
        }
        return mainActivity;
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof Activity) {
            mainActivity = (Activity) getActivity();
        }
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getFragmentId(), container, false);
            bind = ButterKnife.bind(this, rootView);
            if (isAdded()) {
                initViewAndData(rootView);
            }

        }
        return rootView;
    }

    protected abstract void initViewAndData(View view);

    protected abstract int getFragmentId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (rootView != null) {//为rootView做缓存，在viewpager中使用fragment时可以提升切换流畅度
//            ((ViewGroup) rootView.getParent()).removeView(rootView);
//        }
    }


    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {

    }

    /**
     * 判断用户是否登录
     */
    public boolean isLogin() {

        return !StringUtils.isEmpty(SPStaticUtils.getString(SPConstant.USER_ID));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != Unbinder.EMPTY && bind != null) {
            bind.unbind();
        }
    }


}
