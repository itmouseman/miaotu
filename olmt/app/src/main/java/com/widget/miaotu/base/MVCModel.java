package com.widget.miaotu.base;
/**
 *
 * 项目名称：
 * 类名称：MVCModel
 * 类描述：数据基类
 * 修改备注：
 * @version
 *
 */
public abstract class MVCModel<C> {

    public MVCModel(C mControl) {
        this.mControl = mControl;
    }

    protected C mControl;


    /**
     * 释放资源
     */
    public abstract void release();
}
