package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;

import java.util.List;

public class HomeSearchControl extends MVCControl<HomeSearchView> {
    HomeSearchModel mModel;


    public HomeSearchControl() {
        this.mModel = new HomeSearchModel(this);
    }

    public void getHostSearchData(String tempName,int pageNum,int pageSize) {
        mModel.getHistoryData(tempName,"seedling",pageNum,pageSize);
    }

    @Override
    protected void detatchView() {

    }

    public void getHostSearchDataSuc(List<HomeSearchJavaBean> homeSearchJavaBean) {
        mView.getHostSearchDataSuc(homeSearchJavaBean);
    }

    public void getHostSearchDataFail(String message) {
        mView.getHostSearchDataFail(message);
    }
}
