package com.ypwh.cron.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ypwh.cron.goods.dao.GoodsMapper;
import com.ypwh.cron.goods.model.GoodsEntity;
import com.ypwh.cron.goods.service.GoodsService;

/** 
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年8月31日 下午2:11:43 
* 类说明 
*/
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsMapper goodsMapper;
	
	@Override
	public int updateGoodsBrowseNum(List<GoodsEntity> goodsList) {
		// TODO Auto-generated method stub
		if(CollectionUtils.isEmpty(goodsList)){
			return 0;
		}
		return goodsMapper.updateGoodsBrowseNum(goodsList);
	}

	@Override
	public GoodsEntity selectGoodsById(long id) {
		// TODO Auto-generated method stub
		return goodsMapper.selectGoodsById(id);
	}

	@Override
	public int insertStatGoodsDetail(List<Map<String, Object>> goodsList) {
		// TODO Auto-generated method stub
		return goodsMapper.insertStatGoodsDetail(goodsList);
	}

}
