package com.content.baseui.util.str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String PASSWORD_VALIDATOR = "^[0-9a-zA-Z]{6,20}$";// 登录密码校验 6~20位数字或大小写字母

//    public static final String AUTHCODE_WEIXIN_VALIDATOR = "^[1][0,1,2,3,4,5][0-9]{16}$";// 微信授权码 18位纯数字，以10、11、12、13、14、15开头
//    public static final String AUTHCODE_AL_VALIDATOR = "^[28][0-9]{16}$";// 支付宝授权码 28开头的18位数字。

    public static final String NUMBER_VALIDATOR = "^[0-9]*$";// 全数字

    public static boolean isMatch(String regexStr, String targetStr) {
        if (StrUtil.isEmpty(regexStr) || StrUtil.isEmpty(targetStr)) {
            return false;
        }
        Pattern p = Pattern.compile(regexStr.trim());
        Matcher m = p.matcher(targetStr);
        return m.matches();
    }

    /**
     * 身份证替换*,保留前1后1位
     *
     * @param idCard 身份证号
     * @return 替换为*后的身份证信息
     */
    public static String idCardReplaceWithStar(String idCard) {
        return replaceAction(idCard, "(?<=\\d{1})\\d(?=\\d{1})");
    }

    /**
     * 替换银行卡号，保留最后四位
     *
     * @param bankCard
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {
        return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
    }

    /**
     * 手机号，保留前3后4
     *
     * @param phoneNumber
     * @return
     */
    public static String phoneNumberReplaceWithStar(String phoneNumber) {
        return replaceAction(phoneNumber, "(?<=\\d{3})\\d(?=\\d{4})");
    }

    /**
     * 邮箱，@前字符串保留前3后1
     *
     * @param email
     * @return
     */
    public static String emailReplaceWithStar(String email) {
        return replaceAction(email, "(?<=\\S{3})\\S(?=\\S+@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)");
    }

    /**
     * 字符串根据正则替换
     *
     * @param str     替换字符串
     * @param regular 正则表达式
     * @return 替换为*后的字符串
     */
    public static String replaceAction(String str, String regular) {
        return str.replaceAll(regular, "*");
    }
}
