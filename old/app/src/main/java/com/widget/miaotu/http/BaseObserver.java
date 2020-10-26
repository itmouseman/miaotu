package com.widget.miaotu.http;

import android.content.Context;


import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.IntentUtils;
import com.widget.miaotu.http.bean.BaseEntity;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.master.other.login.LoginCodeActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jbwl on 2018/10/10 18:18.
 */

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    protected Context mContext;

    public BaseObserver(Context context) {
        mContext = context;
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {

//        Logger.e(tBaseEntity.toString() + "");
        try {
            if (tBaseEntity.getStatus() == 20011 || tBaseEntity.getStatus() == 20016) {//登录过期等等
                onError(new Throwable("20011"));
            } else {
                onSuccess(tBaseEntity);
            }
        } catch (Exception e) {
            Logger.e(e.toString() + "");
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            onFail(e);
            String s = e.toString();
            boolean status = s.contains("20011");
            Logger.e(status + "");
            if (status) {
                if (mContext != null) {
                    //登录过期
                    SPStaticUtils.put(SPConstant.AUTHORIZATION, "");
                    SPStaticUtils.put(SPConstant.NEW_TOKEN, "");
                    IntentUtils.startIntent(mContext, LoginCodeActivity.class);
                }

            } else {
                Logger.e(s);
//                ToastUtil.showShort("请求异常");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(BaseEntity<T> t) throws Exception;

    protected abstract void onFail(Throwable throwable) throws Exception;
}
