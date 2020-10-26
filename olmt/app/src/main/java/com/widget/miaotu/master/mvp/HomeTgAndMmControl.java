package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;

public class HomeTgAndMmControl extends MVCControl<HomeTgAndMmView> {

    HomeTgAndMmModel mModel;

    public HomeTgAndMmControl() {
        mModel = new HomeTgAndMmModel(this);
    }

    @Override
    protected void detatchView() {
        mModel = new HomeTgAndMmModel(this);
    }

    public void getDataHomeTgAndMmDetail(String idMiaoMu) {
        mModel.getDataHomeTgAndMmDetail(idMiaoMu);
    }

    public void getDataHomeTgAndMmDetailSuc(SeedlingDetailJavaBean seedlingDetailJavaBean) {
        mView.getDataHomeTgAndMmDetailSuc(seedlingDetailJavaBean);
    }
}
