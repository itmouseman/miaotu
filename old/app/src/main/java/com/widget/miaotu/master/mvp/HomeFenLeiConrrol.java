package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.bean.HomeFenLeiJavaBean;

import java.util.List;

public class HomeFenLeiConrrol extends MVCControl<HomeFenLeiView> {

    HomeFenLeiModel mModel;

    public HomeFenLeiConrrol() {

        mModel = new HomeFenLeiModel(this);

    }

    @Override
    protected void detatchView() {
        if (mModel != null) {
            mModel.release();
        }
    }

    public void getData() {

        mModel.getDataForHttp();

    }

    public void dataSuc(List<HomeFenLeiJavaBean> homeFenLeiJavaBean) {

        mView.getDataSuc(homeFenLeiJavaBean);
    }

    public void dataFail(String message) {
        mView.getDataFail(message);
    }
}
