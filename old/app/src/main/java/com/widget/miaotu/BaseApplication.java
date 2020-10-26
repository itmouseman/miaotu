package com.widget.miaotu;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.widget.miaotu.common.greedao.DaoMaster;
import com.widget.miaotu.common.greedao.DaoSession;

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static final String DB_NAME = "seedling.db";

    private static DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

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

        Logger.addLogAdapter(new AndroidLogAdapter( ));
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
