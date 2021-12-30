# seckill

若有需要，可以关注公众号： CppCoding
秒杀项目

1.前后端分离，在redis中缓存静态页面

2.使用redis缓存cookie解决单点登陆问题

3.设置随机url、算数验证码功能减少脚本秒杀

4.用验证码进行流量错峰，用rabbitmq进行流量削峰

5.使用lua脚本快速扣减库存，使用tabbitmq异步扣减mysql库存


使用redis缓存静态页面

整个页面传输占用较大，采用前后端分离处理

使用用户id和商品id做唯一索引

通过redis预减库存，减少数据库访问

内存标记减少redis访问

请求进入队列缓存，异步下单

使用redis预减库存，rabbitmq做消息队列进行下单操作

首先将秒杀商品数量读到redis中

用rabbitmq进行异步的下单操作

再用map做内存标记，若是内存不足，直接返回减少redis访问

使用MD5和UUID生成加密随机秒杀url（接口地址隐藏），再用验证码进行削峰操作

使用接口限流，限制每个用户访问次数

