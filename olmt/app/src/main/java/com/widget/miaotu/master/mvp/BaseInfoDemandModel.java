package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.SaveGardenBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

public class BaseInfoDemandModel extends MVCModel<BaseInfoDemandControl> {

    public BaseInfoDemandModel(BaseInfoDemandControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void addTechInfoNew(SaveGardenBean bean, BaseObserver<Object> objectBaseObserver) {
        RetrofitFactory.getInstence().API().saveGarden(bean).compose(TransformerUi.setThread()).subscribe(objectBaseObserver);
    }
}
