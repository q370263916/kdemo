package com.ypwh.cron.msg.service.impl;

import com.ypwh.common.constant.CacheKey;
import com.ypwh.cron.msg.bean.KaflkaEnum;
import com.ypwh.cron.msg.bean.UserBonusEntity;
import com.ypwh.cron.msg.bean.UserRedBagEntity;
import com.ypwh.cron.msg.dao.UserBonusMapper;
import com.ypwh.cron.msg.service.UserBonusService;
import com.ypwh.cron.msg.service.UserRedBagService;
import com.ypwh.cron.msg.util.KafkaProductServer;
import com.ypwh.cron.msg.util.SMSRedisUtil;
import com.ypwh.cron.msg.util.TimeUtil;
import com.ypwh.framework.common.util.Check;
import com.ypwh.framework.common.util.DateUtil;
import com.ypwh.framework.common.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserBonusServiceServiceImpl implements UserBonusService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserBonusServiceServiceImpl.class);
	@Autowired
	private UserBonusMapper userBonusMapper;

	@Autowired
	private UserRedBagService userRedBagService;

	@Autowired
	private KafkaProductServer kafkaProductServer;

	@Override
	public List<UserBonusEntity> listUserBonus(UserBonusEntity entity) {
		String endDate = DateUtil.dateToStr(new Date())+" 12:00:00";
		// 当天24小时前
		Date date = TimeUtil.dealDate(new Date(), -24);
		String startDate = DateUtil.dateToStr(date)+" 12:00:00";
		logger.info("----listUserBonus---startDate" + startDate
				+ "------endDate-------" + endDate);

		List<UserBonusEntity> list = userBonusMapper.listUserBonus(startDate,
				endDate, entity.getId());

		return list;
	}

	@Override
	public List<UserBonusEntity> listUserBonusGreaterDay(UserBonusEntity entity) {
		Date date = TimeUtil.dealDate(new Date(), 24);
		String startDate = DateUtil.dateToStr(date)+" 16:00:00";
		Date date2 = TimeUtil.dealDate(new Date(), 48);
		String endDate = DateUtil.dateToStr(date2)+" 16:00:00";
		logger.info("----listUserBonusGreaterDay---startDate" + startDate
				+ "------endDate-------" + endDate);
		return userBonusMapper.listUserBonusGreaterDay(startDate, endDate,
				entity.getId());
	}

	@Override
	public List<UserBonusEntity> listUserBonusLessDay(UserBonusEntity entity) {
		String startDate = DateUtil.dateToStr(new Date())+" 16:00:00";
		Date date = TimeUtil.dealDate(new Date(), 24);
		String endDate = DateUtil.dateToStr(date)+" 16:00:00";
		logger.info("----listUserBonusLessDay---startDate" + startDate
				+ "------endDate-------" + endDate);
		return userBonusMapper.listUserBonusLessDay(startDate, endDate,
				entity.getId());
	}

	@Override
	public void doArrive() {
		String keyField = "userIds";
		String key = CacheKey.generateKey(CacheKey.KEY_PREFIX_GOODSNOTIFY,
				keyField);
		String arrvicekey = CacheKey.generateKey(
				CacheKey.KEY_PREFIX_GOODSNOTIFY, "userIds:arrvice");
		String expkey = CacheKey.generateKey(CacheKey.KEY_PREFIX_GOODSNOTIFY,
				"userIds:expire");
		String dateStr = DateUtil.dateToStr(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		UserBonusEntity ube = new UserBonusEntity();
		UserRedBagEntity brb = new UserRedBagEntity();
		List<UserBonusEntity> list = new ArrayList<UserBonusEntity>();
		List<UserRedBagEntity> bagList = new ArrayList<UserRedBagEntity>();
		List<Long> newlistBon = new ArrayList<Long>();
		List<Long> newlistRed = new ArrayList<Long>();
		List<Long> explist = new ArrayList<Long>();
		List<Long> daylist = new ArrayList<Long>();
		
		String userIdsArr = (String) SMSRedisUtil.get(arrvicekey);
		String userIdsExp = (String) SMSRedisUtil.get(expkey);
		String arrTime = "";
		List<Long> arrList = new ArrayList<Long>();
		if (!Check.NuNString(userIdsArr) || !Check.NuNString(userIdsExp)) {

			if (!Check.NuNString(userIdsArr)) {
				JSONObject obj = JSONObject.fromObject(userIdsArr);
				arrTime = obj.getString("time");
				String arrStr = obj.getString("userIds");
				JSONArray jsonArray = JSONArray.fromObject(arrStr);
				arrList = JSONArray.toList(jsonArray, Long.class);
			}
			if (!Check.NuNString(userIdsExp)) {
				JSONObject obj2 = JSONObject.fromObject(userIdsExp);
				String expStr = obj2.getString("userIds");
				if (userIdsExp.indexOf(dateStr) > 0) {
					String dayStr = obj2.getString(dateStr);
					JSONArray dayArr = JSONArray.fromObject(dayStr);
					daylist = JSONArray.toList(dayArr, Long.class);
				}
				JSONArray expArr = JSONArray.fromObject(expStr);
				explist = JSONArray.toList(expArr, Long.class);
			}
		}
        long bonusStart=-1;
        long redbagStart=-1;
		LocalDateTime nowTime = LocalDateTime.now();
		int hour=nowTime.getHour();
		while (true) {
			if(21<=hour){
	            logger.info(" 22:00 不推送");
	            break;
	        }
			logger.info("bonusId={},redbagId={}",ube.getId(),brb.getId());
			if(bonusStart<=ube.getId()){
			   list = this.listUserBonus(ube);
			}
			if(redbagStart<=brb.getId()){
			    bagList = userRedBagService.listUserRedBag(brb);
			}
			
			if (null != list && list.size() > 1) {
				
				ube.setId(list.get(list.size() - 1).getId()); // 去最后一个id
				logger.info("bonusId={}",ube.getId());
			}
			UserRedBagEntity brbNext = new UserRedBagEntity();
			if (null != bagList && bagList.size() > 1) {
				brb.setId(bagList.get(bagList.size() - 1).getId());
				logger.info("redbagId={}",brb.getId());
			}
			if(bonusStart==ube.getId()&&redbagStart==brb.getId()){
				break;
			}
			bonusStart=ube.getId();
			redbagStart=brb.getId();
			// 红包 优惠券只发一个
			if (null != list && list.size() > 0) {
				for (UserBonusEntity ub : list) {
					long userId = ub.getUserId();
					logger.info("111111111111111111111=============bonusId={}",ub.getId());
					newlistBon.add(userId);
				}
			}
			if (null != bagList && bagList.size() > 0) {
				for (UserRedBagEntity ur : bagList) {
					logger.info("111111111111111111111=============redbagId={}",ur.getId());
					long userId = ur.getUserId();
					newlistRed.add(userId);
				}
			}
			if ((null == list || list.size() <= 0)
					&& (null == bagList || bagList.size() <= 0)) {
				break;
			}
			
			// 查询历史id
			if (dateStr.equals(arrTime)) { // 同一天
				logger.info("0000000============dateStr={} arrTime={}",dateStr,arrTime);
				newlistRed.removeAll(arrList); // 去除历史发过的红包id
				newlistRed.removeAll(explist); // 去除发过到期的
				newlistBon.removeAll(arrList); // 去除历史发过的优惠券id
				newlistBon.removeAll(explist);// 去除发过到期的
				newlistBon.removeAll(newlistRed);// 去除相同的id
			} else {
				logger.info("00000001111111============dateStr={} arrTime={}",dateStr,arrTime);

				Set<Long> set = new HashSet<Long>(arrList); // 去重
				Collection rs = CollectionUtils.disjunction(arrList, set); // 去差集
				List<Long> cfList = new ArrayList<Long>(rs);
				newlistRed.removeAll(daylist); // 去除发过到期的
				newlistRed.removeAll(cfList); // 去除发过2次的红包
				newlistBon.removeAll(daylist);// 去除发过到期的
				newlistBon.removeAll(cfList); // 去除发过2次的优惠券
				newlistBon.removeAll(newlistRed);// 去除相同的id
			}

			if (null != newlistBon && newlistBon.size() > 0) {
				kafkaProductServer.sndMesForTemplate(
						KaflkaEnum.ABONTOPIC.getTopic(),
						String.valueOf(System.currentTimeMillis()),
						JsonUtil.toJson(newlistBon), "0", 1);
				arrList.addAll(newlistBon);
				daylist.addAll(newlistBon);
			}
			newlistRed.removeAll(newlistBon);
			if (null != newlistRed && newlistRed.size() > 0) {
				kafkaProductServer.sndMesForTemplate(
						KaflkaEnum.AREDTOPIC.getTopic(),
						String.valueOf(System.currentTimeMillis()),
						JsonUtil.toJson(newlistRed), "0", 1);
				arrList.addAll(newlistRed);
				daylist.addAll(newlistRed);
			}
		}
		if (!Check.NuNString(userIdsArr)) {
			// 已经发过
			if (!dateStr.equals(arrTime)) { // 不是同一天
				map.put(dateStr, daylist);
				newlistBon.addAll(arrList); // 在合并历史的所有id

				logger.info("----2222-----dateStr" + dateStr
						+ "----------------arrTime" + arrTime);
				map.put("time", dateStr);
				map.put("userIds", arrList);
				long arrtime = SMSRedisUtil.ttl(arrvicekey);
				if (null != newlistBon && newlistBon.size() > 0) {
					SMSRedisUtil.setAndExpire(arrvicekey, JsonUtil.toJson(map),
							(int) arrtime);
				}
			}
		} else { // 没有历史数据
			
			logger.info("----------dateSt---cache insert----1111-------arrTime");
			map.put(dateStr, daylist);
			map.put("time", dateStr);
			map.put("userIds", arrList);
			if (null != daylist && daylist.size() > 0) {
				SMSRedisUtil.setAndExpire(arrvicekey, JsonUtil.toJson(map),
						7 * 24 * 60 * 60);
			}

		}

	}

	@Override
	public void doExpire() {
		String keyField = "userIds:arrvice";
		String arrkey = CacheKey.generateKey(CacheKey.KEY_PREFIX_GOODSNOTIFY,
				keyField);
		String expirekey = CacheKey.generateKey(
				CacheKey.KEY_PREFIX_GOODSNOTIFY, "userIds:expire");
		Map<String, Object> map = new HashMap<String, Object>();
		UserBonusEntity ube = new UserBonusEntity();
		UserRedBagEntity brb = new UserRedBagEntity();
		List<UserBonusEntity> greaterBon = new ArrayList<UserBonusEntity>();
		List<UserBonusEntity> lessBon = new ArrayList<UserBonusEntity>();
		List<UserRedBagEntity> greaterRed = new ArrayList<UserRedBagEntity>();
		List<UserRedBagEntity> lessRed = new ArrayList<UserRedBagEntity>();
		List<Long> pagelist = new ArrayList<Long>();
		List<Long> explist = new ArrayList<Long>();
		List<Long> arrlist = new ArrayList<Long>();
		List<Long> daylist = new ArrayList<Long>();
		String dateStr = DateUtil.dateToStr(new Date());
		ube.setId(0L);
		brb.setId(0L);
		greaterBon = this.listUserBonusGreaterDay(ube);
		lessBon = this.listUserBonusLessDay(ube);
		greaterRed = userRedBagService.listUserRedBagGreaterDay(brb);
		lessRed = userRedBagService.listUserRedBagLessDay(brb);
		List<Long> listBon = new ArrayList<Long>();
		List<Long> listRed = new ArrayList<Long>();
		List<Long> bonlistTemp = new ArrayList<Long>();
		List<Long> redlistTemp = new ArrayList<Long>();
		LocalDateTime nowTime = LocalDateTime.now();
		int hour=nowTime.getHour();
		while (true) {
			if(21<=hour){
	            logger.info(" 22:00 不推送");
	            break;
	        }
			if (null != greaterBon && greaterBon.size() > 0) {
				for (UserBonusEntity ub : greaterBon) {
					long userId = ub.getUserId();
					bonlistTemp.add(userId);
				}
			}
			if (null != lessBon && lessBon.size() > 0) {
				for (UserBonusEntity ub : lessBon) {
					long userId = ub.getUserId();
					bonlistTemp.add(userId);
				}
			}
			if (null != greaterRed && greaterRed.size() > 0) {
				for (UserRedBagEntity ub : greaterRed) {
					long userId = ub.getUserId();
					redlistTemp.add(userId);
				}
			}
			if (null != lessRed && lessRed.size() > 0) {
				for (UserRedBagEntity ub : lessRed) {
					long userId = ub.getUserId();
					redlistTemp.add(userId);
				}
			}
			// 是否 有重复用户 true 有 flase 无
			for (int i = 0; i < bonlistTemp.size(); i++) {
				if (!listBon.contains(bonlistTemp.get(i))) {
					listBon.add(bonlistTemp.get(i));
				}
			}
			for (int i = 0; i < redlistTemp.size(); i++) {
				if (!listRed.contains(redlistTemp.get(i))) {
					listRed.add(redlistTemp.get(i));
				}
			}
			boolean bol = this.compareList(listBon, listRed);
			if (bol) {
				// 查询历史id
				String userIdsArr = (String) SMSRedisUtil.get(arrkey);
				String userIdsExp = (String) SMSRedisUtil.get(expirekey);
				if (!Check.NuNString(userIdsArr)
						|| !Check.NuNString(userIdsExp)) {

					String arrTime = "";
					if (!Check.NuNString(userIdsArr)) {
						JSONObject obj = JSONObject.fromObject(userIdsArr);
						String arrStr = obj.getString("userIds");
						if (userIdsArr.indexOf(dateStr) > 0) {
							String datStr = obj.getString(dateStr);
							JSONArray dayArray = JSONArray.fromObject(datStr);
							daylist = JSONArray.toList(dayArray, Long.class);
						}
						arrTime = obj.getString("time");
						JSONArray jsonArray = JSONArray.fromObject(arrStr);
						arrlist = JSONArray.toList(jsonArray, Long.class);

					}
					String expTime = "";
					if (!Check.NuNString(userIdsExp)) {
						JSONObject obj2 = JSONObject.fromObject(userIdsExp);
						String expStr = obj2.getString("userIds");
						expTime = obj2.getString("time");
						JSONArray expArr = JSONArray.fromObject(expStr);
						explist = JSONArray.toList(expArr, Long.class);
					}
					if (expTime.equals(dateStr))// 同一天
					{
						listBon.removeAll(arrlist); // 去除历史发过的红包id
						listBon.removeAll(explist);
						listRed.removeAll(arrlist); // 去除历史发过的优惠券id
						listRed.removeAll(explist);
						listRed.removeAll(listBon);// 去除相同的id 优先发红包到期
					} else {
						if (arrTime.equals(dateStr)) {
							Set<Long> set = new HashSet<Long>(explist); // 去重
							Collection rs = CollectionUtils.disjunction(
									explist, set); // 去差集
							List<Long> cfList = new ArrayList<Long>(rs);

							// Set<Long> arrset = new HashSet<Long>(arrlist);
							// //去重
							// Collection arrrs =
							// CollectionUtils.disjunction(arrlist,arrset);
							// //去差集
							// List<Long> arrCfList=new ArrayList<Long>(arrrs);
							// //发过2次
							// arrlist.removeAll(arrCfList); //剔除发送2次的
							listBon.removeAll(daylist); // 去除历史发过的红包id
							listBon.removeAll(cfList); // 去除2次发送的优惠券
							listBon.removeAll(pagelist);
							listRed.removeAll(daylist); // 去除历史发过的优惠券id
							listRed.removeAll(cfList);// 去除2次发生的红包
							// listRed.removeAll(listBon);//去除相同的id 优先发红包到期
							listRed.removeAll(daylist);
							listBon.removeAll(listRed);
						} else {
							Set<Long> set = new HashSet<Long>(explist); // 去重
							Collection rs = CollectionUtils.disjunction(
									explist, set); // 去差集
							List<Long> cfList = new ArrayList<Long>(rs);
							listBon.removeAll(cfList); // 去除2次发送的优惠券
							listBon.removeAll(pagelist);
							listRed.removeAll(cfList);// 去除2次发生的红包
							listRed.removeAll(pagelist);
							// listRed.removeAll(listBon);//去除相同的id 优先发红包到期
							listBon.removeAll(listRed);
						}
					}
					if (null != listBon && listBon.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EBONTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listBon), "0", 1);
						pagelist.addAll(listBon);
					}
					listRed.removeAll(listBon);
					if (null != listRed && listRed.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EREDTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listRed), "0", 1);
						pagelist.addAll(listRed);
					}
				} else { // 没有历史数据
					listBon.removeAll(listRed);// 去除相同的id 优先发红包
					if (null != listRed && listRed.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EREDTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listRed), "0", 1);
						pagelist.addAll(listRed);
					}
					// listBon.removeAll(listRed);
					if (null != listBon && listBon.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EBONTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listBon), "0", 1);
						pagelist.addAll(listBon);
					}

				}
			} else {
				// 历史发送过的
				String userIdArr = (String) SMSRedisUtil.get(arrkey);
				String userIdsExp = (String) SMSRedisUtil.get(expirekey);
				if (!Check.NuNString(userIdArr) || !Check.NuNString(userIdsExp)) {
					String arrTime = "";
					if (!Check.NuNString(userIdArr)) {
						JSONObject obj = JSONObject.fromObject(userIdArr);
						String arrStr = obj.getString("userIds");
						if (userIdArr.indexOf(dateStr) > 0) {
							String datStr = obj.getString(dateStr);
							JSONArray dayArray = JSONArray.fromObject(datStr);
							daylist = JSONArray.toList(dayArray, Long.class);
						}
						arrTime = obj.getString("time");
						JSONArray jsonArray = JSONArray.fromObject(arrStr);
						arrlist = JSONArray.toList(jsonArray, Long.class);
					}
					String expTime = "";
					if (!Check.NuNString(userIdsExp)) {
						JSONObject obj2 = JSONObject.fromObject(userIdsExp);
						String expStr = obj2.getString("userIds");
						expTime = obj2.getString("time");
						JSONArray expArr = JSONArray.fromObject(expStr);
						explist = JSONArray.toList(expArr, Long.class);
					}
					if (expTime.equals(dateStr))// 同一天
					{
						listBon.removeAll(arrlist); // 剔除已经发过的
						listBon.removeAll(explist);
						listRed.removeAll(arrlist); // 剔除已经发送过的
						listRed.removeAll(explist);
					} else {
						if (arrTime.equals(dateStr)) {
							Set<Long> set = new HashSet<Long>(explist); // 去重
							Collection rs = CollectionUtils.disjunction(
									explist, set); // 去差集
							List<Long> cfList = new ArrayList<Long>(rs);
							// listBon.removeAll(explist); // 剔除已经发过的
							listBon.removeAll(daylist);
							listBon.removeAll(cfList);
							listBon.removeAll(pagelist);
							// listRed.removeAll(explist);
							listRed.removeAll(daylist);
							listRed.removeAll(cfList);
							listRed.removeAll(pagelist);
						} else {
							Set<Long> set = new HashSet<Long>(explist); // 去重
							Collection rs = CollectionUtils.disjunction(
									explist, set); // 去差集
							List<Long> cfList = new ArrayList<Long>(rs);
							listBon.removeAll(explist); // 剔除已经发过的
							listBon.removeAll(cfList);
							listBon.removeAll(pagelist);
							listRed.removeAll(explist);
							listRed.removeAll(cfList);
							listRed.removeAll(pagelist);
						}

					}
					if (null != listBon && listBon.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EBONTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listBon), "0", 1);
						pagelist.addAll(listBon);
					}
					listRed.removeAll(listBon);
					if (null != listRed && listRed.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EREDTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listRed), "0", 1);
						pagelist.addAll(listRed);
					}
				} else {
					if (null != listBon && listBon.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EBONTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listBon), "0", 1);
						pagelist.addAll(listBon);

					}
					if (null != listRed && listRed.size() > 0) {
						kafkaProductServer.sndMesForTemplate(
								KaflkaEnum.EREDTOPIC.getTopic(),
								String.valueOf(System.currentTimeMillis()),
								JsonUtil.toJson(listRed), "0", 1);
						listBon.addAll(listRed);
					}
				}
			}
			if ((null == greaterBon || greaterBon.size() <= 0)
					&& (null == lessBon || lessBon.size() <= 0)
					&& (null == greaterRed || greaterRed.size() <= 0)
					&& (null == lessRed || lessRed.size() <= 0)) {
				break;
			} else {
				UserBonusEntity ubeNext = new UserBonusEntity();
				UserRedBagEntity brbNext = new UserRedBagEntity();
				if (null != greaterBon && greaterBon.size() > 0) {
					ubeNext.setId(greaterBon.get(greaterBon.size() - 1).getId());
					greaterBon = this.listUserBonusGreaterDay(ubeNext);
				}
				if (null != lessBon && lessBon.size() > 0) {
					ubeNext.setId(lessBon.get(lessBon.size() - 1).getId());
					lessBon = this.listUserBonusLessDay(ubeNext);
				}
				if (null != greaterRed && greaterRed.size() > 0) {
					brbNext.setId(greaterRed.get(greaterRed.size() - 1).getId());
					greaterRed = userRedBagService
							.listUserRedBagGreaterDay(brbNext);
				}
				if (null != lessRed && lessRed.size() > 0) {
					brbNext.setId(lessRed.get(lessRed.size() - 1).getId());
					lessRed = userRedBagService.listUserRedBagLessDay(brbNext);
				}
				if ((null == greaterBon || greaterBon.size() <= 0)
						&& (null == lessBon || lessBon.size() <= 0)
						&& (null == greaterRed || greaterRed.size() <= 0)
						&& (null == lessRed || lessRed.size() <= 0)) {
					break;
				}
			}
		}
		String userIdsExp = (String) SMSRedisUtil.get(expirekey);
		String expTime = "";
		if (!Check.NuNString(userIdsExp)) {
			JSONObject obj2 = JSONObject.fromObject(userIdsExp);
			String expStr = obj2.getString("userIds");
			expTime = obj2.getString("time");
			JSONArray expArr = JSONArray.fromObject(expStr);
			explist = JSONArray.toList(expArr, Long.class);
			if (!expTime.equals(dateStr)) {// 不同一天
				listRed.addAll(listBon);// 合并本次发送的所有id
				List<Long> dayList = new ArrayList<Long>();
				dayList.addAll(listRed);
				map.put(dateStr, dayList);
				listRed.addAll(explist);
				map.put("time", dateStr);
				map.put("userIds", listRed);
				long exTime = SMSRedisUtil.ttl(expirekey);
				if (null != listRed && listRed.size() > 0) {
					SMSRedisUtil.setAndExpire(expirekey, JsonUtil.toJson(map),
							(int) exTime);
				}
			}
		} else {
			listBon.addAll(listRed);
			List<Long> dayList = new ArrayList<Long>();
			dayList.addAll(listBon);
			map.put(dateStr, dayList);
			map.put("time", dateStr);
			map.put("userIds", listBon);
			if (null != listBon && listBon.size() > 0) {
				SMSRedisUtil.setAndExpire(expirekey, JsonUtil.toJson(map),
						7 * 24 * 60 * 60);
			}
		}
	}

	public boolean compareList(List arg1, List arg2) {
		boolean flag = Collections.disjoint(arg1, arg2);
		// ture 没有 flase 有
		return !flag;
	}

	
}
