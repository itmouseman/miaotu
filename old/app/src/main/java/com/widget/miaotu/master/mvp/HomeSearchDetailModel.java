package com.widget.miaotu.master.mvp;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeSearchDetailJavaBean;
import com.widget.miaotu.http.bean.head.HeadSearchDetailBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeSearchDetailModel extends MVCModel<HomeSearchDetailControl> {


    public HomeSearchDetailModel(HomeSearchDetailControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void getDetailData(HeadSearchDetailBean headSearchDetailBean) {
        RetrofitFactory.getInstence().API().seedlingSearch(headSearchDetailBean)
                .compose(TransformerUi.<BaseEntity<HomeSearchDetailJavaBean>>setThread())
                .subscribe(new BaseObserver<HomeSearchDetailJavaBean>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<HomeSearchDetailJavaBean> t) throws Exception {
                        Log.e("HYDetailSearch", t.toString());
                        HomeSearchDetailJavaBean homeSearchDetailJavaBean = t.getData();
                        mControl.getDataSuc(homeSearchDetailJavaBean);
                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        Log.e("HYDetailSearcherro", throwable.getMessage());

                    }
                });

    }


}
