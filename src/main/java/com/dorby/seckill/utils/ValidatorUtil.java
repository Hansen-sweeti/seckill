package com.dorby.seckill.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: dorby
 * @Description: 手机号码验证
 * @Date: 2021/12/16 14:24
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher=mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
