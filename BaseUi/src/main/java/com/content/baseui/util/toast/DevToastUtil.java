package com.content.baseui.util.toast;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.content.baseui.util.DevAppUtil;
import com.content.baseui.util.device.DevDeviceUtil;
import com.content.baseui.util.log.DevLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.drakeet.support.toast.ToastCompat;

public class DevToastUtil {

    private static final String TAG = DevToastUtil.class.getSimpleName();

    private Toast sToast;
    private ToastCompat mToast;

    private volatile boolean hasReflectedHandler = false;

    private static DevToastUtil instance;

    public static DevToastUtil getInstance() {
        if (instance == null) {
            synchronized (DevToastUtil.class) {
                if (instance == null) {
                    instance = new DevToastUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 自定义Toast展示 目前小米手机仍使用系统toast
     */
    public void showShortToast(int resId) {
        String msgStr = DevAppUtil.getApplication().getResources().getString(resId);
        showShortToast(msgStr);
    }

    public void showShortToast(String msg) {
        showToast(msg, DevToast.LENGTH_SHORT);
    }

    /**
     * 自定义Toast展示 目前小米手机仍使用系统toast
     */
    public void showLongToast(int resId) {
        String msgStr = DevAppUtil.getApplication().getResources().getString(resId);
        showLongToast(msgStr);
    }

    public void showLongToast(String msg) {
        showToast(msg, DevToast.LENGTH_LONG);
    }

    public void showToast(final String message, final int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        DevLog.d(TAG, "showShortToast msg:" + message);
        DevAppUtil.getApplication().post2UIRunnable(new Runnable() {
            @Override
            public void run() {
                int sdkInt = Build.VERSION.SDK_INT;
                if (sdkInt >= Build.VERSION_CODES.O) {
                    DevToastUtil.getInstance().showSysToast(DevAppUtil.getApplication().getApplicationContext(), message, duration);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // Android API 7.0 以上使用系统的
//                    ToastCompat.makeText(DevAppUtil.getApplication().getApplicationContext(), message, duration).show();
                    DevToastUtil.getInstance().showMyToast(DevAppUtil.getApplication().getApplicationContext(), message, duration);
                } else if (DevDeviceUtil.isXiaoMiDevice()) {
                    DevToastUtil.getInstance().showSysToast(DevAppUtil.getApplication().getApplicationContext(), message, duration);
                } else {
                    DevToastUtil.getInstance().showMyToast(DevAppUtil.getApplication().getApplicationContext(), message, duration);
                }
            }
        });
    }

    /**
     * 自定义Toast展示 目前小米手机仍使用系统toast
     */
    private synchronized void showMyToast(@NonNull Context context, String message, int duration) {
        DevLog.i(TAG, "showMyToast msg:" + message);
        if (mToast == null) {
//            mToast = DevToast.makeText(context, message, duration);
            mToast = ToastCompat.makeText(context, message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /*
     } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (isReflectedHandler) {
                        DevToastUtil.getInstance().showSysToast(DevAppUtil.getApplication().getApplicationContext(), message, duration);
                    } else { //这里为了避免多次反射，使用一个标识来限制
                        isReflectedHandler = true;
                        reflectTNHandler(sToast);
                    }
     */

    /**
     * 使用系统Toast
     */
    private synchronized void showSysToast(@NonNull Context context, String message, int duration) {
        DevLog.i(TAG, "showSysToast msg:" + message);
        if (sToast == null) {
            sToast = Toast.makeText(context, message, duration);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !hasReflectedHandler) {
                hasReflectedHandler = true;
                reflectTNHandler(sToast);
            }
        } else {
            sToast.setText(message);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

//    public void showLongToast(final String message) {
//        if (StrUtil.isEmpty(message)) {
//            return;
//        }
//        DevLog.d(TAG, "showLongToast msg:" + message);
//        DevAppUtil.getApplication().post2UIRunnable(new Runnable() {
//            @Override
//            public void run() {
//                if (Build.VERSION.SDK_INT >= 24) { // Android API 7.0 以上使用系统的
//                    DevToastUtil.getInstance().showSysToast(DevAppUtil.getApplication().getApplicationContext(), message, Toast.LENGTH_LONG);
//                } else if (DevDeviceUtil.isXiaoMiDevice()) {
//                    DevToastUtil.getInstance().showSysToast(DevAppUtil.getApplication().getApplicationContext(), message, Toast.LENGTH_LONG);
//                } else {
//                    DevToastUtil.getInstance().showMyToast(DevAppUtil.getApplication().getApplicationContext(), message, DevToast.LENGTH_LONG);
//                }
//            }
//        });
//    }

    private static void reflectTNHandler(Toast toast) {
        try {
            Field tNField = toast.getClass().getDeclaredField("mTN");
            if (tNField == null) {
                return;
            }
            tNField.setAccessible(true);
            Object TN = tNField.get(toast);
            if (TN == null) {
                return;
            }
            Field handlerField = TN.getClass().getDeclaredField("mHandler");
            if (handlerField == null) {
                return;
            }
//            Timber.d("TN class is %s.", TN.getClass());
            DevLog.d(TAG, String.format("TN class is %s.", TN.getClass()));
            handlerField.setAccessible(true);
            handlerField.set(TN, new ProxyTNHandler(TN));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //Toast$TN持有的Handler变量
    private static class ProxyTNHandler extends Handler {
        private Object tnObject;
        private Method handleShowMethod;
        private Method handleHideMethod;

        ProxyTNHandler(Object tnObject) {
            this.tnObject = tnObject;
            try {
                this.handleShowMethod = tnObject.getClass().getDeclaredMethod("handleShow", IBinder.class);
                this.handleShowMethod.setAccessible(true);
                DevLog.d(TAG, String.format("handleShow method is %s", handleShowMethod));
                this.handleHideMethod = tnObject.getClass().getDeclaredMethod("handleHide");
                this.handleHideMethod.setAccessible(true);
                DevLog.d(TAG, String.format("handleHide method is %s", handleHideMethod));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    //SHOW
                    IBinder token = (IBinder) msg.obj;
                    DevLog.d(TAG, String.format("handleMessage(): token is %s", token));
                    if (handleShowMethod != null) {
                        try {
                            handleShowMethod.invoke(tnObject, token);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (WindowManager.BadTokenException e) {
                            //显示Toast时添加BadTokenException异常捕获
                            e.printStackTrace();
                            DevLog.e(TAG, e.toString() + " show toast error.");
                        }
                    }
                    break;
                }

                case 1: {
                    //HIDE
                    DevLog.d(TAG, "handleMessage(): hide");
                    if (handleHideMethod != null) {
                        try {
                            handleHideMethod.invoke(tnObject);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 2: {
                    //CANCEL
                    DevLog.d(TAG, "handleMessage(): cancel");
                    if (handleHideMethod != null) {
                        try {
                            handleHideMethod.invoke(tnObject);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

            }
            super.handleMessage(msg);
        }
    }
}
