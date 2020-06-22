package com.content.baseui.util.net;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtil {

    private final static String TAG = NetworkUtil.class.getSimpleName();

    /**
     * 网络不可使用
     */
    public static final int NETWORKTYPE_INVALID = -1; // 无可用网络
    /** 2G */
    // public static final int NETWORKTYPE_2G = 1;
    /** 3G */
    // public static final int NETWORKTYPE_3G = 2;// 闈炶仈閫�3G
    /**
     * wifi
     */
    public static final int NETWORKTYPE_WIFI = 99; // 正在使用wifi

    /** 3G 中国联通 */
    // public static final int NETWORKTYPE_3G_UNICOM = 4; // 鑱旈��3G
    /**
     * wap 网络
     */
    // public static final int NETWORKTYPE_WAP = 5;

    private NetInfo theTempNet = null;

    /**
     * 返回当前网络连接类型
     * NETWORK_TYPE_UNKNOWN[0]: Network type is unknown
     * NETWORK_TYPE_GPRS[1]: ~ 100 kbps
     * NETWORK_TYPE_EDGE[2]: ~ 50-100 kbps
     * NETWORK_TYPE_UMTS[3]: ~ 400-7000 kbps
     * NETWORK_TYPE_CDMA[4]: ~ 14-64 kbps
     * NETWORK_TYPE_EVDO_0[5]: ~ 400-1000 kbps
     * NETWORK_TYPE_EVDO_A[6]: ~ 600-1400 kbps
     * NETWORK_TYPE_1xRTT[7]: ~ 50-100 kbps
     * NETWORK_TYPE_HSDPA[8]: ~ 2-14 Mbps
     * NETWORK_TYPE_HSUPA[9]: ~ 1-23 Mbps
     * NETWORK_TYPE_HSPA[10]: ~ 700-1700 kbps
     * NETWORK_TYPE_IDEN[11]: ~25 kbps
     * NETWORK_TYPE_EVDO_B[12]: ~ 5 Mbps
     * NETWORK_TYPE_LTE[13]: ~ 10+ Mbps
     * NETWORK_TYPE_EHRPD[14]: ~ 1-2 Mbps
     * NETWORK_TYPE_HSPAP[15]: ~ 10-20 Mbps
     */
    private static int getMobileNetworkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkType();
    }

    /**
     * 获得当前网络连接
     * NETWORKTYPE_INVALID[-1]: 网络不可用
     * NETWORK_TYPE_UNKNOWN[0]: Network type is unknown
     * NETWORK_TYPE_GPRS[1]: ~ 100 kbps
     * NETWORK_TYPE_EDGE[2]: ~ 50-100 kbps
     * NETWORK_TYPE_UMTS[3]: ~ 400-7000 kbps
     * NETWORK_TYPE_CDMA[4]: ~ 14-64 kbps
     * NETWORK_TYPE_EVDO_0[5]: ~ 400-1000 kbps
     * NETWORK_TYPE_EVDO_A[6]: ~ 600-1400 kbps
     * NETWORK_TYPE_1xRTT[7]: ~ 50-100 kbps
     * NETWORK_TYPE_HSDPA[8]: ~ 2-14 Mbps
     * NETWORK_TYPE_HSUPA[9]: ~ 1-23 Mbps
     * NETWORK_TYPE_HSPA[10]: ~ 700-1700 kbps
     * NETWORK_TYPE_IDEN[11]: ~25 kbps
     * NETWORK_TYPE_EVDO_B[12]: ~ 5 Mbps
     * NETWORK_TYPE_LTE[13]: ~ 10+ Mbps
     * NETWORK_TYPE_EHRPD[14]: ~ 1-2 Mbps
     * NETWORK_TYPE_HSPAP[15]: ~ 10-20 Mbps
     * NETWORKTYPE_WIFI[99]: 无线wifi
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected()) {// 若没有网络信息实例或者当前实例还不能够正常传递信息
            Log.e(TAG, "NETWORKTYPE_INVALID");
            return NETWORKTYPE_INVALID;
        }
        int type = netInfo.getType();
        int result = NETWORKTYPE_INVALID;
        switch (type) {
            case ConnectivityManager.TYPE_WIFI: {// 使用无线wifi
                result = NETWORKTYPE_WIFI;
                break;
            }
            case ConnectivityManager.TYPE_MOBILE: {// 使用运营商网络
                result = getMobileNetworkType(context);
                break;
            }
            default: {
                result = NETWORKTYPE_INVALID;
                break;
            }
        }
        return result;
    }

    public static boolean is4GConnected(Context context) {
        return getNetWorkType(context) == ConnectivityManager.TYPE_MOBILE;
    }

    public static NetInfo getWifiInfoModel(Context context) {
        WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Log.v(TAG, "wifiFoo");
        if (!mWifi.isWifiEnabled()) {
            return null;
        }
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        String netName = wifiInfo.getSSID(); // 获取被连接网络的名称
        int ip = wifiInfo.getIpAddress();
        String netMac = wifiInfo.getBSSID(); // 获取被连接网络的mac地址
        // String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址(网卡唯一标识)
        NetInfo temp = new NetInfo();
        temp.setNetIp(ip);
        temp.setNetMac(netMac);
        temp.setNetName(netName);
        return temp;
    }

    /**
     * 鑾峰彇Ip 鍦板潃
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, "exception_ipaddress");
        }
        return null;
    }

//    public static boolean isNetAvailableII(Context context) {
//        int state = getNetWorkType(context);
//        if (state == NETWORKTYPE_INVALID) {
//            return false;
//        }
//        return true;
//    }

//    public static boolean isWifiAvailable(Context ctx) {
//        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//        return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
//    }

//    /**
//     * 检测网络是否可用
//     *
//     * @return
//     */
//    public static boolean isNetAvailable(Context context) {
//        if (context != null) {
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo ni = cm.getActiveNetworkInfo();
//            if (ni != null) {
//                return ni.isConnected();
//            }
//        }
//        return false;
//    }

    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final int TYPE_NET_WORK_DISABLED = 0;
    public static final int TYPE_CM_CU_WAP = 4;// wap 10.0.0.172
    public static final int TYPE_CT_WAP = 5;// wap 10.0.0.200
    public static final int TYPE_OTHER_NET = 6;
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    public static int checkNetworkType(Context mContext) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mobNetInfoActivity = connectivityManager.getActiveNetworkInfo();
            if (mobNetInfoActivity == null || !mobNetInfoActivity.isAvailable()) {
                return TYPE_OTHER_NET;
            } else {
                int netType = mobNetInfoActivity.getType();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_OTHER_NET;
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    Cursor c = mContext.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                    if (c != null) {
                        c.moveToFirst();
                        final String user = c.getString(c.getColumnIndex("user"));
                        if (!TextUtils.isEmpty(user)) {
                            if (user.startsWith(CTWAP)) {
                                return TYPE_CT_WAP;
                            }
                        }
                    }
                    if (c != null) {
                        c.close();
                        c = null;
                    }

                    String netMode = mobNetInfoActivity.getExtraInfo();
                    if (netMode != null) {
                        netMode = netMode.toLowerCase();
                        if (netMode.equals(CMWAP) || netMode.equals(WAP_3G) || netMode.equals(UNIWAP)) {
                            return TYPE_CM_CU_WAP;
                        }

                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return TYPE_OTHER_NET;
        }

        return TYPE_OTHER_NET;

    }

    /**
     * 关闭wifi
     */
    public static void closeWifi(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(false);
    }

    /**
     * 打开wifi
     */
    public static void openWifi(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(true);
    }
}
