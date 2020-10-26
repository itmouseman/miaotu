package com.widget.miaotu.master.mvp;

import com.blankj.utilcode.util.SPStaticUtils;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.SaveGardenBean;

public class BaseInfoDemandControl extends MVCControl<BaseInfoDemandView> {


    BaseInfoDemandModel mModel;

    public BaseInfoDemandControl( ) {
       mModel = new BaseInfoDemandModel(this);
    }

    @Override
    protected void detatchView() {

    }

    public void addTechInfoNew(SaveGardenBean bean) {


        mModel.addTechInfoNew(bean, new BaseObserver<Object>(BaseApplication.instance()) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {

                if (t.getStatus()==100){
                    //获取是否有企业
                    SPStaticUtils.put(SPConstant.ISCOMPANY, "1");
                    mView.addTechInfoSuccess();
                }else {
                    mView.addTechInfoFail(t.getMessage());
                }
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                    mView.addTechInfoFail("网络错误");
            }
        });
    }
}
