package com.widget.miaotu.base;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

import com.widget.miaotu.common.constant.IBaseActivity;
import com.widget.miaotu.common.utils.other.CommonUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.mob.MobSDK.getContext;

public abstract class BaseActivity extends BaseAbsActivity  implements IBaseActivity {


    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    @Override
    protected void initCData() {

        initData();
    }

    protected abstract void initData();

    @Override
    protected void initCView() {
        initView();
    }

    protected abstract void    initView();


    @Override
    protected abstract int getLayoutId();


    @Override
    public int getResourceColor(@ColorRes int colorId) {
        return ResourcesCompat.getColor(getResources(), colorId, null);
    }

    @Override
    public String getResourceString(@StringRes int stringId) {
        return getResources().getString(stringId);
    }

    @Override
    public String getResourceString(@StringRes int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    @Override
    public Drawable getResourceDrawable(@DrawableRes int id) {
        return ResourcesCompat.getDrawable(getResources(), id, null);
    }

    /**
     * 隐藏输入法
     */
    public void hideInput() {
        View view = getWindow().peekDecorView();
        CommonUtil.hideSoftInput(getContext(), view);
    }


    /**
     * 启用硬件加速
     */
    public void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }
}
