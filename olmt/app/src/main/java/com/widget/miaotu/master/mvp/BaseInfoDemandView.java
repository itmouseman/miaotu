package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;

public interface BaseInfoDemandView extends MVCView {
    void addTechInfoSuccess();

    void addTechInfoFail(String throwable);
}
