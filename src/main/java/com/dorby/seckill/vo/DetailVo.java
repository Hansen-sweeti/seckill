package com.dorby.seckill.vo;

import com.dorby.seckill.pojo.Goods;
import com.dorby.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: dorby
 * @Description: 返回详情
 * @Date: 2021/12/20 21:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}
