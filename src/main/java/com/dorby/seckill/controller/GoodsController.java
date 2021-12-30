package com.dorby.seckill.controller;

import com.dorby.seckill.pojo.User;
import com.dorby.seckill.service.IGoodsService;
import com.dorby.seckill.service.IUserService;
import com.dorby.seckill.vo.DetailVo;
import com.dorby.seckill.vo.GoodsVo;
import com.dorby.seckill.vo.RespBean;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.WebConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dorby
 * @Description: 商品
 * @Date: 2021/12/16 18:19
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {


    /**
     * @author: dorby
     * @Description: windows优化前QPS    12000 线程数1000 循环10次
     * 缓存页面到redis中 20000
     */
    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
////    public String toList(HttpSession session, Model model,@CookieValue("userTicket") String ticket){
//    public String toList(HttpServletRequest request,HttpServletResponse response,Model model, @CookieValue("userTicket") String ticket){
//        if(StringUtils.isEmpty(ticket)){
//            return "login";
//        }
//        User user=userService.getUserByCookie(ticket,request,response);
////        User user=(User)session.getAttribute(ticket);
//        if(user==null){
//            return "login";
//        }
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        //redis中获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

        //如果为空，手动渲染，存入redis并且返回
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60 , TimeUnit.SECONDS);
        }
        return html;
//        return "goodsList";
    }


    /**
     * @author: dorby
     * @Description: 商品详情
     *
     */
//    @RequestMapping(value = "/toDetail/{goodsId}",produces = "text/html;charset=utf-8")

//    @RequestMapping("/toDetail/{goodsId}")
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    //    public String toDetail(Model model,User user,@PathVariable Long goodsId,HttpServletRequest request,HttpServletResponse response){
//        ValueOperations valueOperations=redisTemplate.opsForValue();
//        String html=(String) valueOperations.get("goodsDetail:"+goodsId);
//        if(!StringUtils.isEmpty(html)){
//            return html;
//        }
    //        model.addAttribute("user", user);
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId) {
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int remainSeconds = 0;
        int secKillStatus = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
            secKillStatus = 0;
        } else if (nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
//        model.addAttribute("remainSeconds", remainSeconds);
//        model.addAttribute("secKillStatus", secKillStatus);
//        model.addAttribute("goods", goodsVo);
//        WebContext context=new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
//        html=thymeleafViewResolver.getTemplateEngine().process("goodsDetail",context);
//        if(!StringUtils.isEmpty(html)){
//            valueOperations.set("goodsDetail",html,60, TimeUnit.SECONDS);
//        }
//        return html;
        DetailVo detailVo=new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }
}
