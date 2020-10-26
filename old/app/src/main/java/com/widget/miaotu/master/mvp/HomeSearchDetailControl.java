package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.head.HeadSearchDetailBean;

public class HomeSearchDetailControl extends MVCControl<HomeSearchDetailView> {
    HomeSearchDetailModel model;

    public HomeSearchDetailControl() {
        model = new HomeSearchDetailModel(this);
    }

    @Override
    protected void detatchView() {

    }

    public void getDetailData( HeadSearchDetailBean headSearchDetailBean) {
        model.getDetailData(headSearchDetailBean);
    }



    public void getDataSuc(HomeSearchDetailJavaBean homeSearchDetailJavaBean) {


        mView.getDetailInfoSuc(homeSearchDetailJavaBean);
    }

}
