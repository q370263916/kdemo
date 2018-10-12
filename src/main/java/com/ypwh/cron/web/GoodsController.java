package com.ypwh.cron.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ypwh.cron.goods.model.GoodsEntity;
import com.ypwh.cron.goods.service.GoodsService;
import com.ypwh.cron.util.Constants;
import com.ypwh.cron.util.CronUtil;
import com.ypwh.cron.util.RankRedisUtil;
import com.ypwh.cron.util.mail.MailUtil;

import redis.clients.jedis.Tuple;


/**
 * 拼团
 * @author zgz_j
 *
 */
@Controller
@RequestMapping("/task/goods")
public class GoodsController{
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
	
	@Autowired
	GoodsService goodsService;
	
	@RequestMapping(value = "/updateBrowseNum")
	@ResponseBody
	public Object updateBrowseNum(HttpServletRequest request, HttpServletResponse response) {
		try{
			String date = CronUtil.getDateFormat(CronUtil.getDateByDays(1, -1), CronUtil.DATE_FORMAT_TOGETHER);
			String key = Constants.GOODS_BROWSE_NUM_ONE_DAY + date;
			Long size = RankRedisUtil.zcard(key);
			int updateAllNum = 0;
			int maxBrowsNum = 0;
			if(size != null && size > 0){
				int length = Constants.GOODS_BROWSE_NUM_UPDATE_ONCE;//每次从缓存取出n个
				int start = 0;
				int end = -1;
				while(end < size - 1){
					end = start + length;
					Set<Tuple> goodsSet = RankRedisUtil.zrangeWithScores(key, start, end);
					start = end + 1;
					if(goodsSet == null || goodsSet.size() == 0){
						continue;
					}
					for(Tuple t : goodsSet){
						if(t.getScore() > maxBrowsNum){
							maxBrowsNum = Double.valueOf(t.getScore()).intValue();
						}
					}
					int updateNum = processGoodsBrowseNum(goodsSet);
					updateAllNum += updateNum;
				}
			}
			logger.info("日期：[" + date + "], size：[" + size + "], updateAllNum：[" + updateAllNum + "], maxBrowsNum：[" + maxBrowsNum + "]");
			MailUtil.send("日期：[" + date + "], size：[" + size + "], updateAllNum：[" + updateAllNum + "], maxBrowsNum：[" + maxBrowsNum + "]", 
					"商品PV", "zhuguangzhu@91sph.com");
			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}

	private int processGoodsBrowseNum(Set<Tuple> goodsSet) {
		try {
			List<GoodsEntity> goodsList = Lists.newArrayList();
			List<Map<String, Object>> goodsStatList = Lists.newArrayList();
			GoodsEntity goods = null;
			Map<String, Object> stat = null;
			for(Tuple t : goodsSet){
				goods = new GoodsEntity();
				stat = Maps.newHashMap();
				stat.put("pv", new Double(t.getScore()).intValue());
				stat.put("goodsId", Long.parseLong(t.getElement()));
				stat.put("date", CronUtil.getDateFormat(CronUtil.getDateByDays(1, -1), CronUtil.DATE_FORMAT_TOGETHER));
				stat.put("site", 99);
				stat.put("uv", 0);
				stat.put("createTime", new Date());
				goods.setId(Long.parseLong(t.getElement()));
				goods.setBrowseNum(new Double(t.getScore()).intValue());
				goodsList.add(goods);
				goodsStatList.add(stat);
			}
			int statNum = 0;
			if(goodsStatList.size() > 0){
				statNum = goodsService.insertStatGoodsDetail(goodsStatList);
			}
			int goodsNum = 0;
			if(goodsList.size() > 0){
				goodsNum = goodsService.updateGoodsBrowseNum(goodsList);
			}
			return statNum > goodsNum ? goodsNum : statNum;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("processGoodsBrowseNum error", e);
		}
		return 0;
	}
	

}
