package com.content.baseui.util;

import android.content.Context;

import com.content.baseui.application.DevApplication;


public class DevAppUtil {

    private static DevApplication application;

    public static void init(DevApplication context) {
        DevAppUtil.application = context;
    }

    public static DevApplication getApplication() {
        if (application != null) return application;
        throw new NullPointerException("u should init first");
    }

    public static Context getApplicationContext() {
        if (application != null) return application.getApplicationContext();
        throw new NullPointerException("u should init first");
    }


}
