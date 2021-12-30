package com.dorby.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dorby.seckill.pojo.Order;
import com.dorby.seckill.pojo.User;
import com.dorby.seckill.vo.GoodsVo;
import com.dorby.seckill.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dorby
 * @since 2021-12-17
 */
public interface IOrderService extends IService<Order> {
    /**
    * @author:      dorby
    * @Description:  秒杀
    *
    */
    Order seckill(User user, GoodsVo goods);


    /**
    * @author:      dorby
    * @Description:  订单详情
    *
    */
    OrderDetailVo detail(Long orderId);
    /**
    * @author:      dorby
    * @Description:  获取秒杀地址
    * @data:     2021/12/27-11:27
    */
    String createPath(User user, Long goodsId);


    Boolean checkPath(User user, Long goodsId, String path);

    /**
    * @author:      dorby
    * @Description:  校验验证码
    * @data:     2021/12/27-13:35
    */
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
