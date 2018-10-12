package com.ypwh.cron.util;

import java.util.Set;

import redis.clients.jedis.Tuple;

/** 
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年8月31日 下午4:50:31 
* 类说明 
*/
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String date = CronUtil.getDateFormat(CronUtil.getDateByDays(1, -1), CronUtil.DATE_FORMAT_TOGETHER);
		String key = Constants.GOODS_BROWSE_NUM_ONE_DAY + date;
		RankRedisUtil.removeKey(key);
		RankRedisUtil.zincrby(key, 1, "234232721");
		RankRedisUtil.zincrby(key, 2, "234232720");
		RankRedisUtil.zincrby(key, 3, "234232719");
		RankRedisUtil.zincrby(key, 4, "234232718");
		RankRedisUtil.zincrby(key, 5, "234232717");
		RankRedisUtil.zincrby(key, 6, "234232716");
		RankRedisUtil.zincrby(key, 7, "234232715");
		RankRedisUtil.zincrby(key, 8, "234232714");
		RankRedisUtil.zincrby(key, 9, "234232713");
		RankRedisUtil.zincrby(key, 10, "234232712");
		RankRedisUtil.zincrby(key, 11, "234232711");
		RankRedisUtil.zincrby(key, 12, "234232710");
		System.out.println(RankRedisUtil.zscore(key, "2"));
		System.out.println(RankRedisUtil.zcard(key));
		Set<Tuple> tset = RankRedisUtil.zrangeWithScores(key, 2, 10);
		for(Tuple t : tset){
			System.out.println(t.getElement());
			System.out.println(t.getScore());
		}
	}

}
