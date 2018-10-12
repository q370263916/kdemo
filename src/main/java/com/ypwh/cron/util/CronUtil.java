package com.ypwh.cron.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年9月3日 上午11:09:08 
* 类说明 
*/
public class CronUtil {
	
	/**
	 * 日期格式化格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 日期格式化格式
	 */
	public static final String DATE_FORMAT_TOGETHER = "yyyyMMdd";

	/**
	 * 精确到秒的日期时间格式化的格式字符串
	 */
	public static final String FMT_DATETIME_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 精确到秒的日期时间格式化的格式字符串
	 */
	public static final String FMT_DATETIME_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	/**
	 * 日期格式化格式
	 */
	public static final String DATE_FORMAT_HM = "yyyy/MM/dd HH:mm";

	/**
	 * 中文格式化
	 */
	public static final String DATE_MMDDHHMM = "MM月dd日HH:mm";

	/*
	 * days 几天
	 * model 1 为后几天 -1 为前几天
	 */
	public static Date getDateByDays(int days, int model){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days * model);
		return cal.getTime();
	}
	
	public static String getDateFormat(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}
		
}
