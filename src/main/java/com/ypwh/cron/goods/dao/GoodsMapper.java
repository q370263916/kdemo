package com.ypwh.cron.goods.dao;

import java.util.List;
import java.util.Map;

import com.ypwh.cron.goods.model.GoodsEntity;

public interface GoodsMapper {
	
    int updateGoodsBrowseNum(List<GoodsEntity> goodsList);
    
    GoodsEntity selectGoodsById(long goodsId);
    
    List<GoodsEntity> selectGoodsByIds(String goodsIds);
    
    int insertStatGoodsDetail(List<Map<String, Object>> goodsList);
}
	