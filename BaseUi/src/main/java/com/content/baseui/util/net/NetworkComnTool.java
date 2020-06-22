package com.content.baseui.util.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.content.baseui.util.log.DevLog;


public class NetworkComnTool implements NetworkStateInfc {

    private static final String TAG = NetworkComnTool.class.getSimpleName();

   /* @Override
    public boolean isNetworkAvailable(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager mgr = getSysConnectivityManager(context);
            Network[] infos = mgr.getAllNetworks();
            if (infos != null) {
                for (int i = 0; i < infos.length; i++) {
                    NetworkInfo networkInfo = mgr.getNetworkInfo(infos[i]);
                    if (networkInfo.isAvailable())
                }
            }
        } else {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        } return false;
    }*/

    @Override
    public boolean isNetworkConnected(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager mgr = getSysConnectivityManager(context);
            Network[] infos = mgr.getAllNetworks();
            if (infos != null && infos.length != 0) {
                for (int i = 0; i < infos.length; i++) {
                    NetworkInfo networkInfo = mgr.getNetworkInfo(infos[i]);
                    if (networkInfo.isConnected()) {
                        return true;// 只要有一个已建立的网络连接就算
                    }
                }
            }
        } else {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }
        }
        return false;
    }

//    @Override
//    public boolean isWifiAvailable(Context context) {
//        NetworkInfo mWiFiNetworkInfo = getWifiNetworkInfo(context);
//        if (mWiFiNetworkInfo != null) {
//            return mWiFiNetworkInfo.isAvailable();
//        }
//        return false;
//    }

    @Override
    public boolean isWifiConnected(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager mgr = getSysConnectivityManager(context);
            Network[] infos = mgr.getAllNetworks();
            if (infos == null || infos.length == 0) {
                return false;
            }
            for (Network network : infos) {
                NetworkCapabilities networkCapabilities = mgr.getNetworkCapabilities(network);
                boolean isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// 感知型wifi不知道算不算
                    isWifi = isWifi || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE);
                }
                if (!isWifi) {
                    continue;
                }
                NetworkInfo networkInfo = mgr.getNetworkInfo(network);
                if (networkInfo.isConnected()) {
                    return true;// 只要有一个已建立的网络连接就算
                }
            }
        } else {
            NetworkInfo mWiFiNetworkInfo = getWifiNetworkInfo(context);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isConnected();
            }
        }
        return false;
    }

