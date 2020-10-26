package com.widget.miaotu.master.mvp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.base.MVCModel;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.HomeSearchJavaBean;
import com.widget.miaotu.http.bean.ImgUrlJavaBean;
import com.widget.miaotu.http.bean.SeedlingDetailJavaBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeTgAndMmModel extends MVCModel<HomeTgAndMmControl> {

    public HomeTgAndMmModel(HomeTgAndMmControl mControl) {
        super(mControl);
    }

    @Override
    public void release() {

    }

    public void getDataHomeTgAndMmDetail(String idMiaoMu) {

        JSONObject root = new JSONObject();

        try {
            root.put("id", idMiaoMu);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            RetrofitFactory.getInstence().API().seedlingDetail(requestBody)
                    .compose(TransformerUi.<BaseEntity<SeedlingDetailJavaBean>>setThread())
                    .subscribe(new BaseObserver<SeedlingDetailJavaBean>(BaseApplication.instance()) {
                        @Override
                        protected void onSuccess(BaseEntity<SeedlingDetailJavaBean> t) throws Exception {

                            mControl.getDataHomeTgAndMmDetailSuc(t.getData());
                        }

                        @Override
                        protected void onFail(Throwable throwable) throws Exception {
                            Logger.e(throwable.getMessage());

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
