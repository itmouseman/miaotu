package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.PostWxPayBean;
import com.widget.miaotu.http.bean.head.HeadPostPayWxBean;
import com.widget.miaotu.http.bean.head.VipOrderSendBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

public class AddVipModel extends MVCModel<AddVipControl> {

    public AddVipModel(AddVipControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }



    public void postPayWx(HeadPostPayWxBean headPostPayWxBean, BaseObserver<PostWxPayBean> postWxPayBeanBaseObserver) {
        RetrofitFactory.getInstence().API().postPayWx(headPostPayWxBean).subscribe(postWxPayBeanBaseObserver);
    }
}
