package com.dorby.seckill.vo;

import com.dorby.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dorby
 * @Description: 订单详情返回对象
 * @Date: 2021/12/21 9:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private Order order;
    private GoodsVo goodsVo;
}
