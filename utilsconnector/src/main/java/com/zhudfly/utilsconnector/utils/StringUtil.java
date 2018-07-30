package com.zhudfly.utilsconnector.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.URLUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 与字符串相关的辅助类，eg. verify email、phone
 * Created by zhudf on 2018/5/9.
 */

public class StringUtil {

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str){
        return TextUtils.isEmpty(str);
    }

    /**
     * Returns true if the string is null or 0-length or " ".
     * @param str the string to be examined
     * @return true if str is null or zero length or " "
     */
    public static boolean isTrimEmpty(@Nullable String str){
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }


    /**
     * 判断是否是手机号，只判断以‘1’开始且为11位
     * @param mobileNumber the mobileNumber to be examined
     * @return true if mobileNumber is valid
     */
    public static boolean checkMobile(String mobileNumber) {
        boolean flag;
        try {
            Pattern regex = Pattern.compile("^(1)\\d{10}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            flag = false;
        }
        return flag;
    }

    /**
     * check the tel is valid or not
     * with extension : 201-15632563-100
     * not with extension : 15632563-100
     * @param telNumber the tel to be examined
     * @param withExtension with extension or not
     * @return true if the telNum is valid
     */
    public static boolean checkTel(String telNumber, boolean withExtension) {
        boolean flag;
        try {
            Pattern regex = withExtension
                            ?Pattern.compile("^0\\d{2,3}-\\d{7,8}-\\d{1,5}$")
                            :Pattern.compile("^0\\d{2,3}-\\d{7,8}$");
            Matcher matcher = regex.matcher(telNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            flag = false;
        }
        return flag;
    }

    /**
     * check the tel is valid or not (both with or without extesion)
     * @param telNumber the tel to be examined
     * @return true if the telNum is valid
     */
    public static boolean checkTel(String telNumber) {
        return checkTel(telNumber, true) || checkTel(telNumber, false);
    }

    /**
     * check the email is valid or not
     *
     * @param email the email to be examined
     * @return true if email is valid
     */
    public static boolean checkEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)" +
                        "@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                        "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}" +
                        "|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * check url is valid or not
     * @param url the url to be examined
     * @return true if url is valid
     */
    public static boolean checkUrl(String url) {
        return URLUtil.isValidUrl(url);
    }

    /**
     * check string is combine by digital or not
     * @param str the string to be examined
     * @return true if string is combine by digital
     */
    public static boolean isDigital(String str) {
        return !isEmpty(str) && str.matches("^[0-9]*$");
    }
}
