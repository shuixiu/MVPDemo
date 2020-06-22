package com.content.baseui.util.str;

import java.util.List;

public class StrUtil {
    /**
     * 判断字符是否为空
     *
     * @param content
     * @return
     */
    public static boolean isEmpty(String content) {
        return (null == content || "".equals(content.trim()));
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEqual(String str1, String str2) {
        return str1 == null ? (str2 == null ? true : false) : (str2 == null ? false : str1.trim().equals(str2.trim()));
    }

    public static String toHexString(byte by) {
        try {
            return Integer.toHexString((0x000000ff & by) | 0xffffff00).substring(6);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把list转化成string字符串
     *
     * @param contactPhones
     * @return
     */
    public static String ArrayToString_Local(List<String> contactPhones) {
        if (contactPhones == null) {
            return null;
        }
        StringBuilder resultSb = new StringBuilder("(");
        boolean flag = false;
        for (String string : contactPhones) {
            if (flag) {
                resultSb.append(",");
            } else {
                flag = true;
            }
            resultSb.append("'" + string + "'");
        }
        resultSb.append(")");
        return resultSb.toString();
    }

    /**
     * 把list转化成string字符串
     *
     * @param contactPhones
     * @return
     */
    public static String transArrayToString_Remote(List<String> contactPhones) {
        if (contactPhones == null) {
            return null;
        }
        StringBuilder resultSb = new StringBuilder();
        boolean flag = false;
        for (String string : contactPhones) {
            if (flag) {
                resultSb.append(",");
            } else {
                flag = true;
            }
            resultSb.append(string);
        }
        return resultSb.toString();
    }

    // public static String transArrayToString_enen(String[] strArr)
    // {
    // if(null == strArr || strArr.length == 0)
    // {
    // return null;
    // }
    // StringBuilder sBuilder=new StringBuilder();
    // boolean flag=false;
    // for (String string : strArr) {
    // if (flag) {
    // sBuilder.append(",");
    // }else {
    // flag=true;
    // }
    // sBuilder.append(string);
    // }
    // return sBuilder.toString();
    // }

    public static double oneAfterDian(double d) {
        double r = 0.0;
        try {
            r = (double) ((int) Math.round(d * 100) / 100.00);
        } catch (Exception e) {

        }
        return r;
    }

    /**
     * 隐藏手机中间数字
     */
    public static String resetPhoneNum(String str) {
        try {
            String dev = "";
            if (str != null && str.length() > 10) {
                dev = str.substring(0, 3) + "****" + str.substring(7, str.length());
            }
            return dev;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

}
