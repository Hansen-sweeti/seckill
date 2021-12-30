package com.dorby.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: dorby
 * @Description: 测试
 * @Date: 2021/12/16 10:30
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
    * @author:      dorby
    * @Description:  测试页面跳转
     *
    */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","x");
        return "hello";
    }
}
