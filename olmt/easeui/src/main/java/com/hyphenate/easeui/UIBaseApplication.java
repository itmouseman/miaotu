package com.hyphenate.easeui;

import android.app.Application;

public class UIBaseApplication extends Application {
    private static UIBaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static UIBaseApplication instance() {
        return instance;
    }
}
