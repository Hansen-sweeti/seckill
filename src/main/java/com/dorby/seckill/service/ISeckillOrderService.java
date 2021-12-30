package com.dorby.seckill.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dorby.seckill.pojo.SeckillGoods;
import com.dorby.seckill.pojo.SeckillOrder;
import com.dorby.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dorby
 * @since 2021-12-17
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {
    /**
    * @author:      dorby
    * @Description:  获取秒杀结果
    * @data:     2021/12/25-14:59
    */
    Long getResult(User user, Long goodsId);
}
