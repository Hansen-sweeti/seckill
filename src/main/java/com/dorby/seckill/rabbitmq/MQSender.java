package com.dorby.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: dorby
 * @Description: 消息发送者
 * @Date: 2021/12/24 20:06
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
    public void send(Object msg){
        log.info("发送消息："+msg);
        rabbitTemplate.convertAndSend("queue",msg);
    }*/

    public void sendSeckillMessage(String message){
        log.info("发送消息:"+message);
        rabbitTemplate.convertAndSend("seckillExchange","seckill.message",message);
    }
}
