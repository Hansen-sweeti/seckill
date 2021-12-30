package com.dorby.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: dorby
 * @Description: 公共返回对象枚举
 * @Date: 2021/12/16 13:14
 */

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务器异常"),

    //登陆模块
    MOBILE_ERROR(500211,"手机号码不正确"),
    LOGIN_ERROR(500210,"用户名或者密码不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    MOBILE_NOT_EXIST(500213,"手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214,"密码更新失败"),
    SESSION_ERROR(500215,"用户不存在"),
    //订单模块
    ORDER_NOT_EXIST(500300,"订单信息不存在"),

    //秒杀模块
    REPEATE_ERROR(500501,"该商品没人限购买一件"),
    EMPTY_STOCK(500500,"库存不足"),
    ERROR_CAPTCHA(500503,"验证码错误，重新输入"),
    ACCESS_LIMIT_REACHED(500504,"访问过于频繁，请稍后再试"),
    REQUEST_ILEGAL(500502,"请求非法，请重新尝试");

    private final Integer code;
    private final String message;

}
