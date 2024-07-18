package com.zxr.aiojbakcend.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 */
public class VerificationToolUtils {
    /**
     * 验证手机号
     * @param phone
     * @return
     */
    public static boolean checkMobilePhone(String phone) {
        String regex = "^[1]\\d{10}$";
        if (StringUtils.isEmpty(phone) || phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }
}
