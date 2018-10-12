package com.ypwh.cron.goods.model;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsEntity {
	public long id;
	public String goodsName;
	public BigDecimal salePrice;
	public String brandName;
	public int brandId;
	public long ownerId;
	public int cateId;
	public int subCateId;
	public String cateName;
	public String subCateName;
	public int goodsState;
	public int stateName;
	public long gloveId;
	public BigDecimal purchasePrice;
	public int usageStateId;
	public String goodsBrief;
	public String goodsThumb;
	public int pbizType;
	public int bizType;
	/**
	 * 购买或赠送的养护服务商品id
	 */
	private int bonusId;

	private Date brushTime;
	private Date publishTime;
	private Date createTime;
	private Date lastTime;
	private int brandSeriesId;
	private int browseNum;
	private int favorNum;

	private String remarks;

	private String labelIds;

	/**
	 * 0 不限 1 男 2 女
	 */
	private int sex;
	/**
	 * 是否可以议价 0 可以 1 不可以
	 */
	private int canBargain;

	/**
	 * 价格趋势  0 价格不变 1 降价
	 */
	private int priceCurrent;
	/**
	 * 期望到手价
	 */
	private BigDecimal expectPrice;
	private int orderAppState;

	private double manualScore;
	private double fixedScore;
	private double dynamicScore;
	private int commonwealRate;

	private String deviceToken;

	private String ip;

	private int source;

	private String activityIds;
	private BigDecimal activityPrice;

	private String lastRemarks;
	private String barCode;

	private int postageType;

	private BigDecimal expressFee;

	private int expressType;
	private int stockRemain;

	private BigDecimal tradeRate;

	private long sourceId;

	private int lastApproveCode;

	private int cashOnDelivery;

	private String modelSerial;

	private int stockNum;

	private BigDecimal supplyPrice;

	private int isHidden;

	/**佣金券Id*/
	private long commisionId;

	/**自定义品牌*/
	private String selfDefBrand;

	/**产地*/
	private String sourceArea;

	private int goodsProgress;

	private int isNew;//是否是新品

	private BigDecimal leaseDeposit;

	//首单价格
	private BigDecimal firstPrice;
	//七日租金
	private BigDecimal saleSevenPrice;
	//十五日租金
	private BigDecimal saleHalfMonthPrice;
	//三十日租金
	private BigDecimal saleMonthPrice;

	private int newerPriceEnabled;

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public BigDecimal getLeaseDeposit() {
		return leaseDeposit;
	}

	public void setLeaseDeposit(BigDecimal leaseDeposit) {
		this.leaseDeposit = leaseDeposit;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public int getGoodsProgress() {
		return goodsProgress;
	}

	public void setGoodsProgress(int goodsProgress) {
		this.goodsProgress = goodsProgress;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public int getCateId() {
		return cateId;
	}
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}
	public int getSubCateId() {
		return subCateId;
	}
	public void setSubCateId(int subCateId) {
		this.subCateId = subCateId;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getSubCateName() {
		return subCateName;
	}
	public void setSubCateName(String subCateName) {
		this.subCateName = subCateName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(int goodsState) {
		this.goodsState = goodsState;
	}
	public int getStateName() {
		return stateName;
	}
	public void setStateName(int stateName) {
		this.stateName = stateName;
	}
	public long getGloveId() {
		return gloveId;
	}
	public void setGloveId(long gloveId) {
		this.gloveId = gloveId;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public int getUsageStateId() {
		return usageStateId;
	}
	public void setUsageStateId(int usageStateId) {
		this.usageStateId = usageStateId;
	}
	public String getGoodsBrief() {
		return goodsBrief;
	}
	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}
	public String getGoodsThumb() {
		return goodsThumb ;
	}
	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}
	public int getPbizType() {
		return pbizType;
	}
	public void setPbizType(int pbizType) {
		this.pbizType = pbizType;
	}
	public int getBizType() {
		return bizType;
	}
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
	public Date getBrushTime() {
		return brushTime;
	}
	public void setBrushTime(Date brushTime) {
		this.brushTime = brushTime ;
	}
	public int getBrandSeriesId() {
		return brandSeriesId;
	}
	public void setBrandSeriesId(int brandSeriesId) {
		this.brandSeriesId = brandSeriesId;
	}
	public int getBonusId() {
		return bonusId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setBonusId(int bonusId) {
		this.bonusId = bonusId;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public int getBrowseNum() {
		return browseNum;
	}
	public int getFavorNum() {
		return favorNum;
	}
	public void setBrowseNum(int browseNum) {
		this.browseNum = browseNum;
	}
	public void setFavorNum(int favorNum) {
		this.favorNum = favorNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}
	public String getLabelIds() {
		return labelIds;
	}
	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds == null ? null : labelIds.trim();
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getCanBargain() {
		return canBargain;
	}
	public void setCanBargain(int canBargain) {
		this.canBargain = canBargain;
	}
	public int getPriceCurrent() {
		return priceCurrent;
	}
	public void setPriceCurrent(int priceCurrent) {
		this.priceCurrent = priceCurrent;
	}
	public BigDecimal getExpectPrice() {
		return expectPrice;
	}
	public void setExpectPrice(BigDecimal expectPrice) {
		this.expectPrice = expectPrice;
	}
	public int getOrderAppState() {
		return orderAppState;
	}
	public void setOrderAppState(int orderAppState) {
		this.orderAppState = orderAppState;
	}
	public double getManualScore() {
		return manualScore;
	}
	public double getFixedScore() {
		return fixedScore;
	}
	public double getDynamicScore() {
		return dynamicScore;
	}
	public void setManualScore(double manualScore) {
		this.manualScore = manualScore;
	}
	public void setFixedScore(double fixedScore) {
		this.fixedScore = fixedScore;
	}
	public void setDynamicScore(double dynamicScore) {
		this.dynamicScore = dynamicScore;
	}
	public int getCommonwealRate() {
		return commonwealRate;
	}
	public void setCommonwealRate(int commonwealRate) {
		this.commonwealRate = commonwealRate;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getActivityIds() {
		return activityIds;
	}
	public BigDecimal getActivityPrice() {
		return activityPrice;
	}
	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}
	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
	public String getLastRemarks() {
		return lastRemarks;
	}
	public void setLastRemarks(String lastRemarks) {
		this.lastRemarks = lastRemarks;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public int getPostageType() {
		return postageType;
	}
	public void setPostageType(int postageType) {
		this.postageType = postageType;
	}
	public BigDecimal getExpressFee() {
		return expressFee;
	}
	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}
	public int getExpressType() {
		return expressType;
	}
	public void setExpressType(int expressType) {
		this.expressType = expressType;
	}
	public int getStockRemain() {
		return stockRemain;
	}
	public void setStockRemain(int stockRemain) {
		this.stockRemain = stockRemain;
	}
	public BigDecimal getTradeRate() {
		return tradeRate;
	}
	public void setTradeRate(BigDecimal tradeRate) {
		this.tradeRate = tradeRate;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public int getLastApproveCode() {
		return lastApproveCode;
	}
	public void setLastApproveCode(int lastApproveCode) {
		this.lastApproveCode = lastApproveCode;
	}
	public int getCashOnDelivery() {
		return cashOnDelivery;
	}
	public void setCashOnDelivery(int cashOnDelivery) {
		this.cashOnDelivery = cashOnDelivery;
	}
	public String getModelSerial() {
		return modelSerial;
	}
	public void setModelSerial(String modelSerial) {
		this.modelSerial = modelSerial;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public int getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(int isHidden) {
		this.isHidden = isHidden;
	}
	public long getCommisionId() {
		return commisionId;
	}
	public void setCommisionId(long commisionId) {
		this.commisionId = commisionId;
	}

	public String getSourceArea() {
		return sourceArea;
	}

	public void setSourceArea(String sourceArea) {
		this.sourceArea = sourceArea;
	}

	public String getSelfDefBrand() {
		return selfDefBrand;
	}
	public void setSelfDefBrand(String selfDefBrand) {
		this.selfDefBrand = selfDefBrand;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getNewerPriceEnabled() {
		return newerPriceEnabled;
	}

	public void setNewerPriceEnabled(int newerPriceEnabled) {
		this.newerPriceEnabled = newerPriceEnabled;
	}

	public BigDecimal getSaleSevenPrice() {
		return saleSevenPrice;
	}

	public void setSaleSevenPrice(BigDecimal saleSevenPrice) {
		this.saleSevenPrice = saleSevenPrice;
	}

	public BigDecimal getSaleHalfMonthPrice() {
		return saleHalfMonthPrice;
	}

	public void setSaleHalfMonthPrice(BigDecimal saleHalfMonthPrice) {
		this.saleHalfMonthPrice = saleHalfMonthPrice;
	}

	public BigDecimal getSaleMonthPrice() {
		return saleMonthPrice;
	}

	public void setSaleMonthPrice(BigDecimal saleMonthPrice) {
		this.saleMonthPrice = saleMonthPrice;
	}
}
