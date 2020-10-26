package com.widget.miaotu.master.mvp;

import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.PostWxPayBean;
import com.widget.miaotu.http.bean.head.HeadPostPayWxBean;
import com.widget.miaotu.http.bean.head.VipOrderSendBean;

public class AddVipControl extends MVCControl<AddVipView> {

    AddVipModel mAddModel;

    public AddVipControl() {
        mAddModel = new AddVipModel(this);
    }

    @Override
    protected void detatchView() {

    }



    public void postPayWx(HeadPostPayWxBean headPostPayWxBean) {
        mAddModel.postPayWx(headPostPayWxBean, new BaseObserver<PostWxPayBean>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<PostWxPayBean> t) throws Exception {
                if (t.getStatus() == 100) {
                    mView.VipOrderVxSuccess(t.getData());
                } else {
                    mView.VipOrderVxFail(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                mView.VipOrderVxFail("网络请求异常");
            }
        });
    }
}
