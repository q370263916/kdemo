package com.ypwh.cron.msg.service.impl;


import com.ypwh.cron.msg.bean.UserDetailEntity;
import com.ypwh.cron.msg.bean.UserEntity;
import com.ypwh.cron.msg.bean.UserRedBagEntity;
import com.ypwh.cron.msg.dao.UserRedBagMapper;
import com.ypwh.cron.msg.service.UserRedBagService;
import com.ypwh.cron.msg.util.TimeUtil;
import com.ypwh.framework.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
@Service(value = "userRedBagService")
public class UserRedbagServiceServiceImpl implements UserRedBagService {
	private static final Logger logger = LoggerFactory.getLogger(UserRedbagServiceServiceImpl.class);
	@Autowired
	private UserRedBagMapper userRedBagMapper;





	@Override
	public List<UserRedBagEntity> listUserRedBag(UserRedBagEntity entity) {
		String endDate=DateUtil.dateToStr(new Date())+" 12:00:00";
		//当天24小时前
		Date date= TimeUtil.dealDate(new Date(),-24);
		String startDate=DateUtil.dateToStr(date)+" 12:00:00";
		logger.info("----listUserRedBag---startDate"+startDate+"------endDate-------"+endDate);
		return userRedBagMapper.listUserRedBag(startDate,endDate,entity.getId());
	}

	@Override
	public List<UserRedBagEntity> listUserRedBagGreaterDay(UserRedBagEntity entity) {

		Date date= TimeUtil.dealDate(new Date(),24);
		String startDate=DateUtil.dateToStr(date)+" 16:00:00";
		Date date2= TimeUtil.dealDate(new Date(),48);
		String endDate=DateUtil.dateToStr(date2)+" 16:00:00";
		logger.info("----listUserRedBagGreaterDay---startDate"+startDate+"------endDate-------"+endDate);
		return userRedBagMapper.listUserRedBagGreaterDay(startDate,endDate,entity.getId());
	}

	@Override
	public List<UserRedBagEntity> listUserRedBagLessDay(UserRedBagEntity entity) {
		String startDate=DateUtil.dateToStr(new Date())+" 16:00:00";

		Date date2= TimeUtil.dealDate(new Date(),24);
		String endDate=DateUtil.dateToStr(date2)+" 16:00:00";
		logger.info("----listUserRedBagGreaterDay---startDate"+startDate+"------endDate-------"+endDate);
		return userRedBagMapper.listUserRedBagLessDay(startDate,endDate,entity.getId());
	}

	@Override
	public List<UserEntity> listUser(List<Long> userIds) {
		return userRedBagMapper.listUser(userIds);
	}

	@Override
	public List<UserDetailEntity> listUserDetail(List<Long> userIds) {
		return userRedBagMapper.listUserDetail(userIds);
	}
}
