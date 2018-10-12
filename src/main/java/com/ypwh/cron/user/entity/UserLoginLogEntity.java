package com.ypwh.cron.user.entity;

import java.util.Date;

/**
 * 用户登录时间日志
 * @author michael.bai
 * @date 2017年10月19日
 */
public class UserLoginLogEntity {
	private long id;
	private long userId;
	private Date loginTime;
	private String source;//来源
	private int loginType;//类型
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
}