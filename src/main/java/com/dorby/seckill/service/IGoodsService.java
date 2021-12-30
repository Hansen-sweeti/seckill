package com.dorby.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dorby.seckill.mapper.GoodsMapper;
import com.dorby.seckill.pojo.Goods;
import com.dorby.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dorby
 * @since 2021-12-17
 */
public interface IGoodsService extends IService<Goods> {
    /**
    * @author:      dorby
    * @Description:  获取商品列表
    *
    */
    List<GoodsVo> findGoodsVo();
    /**
    * @author:      dorby
    * @Description:  获取商品详情
    *
    */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
