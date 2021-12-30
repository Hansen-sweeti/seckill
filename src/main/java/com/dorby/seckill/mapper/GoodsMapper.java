package com.dorby.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dorby.seckill.pojo.Goods;
import com.dorby.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dorby
 * @since 2021-12-17
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    GoodsVo findGoodsVoByGoodsId(Long goodsId);


    List<GoodsVo> findGooodsVo();
}
