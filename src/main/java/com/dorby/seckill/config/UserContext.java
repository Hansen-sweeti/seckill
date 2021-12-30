package com.dorby.seckill.config;

import com.dorby.seckill.pojo.User;

/**
 * @Author: dorby
 * @Description:
 * @Date: 2021/12/27 14:49
 */
public class UserContext {
    private static ThreadLocal<User> userHolder=new ThreadLocal<User>();

    public static void setUser(User user){
        userHolder.set(user);
    }

    public static User getUser(){
        return userHolder.get();
    }
}