//    @Override
//    public boolean isMobileAvailable(Context context) {
//        NetworkInfo mMobileNetworkInfo = getMobileNetworkInfo(context);
//        if (mMobileNetworkInfo != null) {
//            return mMobileNetworkInfo.isAvailable();
//        }
//        return false;
//    }

    @Override
    public boolean isMobileConnected(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager mgr = getSysConnectivityManager(context);
            Network[] infos = mgr.getAllNetworks();
            if (infos == null || infos.length == 0) {
                return false;
            }
            for (Network network : infos) {
                NetworkCapabilities networkCapabilities = mgr.getNetworkCapabilities(network);
                boolean isCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// 感知型wifi不知道算不算
//                    isWifi = isWifi || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE);
//                }
                if (!isCellular) {
                    continue;
                }
                NetworkInfo networkInfo = mgr.getNetworkInfo(network);
                if (networkInfo.isConnected()) {
                    return true;// 只要有一个已建立的网络连接就算
                }
            }
        } else {
            NetworkInfo mMobileNetworkInfo = getMobileNetworkInfo(context);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    private NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = getSysConnectivityManager(context);
        return cm.getActiveNetworkInfo();
    }

    private NetworkInfo getWifiNetworkInfo(Context context) {
        ConnectivityManager cm = getSysConnectivityManager(context);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    }

    private NetworkInfo getMobileNetworkInfo(Context context) {
        ConnectivityManager cm = getSysConnectivityManager(context);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    }

    private ConnectivityManager getSysConnectivityManager(Context context) {
        if (context == null) {
            return null;
        }
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    private Callback callback;
    private NetBroadcastReceiver netBroadcastReceiver;// android 7.0以下，使用动态广播的形式
    private ConnectivityManager.NetworkCallback networkCallback;// android 7.0（包含7.0）以上，使用回调的形式

    //    @TargetApi(Build.VERSION_CODES.N)
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void registerNetworkLisner(final Context context,final Callback callback) {
        this.callback = callback;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (this.networkCallback != null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }
            this.networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    ConnectivityManager mgr = getSysConnectivityManager(context);
                    NetworkCapabilities networkCapabilities = mgr.getNetworkCapabilities(network);
                    // 判断是否是WIFI
                    boolean isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// 感知型wifi不知道算不算
                        isWifi = isWifi || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE);
                    }
                    if (isWifi) {
                        NetworkInfo networkInfo = mgr.getNetworkInfo(network);
                        if (networkInfo.isConnected()) {
                            if (callback != null) {
                                callback.whenWifiConnected();// 只要有一个已建立的网络连接就算
                                return;
                            }
                        }
                    }
                    // 判断是否是数据流量
                    boolean isCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                    if (isCellular) {
                        NetworkInfo networkInfo = mgr.getNetworkInfo(network);
                        if (networkInfo.isConnected()) {
                            if (callback != null) {
                                callback.whenWifiConnected();// 只要有一个已建立的网络连接就算
                                return;
                            }
                        }
                    }
                    if (callback != null) {
                        callback.whenUnknowConnected();
                        return;
                    }
                    Log.i(TAG, "onAvailable: " + network);
                }

                @Override
                public void onLosing(Network network, int maxMsToLive) {
                    super.onLosing(network, maxMsToLive);
                    Log.i(TAG, "onLosing: " + network);
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    Log.i(TAG, "onLost: " + network);
                    if (callback != null) {
                        callback.whenNetworkLosted();
                    }
                }

                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                    Log.i(TAG, "onUnavailable: ");
                }

                @Override
                public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    Log.i(TAG, "onCapabilitiesChanged: " + network);
                }

                @Override
                public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties);
                    Log.i(TAG, "onLinkPropertiesChanged: " + network);
                }
            };
            connectivityManager.registerDefaultNetworkCallback(this.networkCallback);
        } else {
            // 采用动态广播的形式完成网络监听
            if (netBroadcastReceiver != null) {
                context.unregisterReceiver(netBroadcastReceiver);
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            netBroadcastReceiver = new NetBroadcastReceiver();
            //注册广播接收
            context.registerReceiver(netBroadcastReceiver, filter);
        }
    }

    public void releaseNetworkLisner(Context context) {
        this.callback = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (this.networkCallback != null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }
        } else {
            if (netBroadcastReceiver != null) {
                context.unregisterReceiver(netBroadcastReceiver);
            }
        }
    }

    class NetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = getActiveNetworkInfo(context);
                if (networkInfo == null) {
                    DevLog.w(TAG, "NetBroadcastReceiver 无可用网络");
                    if (callback != null) {
                        callback.whenNetworkLosted();
                    }
                } else if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                    DevLog.w(TAG, "NetBroadcastReceiver 已连接WIFI");
                    if (callback != null) {
                        callback.whenWifiConnected();
                    }
                } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                    DevLog.w(TAG, "NetBroadcastReceiver 已连接移动数据");
                    if (callback != null) {
                        callback.whenMobileConnected();
                    }
                } else {
                    DevLog.w(TAG, "NetBroadcastReceiver 已连接未知网络 type:" + networkInfo.getType() + " name:" + networkInfo.getTypeName());
                    if (callback != null) {
                        callback.whenUnknowConnected();
                    }
                }
            }
        }
    }

    public interface Callback {
        void whenMobileConnected();

        void whenWifiConnected();

        void whenUnknowConnected();

        void whenNetworkLosted();
    }
}
