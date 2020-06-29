package com.content.baseui.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;


import androidx.multidex.MultiDexApplication;

import com.content.baseui.util.DevAppUtil;
import com.content.baseui.util.log.DevLog;
import com.content.baseui.util.net.NetworkComnTool;
import com.content.baseui.util.net.NetworkStateInfc;

import java.util.concurrent.atomic.AtomicBoolean;

public class DevApplication extends MultiDexApplication {

    protected String TAG = DevApplication.class.getSimpleName();

    private NetworkStateInfc networkStateTool;// 网络检测工具

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        DevLog.d(TAG, "onCreate()");
        super.onCreate();
        DevAppUtil.init(this);
        addLifecycleCallback();
        networkStateTool = new NetworkComnTool();
    }

    @Override
    public void onTerminate() {// 只是在android模拟器上会触发 真机不会
        DevLog.d(TAG, "onTerminate()");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        DevLog.d(TAG, "onLowMemory()");
        super.onLowMemory();
    }

    //<editor-fold desc="简易获取UI线程的方法" defaultstate="collapsed">
    Handler mUiHandler = null; // 用于向界面发送执行代码

    /**
     * 获取Handler简化跨线程执行代码,实现同步/定时器等功能
     * 1.获取指定线程Handler 2.实现Runnable:run()代码
     * 2.调用Handler:post/postAt postDelayed传入Runnable对 获取界面Handler
     */
    public Handler getUiHandler() {
        return (mUiHandler != null) ? (mUiHandler) : (mUiHandler = new Handler(getMainLooper()));
    }

    /**
     * 静态工具函数 免创建Handler实现同步和定时器 直接向ui线程执行代码
     */
    public boolean post2UIRunnable(Runnable r) {
        return getUiHandler().post(r);
    }

    public boolean post2UIAtTime(Runnable r, long uptimeMillis) {
        return getUiHandler().postAtTime(r, uptimeMillis);
    }

    public boolean post2UIDelayed(Runnable r, long delayMillis) {
        return getUiHandler().postDelayed(r, delayMillis);
    }
    //</editor-fold>

    //<editor-fold desc="监测APP是否退到后台的方法" defaultstate="collapsed">
    protected static AtomicBoolean isAppInBackground = new AtomicBoolean(false);// 如果app出现了压入后台的现象 则这里置为true。
    private int appCount = 0;

    public static boolean hasAppBeenBackground() {
        return isAppInBackground.get();
    }

    private void addLifecycleCallback() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityCreated :" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityStarted :" + activity.getLocalClassName() + " appCount:" + appCount);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityResumed :" + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityPaused :" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
                if (appCount == 0) {
                    isAppInBackground.set(true);
                }
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityStopped :" + activity.getLocalClassName() + " appCount:" + appCount);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivitySaveInstanceState :" + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                DevLog.i(TAG, "ActivityLifecycleCallbacks onActivityDestroyed :" + activity.getLocalClassName());
            }
        });
    }

    public boolean hasAppbeenInBackground() {
        return isAppInBackground.get();
    }

    public void resetAppbeanInBackground() {
        isAppInBackground.set(false);
    }

    //</editor-fold>

    //<editor-fold desc="检测网络连接情况方法" defaultstate="collapsed">
    public boolean isNetworkConnected() {
        return networkStateTool.isNetworkConnected(DevApplication.this.getApplicationContext());
    }

    public boolean isMobileConnected() {
        return networkStateTool.isMobileConnected(DevApplication.this.getApplicationContext());
    }

    public boolean isWifiConnected() {
        return networkStateTool.isWifiConnected(DevApplication.this.getApplicationContext());
    }
    //</editor-fold>

}
