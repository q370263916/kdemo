package com.ypwh.cron.chuda.service;

import java.util.List;

import com.ypwh.cron.msg.util.DBContextHolder;
import com.ypwh.db.DataSource;

public interface ChuDaService {
	@DataSource(DBContextHolder.SITE_READ)
	public void run(int typeId,int sendType);
	
	public void  productKafKa(int typeId,int sendType);
	
	public void delRedis();
	
	
	public long  haveSend(String userId,String pushId);
	
	public long  haveSendSys(String userId);
	public boolean  judgeWorkTime(List<String> userId,int sendType);
	
	public boolean isSend();
}
