package com.content.baseui.util.log;

import android.util.Log;

import com.content.baseui.BuildConfig;

public class DevLog {
    public static final boolean ENABLE = BuildConfig.DEBUG;

    public static void d(String TAG, String msg) {
        if (ENABLE) Log.d(buildTag(TAG), msg);
    }

    public static void i(String TAG, String msg) {
        if (ENABLE) Log.i(buildTag(TAG), msg);
    }

    public static void w(String TAG, String msg) {
        if (ENABLE) Log.w(buildTag(TAG), msg);
    }

    public static void e(String TAG, String msg) {
        if (ENABLE) Log.e(buildTag(TAG), msg);
    }

    private static String buildTag(String TAG) {
        return "Auri_" + TAG;
    }
}
