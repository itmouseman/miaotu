package com.widget.miaotu.master.mvp;

import android.util.Log;

import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeFenLeiJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import java.util.List;

public class HomeFenLeiModel extends MVCModel<HomeFenLeiConrrol> {

    public HomeFenLeiModel(HomeFenLeiConrrol mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void getDataForHttp() {
        RetrofitFactory.getInstence().API().getSeedlingClassifyFirst()
                .compose(TransformerUi.<BaseEntity<List<HomeFenLeiJavaBean>>>setThread())
                .subscribe(new BaseObserver<List<HomeFenLeiJavaBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomeFenLeiJavaBean>> t) throws Exception {

                        if (t.getStatus() == 100) {
                            List<HomeFenLeiJavaBean> homeFenLeiJavaBean = t.getData();
                            mControl.dataSuc(homeFenLeiJavaBean);
                        } else {
                            mControl.dataFail(t.getMessage());
                        }

                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        mControl.dataFail(throwable.getMessage());
                    }
                });
    }
}
