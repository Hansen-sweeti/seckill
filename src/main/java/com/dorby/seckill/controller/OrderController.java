package com.dorby.seckill.controller;


import com.dorby.seckill.pojo.User;
import com.dorby.seckill.service.IOrderService;
import com.dorby.seckill.vo.OrderDetailVo;
import com.dorby.seckill.vo.RespBean;
import com.dorby.seckill.vo.RespBeanEnum;
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
 * @since 2021-12-17
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

        /**
        * @author:      dorby
        * @Description:  订单详情
        *
        */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail=orderService.detail(orderId);
        return RespBean.success(detail);
    }

}
