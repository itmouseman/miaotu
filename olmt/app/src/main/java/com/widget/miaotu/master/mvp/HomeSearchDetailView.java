package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;

public interface HomeSearchDetailView extends MVCView {

    void getDetailInfoSuc(HomeSearchDetailJavaBean homeSearchDetailJavaBean);

    void getDataFail(String message);
}
