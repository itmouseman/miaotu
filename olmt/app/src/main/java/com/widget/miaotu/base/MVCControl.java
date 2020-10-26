package com.widget.miaotu.base;

/**
 *
 * 项目名称：
 * 类名称：MVCControl
 * 类描述：控制器
 * 修改备注：
 * @version
 *
 */
public abstract class MVCControl<V> {

    protected V mView;

    /**
     * 界面创建，Presenter与界面取得联系
     */
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 销毁界面
     */
    protected abstract void detatchView();

    /**
     * 判断界面是否销毁
     */
    protected Boolean isViewAttached() {
        if (mView == null) {
            return true;
        }
        return false;
    }


}