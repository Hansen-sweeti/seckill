package com.dorby.seckill.controller;


import com.dorby.seckill.pojo.User;
import com.dorby.seckill.rabbitmq.MQSender;
import com.dorby.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 * dorby
 * @author dorby
 * @since 2021-12-16
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private MQSender mqSender;
    /**
    * @author:      dorby
    * @Description:  用户信息（测试）
    * 
    */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }


    @RequestMapping("/mq")
    @ResponseBody
    public String mq(){
        //mqSender.send("hello");
        return "hello";
    }
}
  