package com.dorby.seckill.rabbitmq;

import com.dorby.seckill.pojo.SeckillMessage;
import com.dorby.seckill.pojo.SeckillOrder;
import com.dorby.seckill.pojo.User;
import com.dorby.seckill.service.IGoodsService;
import com.dorby.seckill.service.IOrderService;
import com.dorby.seckill.utils.JsonUtil;
import com.dorby.seckill.vo.GoodsVo;
import com.dorby.seckill.vo.RespBean;
import com.dorby.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: dorby
 * @Description: 消息消费者
 * @Date: 2021/12/24 20:10
 */
@Slf4j
@Service
public class MQReceiver {


    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IOrderService orderService;


    /*@RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息:"+msg);
    }*/

    /**
    * @author:      dorby
    * @Description:  下单操作
    * @data:     2021/12/25-14:34
    */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接收消息:"+message);
        SeckillMessage seckillMessage= (SeckillMessage) JsonUtil.getInstance().json2obj(message,SeckillMessage.class);
        Long goodsId=seckillMessage.getGoodsId();
        User user=seckillMessage.getUser();
        GoodsVo goodsVo=goodsService.findGoodsVoByGoodsId(goodsId);
        if(goodsVo.getStockCount()<1){
            redisTemplate.opsForValue().set("isStockEmpty:"+goodsVo.getId(),0);
            return;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder=(SeckillOrder) redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
        if(seckillOrder!=null){
            return;
        }
        //下单操作
        orderService.seckill(user,goodsVo);
    }
}
