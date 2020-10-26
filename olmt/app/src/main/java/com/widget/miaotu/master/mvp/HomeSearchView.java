package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;

import java.util.List;

public interface HomeSearchView  extends MVCView {

    void getHostSearchDataSuc(List<HomeSearchJavaBean> homeSearchJavaBean);

    void getHostSearchDataFail(String message);
}
