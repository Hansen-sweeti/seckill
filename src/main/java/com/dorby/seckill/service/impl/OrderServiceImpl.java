package com.dorby.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dorby.seckill.exception.GlobalException;
import com.dorby.seckill.mapper.OrderMapper;
import com.dorby.seckill.pojo.Order;
import com.dorby.seckill.pojo.SeckillGoods;
import com.dorby.seckill.pojo.SeckillOrder;
import com.dorby.seckill.pojo.User;
import com.dorby.seckill.service.IGoodsService;
import com.dorby.seckill.service.IOrderService;
import com.dorby.seckill.service.ISeckillGoodsService;
import com.dorby.seckill.service.ISeckillOrderService;
import com.dorby.seckill.utils.MD5Util;
import com.dorby.seckill.utils.UUIDUtil;
import com.dorby.seckill.vo.GoodsVo;
import com.dorby.seckill.vo.OrderDetailVo;
import com.dorby.seckill.vo.RespBeanEnum;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dorby
 * @since 2021-12-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private OrderMapper orderMapper;

   /*
   不能加，加了会出现循环使用
   @Autowired
    private IOrderService orderService;*/

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;




    /**
    * @author:      dorby
    * @Description:  秒杀
    *
    */
    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goods) {
        //秒杀商品减库存
        SeckillGoods seckillGoods=seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id",goods.getId()));
        /*seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        System.out.println(seckillGoods.getStockCount());
        seckillGoodsService.updateById(seckillGoods);*/
        /*boolean seckillGiidsResult= seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().set("stock_count",
                seckillGoods.getStockCount()).eq("id",seckillGoods.getGoodsId()).gt("stock_count",0));*/
        boolean result=seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().setSql("stock_count"+"= "+"stock_count-1").eq("goods_id",goods.getId()).gt("stock_count",0));
        System.out.println(seckillGoods.getStockCount());
        if(seckillGoods.getStockCount()<1){
            redisTemplate.opsForValue().set("isStockEmpty:"+goods.getId(),0);
            return null;
        }
        //生成订单
        Order order=new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        //生成订单
        orderMapper.insert(order);
        //orderService.save(order);
        //生成秒杀订单
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOderId(order.getId());
        seckillOrderService.save(seckillOrder);
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(),seckillOrder);
        return order;
    }


    /**
    * @author:      dorby
    * @Description:  订单详情
    *
    */

    @Override
    public OrderDetailVo detail(Long orderId){
        if(orderId==null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order=orderMapper.selectById(orderId);
        GoodsVo goodsVo=goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail=new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;
    }


    /**
    * @author:      dorby
    * @Description:  获取秒杀地址
    * @data:     2021/12/27-11:27
    */
    @Override
    public String createPath(User user, Long goodsId) {
        String str=MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);
        return str;
    }



    /**
    * @author:      dorby
    * @Description:  校验秒杀地址
    * @data:     2021/12/27-11:50
    */
    @Override
    public Boolean checkPath(User user, Long goodsId, String path) {
        if(goodsId<0|| StringUtils.isEmpty(path)){
            return false;
        }
        String redisPath= (String) redisTemplate.opsForValue().get("seckillPath:"+user.getId()+":"+goodsId);
        return path.equals(redisPath);
    }

    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if(StringUtils.isEmpty(captcha)||user==null||goodsId<0){
            return  false;
        }
        String redisCaptcha=(String) redisTemplate.opsForValue().get("captcha:"+user.getId()+":"+goodsId);
        return redisCaptcha.equals(captcha);
    }

}
