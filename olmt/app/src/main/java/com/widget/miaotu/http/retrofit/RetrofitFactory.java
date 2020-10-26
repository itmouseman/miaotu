package com.widget.miaotu.http.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPStaticUtils;
import com.widget.miaotu.BaseApplication;
import com.widget.miaotu.common.constant.SPConstant;

import com.widget.miaotu.common.utils.gsutils.DoubleDefault0Adapter;
import com.widget.miaotu.common.utils.gsutils.IntegerDefaultAdapter;
import com.widget.miaotu.common.utils.gsutils.LongDefault0Adapter;
import com.widget.miaotu.common.utils.gsutils.StringNullAdapter;
import com.widget.miaotu.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jbwl on 2018/10/10 17:47.
 */

public class RetrofitFactory {
    private static RetrofitFactory mRetrofitFactory;
    private static ApiService mApiservice;
    private static Gson gson;

    private RetrofitFactory() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w("OKHTTPLL", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS).
                readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS).
                writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder();
                        String token = SPStaticUtils.getString(SPConstant.NEW_TOKEN);
                        String oldToken = SPStaticUtils.getString(SPConstant.AUTHORIZATION);
                        if (!TextUtils.isEmpty(token)) {
                            builder.addHeader("newToken", token);
                            builder.addHeader("token", oldToken);
                        }

                        builder.addHeader("url_name", "new");
                        builder.addHeader("appVersion", "5.8");
//                        builder.addHeader("appVersion","4.0");
                        builder.addHeader("modelType", "android");
                        builder.addHeader("app_key", "android");
                        builder.addHeader("other_header", URLEncoder.encode("中文需要转码"));
                        Response response = chain.proceed(builder.build());
                        return response;
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient).build();
        mApiservice = mRetrofit.create(ApiService.class);
    }

    public static RetrofitFactory getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null) mRetrofitFactory = new RetrofitFactory();
            }
        }
        return mRetrofitFactory;
    }

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(String.class, new StringNullAdapter())
                    .create();
        }
        return gson;
    }

    public Interceptor ResponseInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

//                String token = SpUtil.getStringSF(BaseApplication.instance()
//                        , SPConstant.AUTHORIZATION);
//                Logger.e(token);
//                if (!TextUtils.isEmpty(token)) {
//                    builder.addHeader("accessToken", token);
//                }
                return chain.proceed(builder.build());
            }
        };
    }

    public ApiService API() {
        return mApiservice;
    }
}
