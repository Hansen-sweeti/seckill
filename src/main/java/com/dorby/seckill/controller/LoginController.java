package com.dorby.seckill.controller;

import com.dorby.seckill.service.IUserService;
import com.dorby.seckill.vo.LoginVo;
import com.dorby.seckill.vo.RespBean;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: dorby
 * @Description: 登陆
 * @Date: 2021/12/16 12:53
 */
//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;


    /**
    * @author:      dorby
    * @Description:  跳转登陆页面
    * 
    */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        //log.info("{}",loginVo);
        return userService.doLogin(loginVo,request,response);
    }


}
