package com.widget.miaotu.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * 项目名称：
 * 类名称：HttpMVCModel
 * 类描述：heyang
 * 创建时间：2019/3/24 16:36
 * 修改人：heyang
 * 修改时间：2019/3/24 16:36
 * 修改备注：
 * @version
 *
 */
public abstract class HttpMVCModel<C> extends MVCModel<C> {
    private CompositeDisposable mCompositeDisposable  = new CompositeDisposable();
    public HttpMVCModel(C mControl) {
        super(mControl);
    }

    @Override
    public void release() {
        //关闭所有订阅
        mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
    }

    public void addSubscribe(Disposable sb){
        mCompositeDisposable.add(sb);
    }


}