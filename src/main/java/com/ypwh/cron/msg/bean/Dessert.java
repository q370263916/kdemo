package com.ypwh.cron.msg.bean;

import java.io.Serializable;
import java.math.BigDecimal;

//点心
public class Dessert implements Serializable{


	private static final long serialVersionUID = -4353634644161844848L;
	private long id;
	private long userId;
	private BigDecimal score;
	private BigDecimal balance;
	private int isUse;
	private int bizType;
	private BigDecimal oldScore;


	public BigDecimal getOldScore() {
		return oldScore;
	}

	public void setOldScore(BigDecimal oldScore) {
		this.oldScore = oldScore;
	}

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

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
}
