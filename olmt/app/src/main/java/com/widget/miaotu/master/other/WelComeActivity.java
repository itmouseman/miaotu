package com.widget.miaotu.master.other;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.widget.miaotu.MainActivity;
import com.widget.miaotu.R;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.ui.CountDownProgressView;
import com.widget.miaotu.http.ApiService;
import com.widget.miaotu.http.bean.HomeAdvertisingBean;
import com.widget.miaotu.http.bean.HomeInfoBean;
import com.widget.miaotu.http.bean.StartupInfoBean;
import com.widget.miaotu.master.other.guide.GuideActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WelComeActivity extends Activity {

    private CountDownProgressView cdp;

    private StartupInfoBean bean;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        cdp = findViewById(R.id.cdp);
        imageView = findViewById(R.id.iv_wel_come);
        cdp.setTimeMillis(800);
        cdp.setProgressType(CountDownProgressView.ProgressType.COUNT);
        cdp.start();
        cdp.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 15) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getStartImageForHttp();
                        }
                    }).start();
                }

                if (progress == 100) {
                    toActivity(SPStaticUtils.getInt(SPConstant.OPEN_APP_COUNT, 0));
                }
            }
        });
        cdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdp.stop();
                toActivity(SPStaticUtils.getInt(SPConstant.OPEN_APP_COUNT, 0));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null) {
                    cdp.stop();
                    Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("data", GsonUtils.toJson(bean));
                    startActivity(intent);
                    //增加APP启动次数数量
                    SPStaticUtils.put(SPConstant.OPEN_APP_COUNT, SPStaticUtils.getInt(SPConstant.OPEN_APP_COUNT, 0) + 1);
                    finish();
                }
            }
        });
    }

    private void getStartImageForHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().build();
        Request request = new Request.Builder().url(ApiService.BASE_URL + "v5/home/advertising").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.i(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resuylt = response.body().string();
                HomeAdvertisingBean homeAdvertisingBean = JSON.parseObject(resuylt, HomeAdvertisingBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!WelComeActivity.this.isFinishing()){
                            GlideUtils.loadUrl(WelComeActivity.this, homeAdvertisingBean.getData().getLink(), imageView);
                        }
                    }
                });
            }
        });

    }

    private void toActivity(int count) {
        if (count == 0) {
            startActivity(new Intent(WelComeActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(WelComeActivity.this, MainActivity.class));
        }
        //增加APP启动次数数量
        SPStaticUtils.put(SPConstant.OPEN_APP_COUNT, count + 1);
        finish();
    }

    // 重写activity的onDestroy（）方法，停止该页面的glide的加载请求


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    private void initHomeInfo() {


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(SPConstant.USER_ID, (SPConstant.USER_ID).isEmpty() ? "0" : SPStaticUtils.getString(SPConstant.USER_ID))
                .build();
        Request request = new Request.Builder()
                .url(ApiService.HuoDongLieBIao)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("错误了");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                HomeInfoBean homeInfoBean = gson.fromJson(response.body().string(), HomeInfoBean.class);
                Logger.e(homeInfoBean.toString());
                Logger.e(GsonUtils.toJson(homeInfoBean));
                SPStaticUtils.put(SPConstant.HOME_INFO, GsonUtils.toJson(homeInfoBean));


            }
        });

    }

    private void getStartupInfo() {
//        RxHttpUtils.createApi(ApiService.class)
//                .getStartupInfo(new UserIdBean(SPStaticUtils.getString(SPConstant.USER_ID)))
//                .compose(Transformer.<BaseData<StartupInfoBean>>switchSchedulers())
//                .subscribe(new com.allen.library.observer.DataObserver<StartupInfoBean>() {
//                    @Override
//                    protected void onError(String errorMsg) {
//
//                    }
//
//                    @Override
//                    protected void onSuccess(StartupInfoBean data) {
//                        bean = data;
//                        GlideLoadUtils.getInstance().glideLoad(
//                                WelComeActivity.this
//                        ,data.getStartupImage().getActivities_pic()
//                        ,(ImageView) findViewById(R.id.iv_wel_come)
//                        , R.mipmap.bg_base_default);
//                        initHomeInfo();
//                    }
//                });
    }
}
