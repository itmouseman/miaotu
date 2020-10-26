package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;

public interface HomeTgAndMmView extends MVCView {
    void getDataHomeTgAndMmDetailSuc(SeedlingDetailJavaBean seedlingDetailJavaBean);
}
