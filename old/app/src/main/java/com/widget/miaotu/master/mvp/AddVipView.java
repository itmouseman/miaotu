package com.widget.miaotu.master.mvp;

import com.widget.miaotu.base.MVCView;
import com.widget.miaotu.http.bean.PostWxPayBean;

public interface AddVipView extends MVCView {


    void VipOrderVxSuccess(PostWxPayBean data);

    void VipOrderVxFail(String message);
}
