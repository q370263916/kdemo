package com.ypwh.cron.msg.bean;

import java.io.Serializable;
import java.math.BigDecimal;

//点心
public class Opt implements Serializable{


	private static final long serialVersionUID = -4353634644161844848L;

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
