package com.content.baseui.util.net;

import android.content.Context;

public interface NetworkStateInfc {
//    /**
//     * 判断设备是否有可用的网络
//     */
//    boolean isNetworkAvailable(Context context);

    /**
     * 判断设备网络连接是否已存在，并且可以建立连接并传递数据。
     */
    boolean isNetworkConnected(Context context);

//    /**
//     * 判断设备 WIFI是否可用
//     */
//    boolean isWifiAvailable(Context context);

    /**
     * 判断设备WIFI连接是否已存在，并且可以建立连接并传递数据。
     */
    boolean isWifiConnected(Context context);

//    /**
//     * 判断设备 数据流量是否可用
//     */
//    boolean isMobileAvailable(Context context);

    /**
     * 判断设备数据流量连接是否已存在，并且可以建立连接并传递数据。
     */
    boolean isMobileConnected(Context context);
}
