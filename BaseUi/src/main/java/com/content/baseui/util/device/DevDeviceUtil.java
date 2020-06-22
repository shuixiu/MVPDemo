package com.content.baseui.util.device;

import android.os.Build;

import com.content.baseui.util.log.DevLog;
import com.content.baseui.util.str.StrUtil;


public class DevDeviceUtil {

    private static final String TAG = DevDeviceUtil.class.getSimpleName();

    // 手机制造商
    public static String getProduct() {
        DevLog.d(TAG, "手机制造商: " + Build.PRODUCT);
        return Build.PRODUCT;
    }

    /**
     * 识别系统定制商(品牌)
     * samsung 三星
     * Xiaomi 小米
     * HUAWEI 华为
     * HONOR 华为荣耀
     * vivo
     * OPPO
     * Coolpad
     * Meizu
     * Sony 索尼
     * LG
     *
     * @return
     */
    public static String getBrand() {
        DevLog.d(TAG, "系统定制商: " + Build.BRAND);
        return Build.BRAND;
    }

    // 硬件制造商
    public static String getManufacturer() {
        DevLog.d(TAG, "硬件制造商: " + Build.MANUFACTURER);
        return Build.MANUFACTURER;
    }

    // 硬件名称
    public static String getHardWare() {
        DevLog.d(TAG, "硬件名称: " + Build.HARDWARE);
        return Build.HARDWARE;
    }

    // 型号
    public static String getMode() {
        DevLog.d(TAG, "型号: " + Build.MODEL);
        return Build.MODEL;
    }

    // Android 系统版本
    public static String getAndroidVersion() {
        DevLog.d(TAG, "Android系统版本: " + Build.VERSION.RELEASE);
        return Build.VERSION.RELEASE;
    }

//    // CPU 指令集，可以查看是否支持64位
//    public static String getCpuAbi() {
//        return Build.CPU_ABI;
//    }

    public static String showInfo() {
        return "手机制造商: " + Build.PRODUCT//
                + "\n系统定制商: " + Build.BRAND//
                + "\n硬件制造商: " + Build.MANUFACTURER//
                + "\n硬件名称: " + Build.HARDWARE//
                + "\n型号: " + Build.MODEL + "\nAndroid系统版本: " + Build.VERSION.RELEASE;
    }

    public static boolean isXiaoMiDevice() {
        return StrUtil.isEqual("Xiaomi", getBrand());
    }

    public static boolean isSamsungDevice() {
        return StrUtil.isEqual("samsung", getBrand());
    }

    public static boolean isHuaweiDevice() {
        return StrUtil.isEqual("HUAWEI", getBrand()) || StrUtil.isEqual("HONOR", getBrand());
    }

    public static boolean isOPPODevice() {
        return StrUtil.isEqual("OPPO", getBrand());
    }
}
