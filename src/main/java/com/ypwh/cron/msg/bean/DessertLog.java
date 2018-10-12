package com.ypwh.cron.msg.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//点心
public class DessertLog implements Serializable{


	private static final long serialVersionUID = 8277224842187688271L;
	private long id;
	private long userId;
	private String  content;
	private int revenueType;
	private BigDecimal income;
	private BigDecimal costs;
	private long tradeId;
	private Date createTime;
	
	private String createTimeStr;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	private int bizType;
	private int sourceType;
	private BigDecimal score;
	private BigDecimal currentScoreTotal;//当前点心总数
	private String dessertConfigName;//变更名称
	private long configId;//点心变更类型
	private int operateType;//操作人类型

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public long getConfigId() {
		return configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public DessertLog(){}

	public DessertLog(long userId, String content, int revenueType, int sourceType, BigDecimal income, int bizType, BigDecimal score) {
		this.userId = userId;
		this.content = content;
		this.revenueType = revenueType;
		this.sourceType = sourceType;
		this.income = income;
		this.bizType = bizType;
		this.score = score;
	}
	
	

	public BigDecimal getCurrentScoreTotal() {
		return currentScoreTotal;
	}

	public void setCurrentScoreTotal(BigDecimal currentScoreTotal) {
		this.currentScoreTotal = currentScoreTotal;
	}

	public String getDessertConfigName() {
		return dessertConfigName;
	}

	public void setDessertConfigName(String dessertConfigName) {
		this.dessertConfigName = dessertConfigName;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRevenueType() {
		return revenueType;
	}

	public void setRevenueType(int revenueType) {
		this.revenueType = revenueType;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
