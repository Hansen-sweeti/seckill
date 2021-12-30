package com.dorby.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dorby.seckill.pojo.User;
import com.dorby.seckill.vo.LoginVo;
import com.dorby.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dorby
 * @since 2021-12-16
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);
}
