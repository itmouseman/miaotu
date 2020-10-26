package com.widget.miaotu;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.AppUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.cache.SqliteHelper;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smart.refresh.header.FalsifyFooter;
import com.scwang.smart.refresh.header.FalsifyHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.bugly.crashreport.CrashReport;
import com.widget.miaotu.common.greedao.DaoMaster;
import com.widget.miaotu.common.greedao.DaoSession;

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static final String DB_NAME = "seedling.db";

    private static DaoSession mDaoSession;

    //static 代码段可以防止内存泄露
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorAccent, android.R.color.white);//全局设置主题颜色
                return new FalsifyHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new FalsifyFooter(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SqliteHelper.appInstance = this;
        //bug上报  和统计
        bugliInit();
        //初始化日志处理
        initLogger();
        //MobSdk回传用户隐私授权结果
        MobSDK.submitPolicyGrantResult(true, null);
        initGreenDao();

        // EaseUI初始化
        if (EaseUI.getInstance().init(this, null)) {
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true);
            //EaseUI初始化成功之后再去调用注册消息监听的代码
        }
        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
//        if (EaseUI.getInstance().isMainProcess(this)) {
//
//            EMPushHelper.getInstance().setPushListener(new PushListener() {
//                @Override
//                public void onError(EMPushType pushType, long errorCode) {
//                    //  返回的errorCode仅9xx为环信内部错误，可从EMError中查询，其他错误请根据pushType去相应第三方推送网站查询。
//                    EMLog.e("PushClient", "Push client occur a error: " + pushType + " - " + errorCode);
//                }
//
//                @Override
//                public boolean isSupportPush(EMPushType emPushType, EMPushConfig emPushConfig) {
//                    return super.isSupportPush(emPushType, emPushConfig);
//                }
//            });
//        }

    }

    private void bugliInit() {
        CrashReport.initCrashReport(getApplicationContext(), "bdc29c9924", true);//发布阶段改为false
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        //...在这里设置strategy的属性，在bugly初始化时传入
        strategy.setAppChannel("Android");  //设置渠道
        strategy.setAppVersion(AppUtils.getAppVersionName());      //App的版本
        strategy.setAppPackageName(AppUtils.getAppPackageName());  //App的包名
        CrashReport.initCrashReport(this, "bdc29c9924", true, strategy);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    private void initLogger() {
        //初始化参数
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(3)
//                    .methodOffset(1)
//                    .logStrategy()
                .tag("miaotu")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter());
        //初始化要不要输出Log
        //        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
        //            @Override
        //            public boolean isLoggable(int priority, @Nullable String tag) {
        //                return BuildConfig.DEBUG;
        //            }
        //        });


        // 初始化是否拷贝文件   //保存的路径： /storage/emulated/0
        Logger.addLogAdapter(new DiskLogAdapter());

        // 主要是添加下面这句代码
        MultiDex.install(this);

    }

    public static BaseApplication instance() {
        return instance;
    }
}
