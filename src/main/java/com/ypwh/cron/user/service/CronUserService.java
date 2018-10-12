package com.ypwh.cron.user.service;

import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;

public interface CronUserService {
	@DataSource(DBContextHolder.SITE_READ)
	public  void initUser();
	
	public String getJpushId(String userId);
}
