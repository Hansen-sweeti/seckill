package com.dorby.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: dorby
 * @Description: MD5工具类
 * @Date: 2021/12/16 11:23
 */
@Component
public class MD5Util {
    /**
    * @author:      dorby
    * @Description:  md5加密
    *
    */
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt="1a2b3c4d";
//    第一次加密
    public static String inputPassToPass(String inputPass){
        String str="" + salt.charAt(0) + salt.charAt(2) + inputPass +
                salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
//    第二次加密
    public static String fromPassToDBPass(String fromPass,String salt){
        String str=salt.charAt(0)+salt.charAt(2)+fromPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass=inputPassToPass(inputPass);
        String dbPass=fromPassToDBPass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToPass("123456"));
        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9",salt));
        System.out.println(inputPassToDBPass("123456",salt));
    }
}
