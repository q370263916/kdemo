package com.ypwh.cron.msg.bean;

import java.math.BigDecimal;
import java.util.Date;

public class UserRedBagEntity {
	
	private long id;
	private long redbagId;
	private String redbagName;
	private BigDecimal money;
	private long userId;
	private int state; //0 未使用 1,已使用 2已过期
	private Date startTime;
    private Date getTime;
    private Date endTime;
	private Date useTime;
	private String getChannel;

	
	
	
	
	public String getGetChannel() {
		return getChannel;
	}
	public void setGetChannel(String getChannel) {
		this.getChannel = getChannel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRedbagId() {
		return redbagId;
	}
	public void setRedbagId(long redbagId) {
		this.redbagId = redbagId;
	}
	public String getRedbagName() {
		return redbagName;
	}
	public void setRedbagName(String redbagName) {
		this.redbagName = redbagName;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    @Override
    public String toString() {
        return "UserRedBagEntity{" +
                "id=" + id +
                ", redbagId=" + redbagId +
                ", redbagName='" + redbagName + '\'' +
                ", money=" + money +
                ", userId=" + userId +
                ", state=" + state +
                ", startTime=" + startTime +
                ", getTime=" + getTime +
                ", endTime=" + endTime +
                ", useTime=" + useTime +
                ", getChannel='" + getChannel + '\'' +
                '}';
    }
}
