package com.ypwh.cron.goods.service;

import java.util.List;
import java.util.Map;

import com.ypwh.cron.goods.model.GoodsEntity;
import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;

/** 
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年8月31日 下午2:10:04 
* 类说明 
*/
public interface GoodsService {

	@DataSource(DBContextHolder.SITE_WRITE)
	int updateGoodsBrowseNum(List<GoodsEntity> goodsList);
	
	@DataSource(DBContextHolder.SCM_READ)
	GoodsEntity selectGoodsById(long id);
	
	@DataSource(DBContextHolder.SITE_WRITE)
	int insertStatGoodsDetail(List<Map<String, Object>> goodsList);
}
