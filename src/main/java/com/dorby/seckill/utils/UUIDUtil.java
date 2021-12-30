package com.dorby.seckill.utils;

import java.util.UUID;

/**
 * @Author: dorby
 * @Description: UUID工具类
 * @Date: 2021/12/16 17:20
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
