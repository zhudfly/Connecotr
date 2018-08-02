package com.zhudfly.utilsconnector.utils;

import android.text.TextUtils;
import java.security.MessageDigest;

/**
 * Encryption helper
 * Created by zhudf on 2018/5/8.
 */

public class EncryptionUtil {

    /**
     * encryption by md5
     * @param buffer
     * @return
     */
    public static String md5(byte[] buffer) {
        char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    /**
     * encryption by md5
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(TextUtils.isEmpty(string))
            return "";

        return md5(string.getBytes());
    }
}
