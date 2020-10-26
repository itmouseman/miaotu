package com.widget.miaotu.master.mvp;

import android.util.Log;

import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;
import com.widget.miaotu.http.bean.head.HeadSearchThinkBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeSearchModel extends MVCModel<HomeSearchControl> {
    public HomeSearchModel(HomeSearchControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void getHistoryData(String tempName, String type, int pageNum, int pageSize) {


        RetrofitFactory.getInstence().API().getSearch(new HeadSearchThinkBean(tempName,type,pageNum,pageSize))
                .compose(TransformerUi.<BaseEntity<List<HomeSearchJavaBean>>>setThread())
                .subscribe(new BaseObserver<List<HomeSearchJavaBean>>(BaseApplication.instance()) {
                    @Override
                    protected void onSuccess(BaseEntity<List<HomeSearchJavaBean>> t) throws Exception {
                        if (t.getStatus()==100){
                            List<HomeSearchJavaBean> homeSearchJavaBean = t.getData();
                            mControl.getHostSearchDataSuc(homeSearchJavaBean);
                        }else {
                            mControl.getHostSearchDataFail(t.getMessage());
                        }


                    }

                    @Override
                    protected void onFail(Throwable throwable) throws Exception {
                        mControl.getHostSearchDataFail(throwable.getMessage());

                    }
                });


    }
}
