package com.ypwh.cron.reduction.entity;

import java.util.Date;

public class UserRedBagEntity {
	
	private long id;
	private long userId;
	private int state; //0 未使用 1,已使用 2已过期
	private Date startTime;
    private Date getTime;
    private Date endTime;
	private int getPlatform;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getGetTime() {
		return getTime;
	}
	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getGetPlatform() {
		return getPlatform;
	}
	public void setGetPlatform(int getPlatform) {
		this.getPlatform = getPlatform;
	}

	
}
