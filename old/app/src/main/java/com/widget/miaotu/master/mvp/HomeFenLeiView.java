package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;
import com.widget.miaotu.http.bean.HomeFenLeiJavaBean;

import java.util.List;

public interface HomeFenLeiView  extends MVCView {

    void getDataSuc(List<HomeFenLeiJavaBean> homeFenLeiJavaBean);

    void getDataFail(String message);
}
