package com.ypwh.cron.chuda.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ypwh.framework.common.util.DateUtil;

public class ChudaUtil {
	public static String getExpireTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -10);	
		calendar.set(Calendar.HOUR_OF_DAY, 0);	 	
		calendar.set(Calendar.MINUTE, 0);	 	
		calendar.set(Calendar.SECOND, 0);		
		calendar.set(Calendar.MILLISECOND, 0); 	
		Date time = calendar.getTime();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(time);	
		System.out.println("过期开始时间："+format);
		return format;
	}
	public static  int  getDays(String startDate,String endDate){
		   long between_days = 0L;
	        try {
	            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(sdf.parse(startDate));
	            long time1 = cal.getTimeInMillis();
	            cal.setTime(sdf.parse(endDate));
	            long time2 = cal.getTimeInMillis();
	            between_days=(time2-time1)/(1000*3600*24);
	            System.out.println();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	        return Integer.parseInt(String.valueOf(between_days));


	}
	public static void main(String[] args) {
		int days=ChudaUtil.getDays("2018-09-27", "2018-09-29");
		System.out.println(days);
		
	}
	
	public static String getNowDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);	 	
		calendar.set(Calendar.MINUTE, 0);	 	
		calendar.set(Calendar.SECOND, 0);		
		calendar.set(Calendar.MILLISECOND, 0); 	
		Date time = calendar.getTime();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(time);	
		System.out.println("当前时间："+format);
		return format;
	}
	public static String getArriveTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);	
		calendar.set(Calendar.HOUR_OF_DAY, 12);	 	
		calendar.set(Calendar.MINUTE, 0);	 	
		calendar.set(Calendar.SECOND, 0);		
		calendar.set(Calendar.MILLISECOND, 0); 	
		Date time = calendar.getTime();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(time);	
		System.out.println("红包开始时间："+format);
		return format;
	}
	
	public static String getArriveEndTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);	 	
		calendar.set(Calendar.MINUTE, 0);	 	
		calendar.set(Calendar.SECOND, 0);		
		calendar.set(Calendar.MILLISECOND, 0); 	
		Date time = calendar.getTime();		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(time);	
		System.out.println("红包开始时间："+format);
		return format;
	}
}
