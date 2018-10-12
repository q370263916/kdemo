package com.ypwh.cron.msg.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserBonusEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6380735031852394280L;

	private Long id=0L;

    private Long userId;
    
    private String userName;
    
    private String headImg;

    private Long bonusTypeId;

    private Integer state; //1可用 0 已使用 2已过期
    
    private Integer bonusTypeState;//状态 0 不可领取 1 可领取

    private Date getTime;

    private Date useTime;

    private Long orderId;

    private Integer getPlatform;

    private String getChannel;

    private String bonusNo;


    private String bonusCode;
    private BigDecimal bonusMoney;
    private Date startTime;
    private Date endTime;
    private String bonusName;
    private String bonusTypeName;
    private String cateId;
    private String subCateId;
    private int pbizType;
    private String supportedGoods;
    private Integer couponType;
    
    private String bonusTypeDesc;
    
    private int validDays;
    private String activityIds;
    
    private String brandId;
    private String jumpUrl;
    
    private String supportedMerchants;
    
    /**
     * 优惠券来源类型 1 平台 2 商家
     */
    private int couponMode;
    
    private String bizTypes;
    
    /**商家Id*/
    private long merchantId;
    
    private String bonusDescribe;  //详细信息
    
    public String getBonusDescribe() {
		return bonusDescribe;
	}

	public void setBonusDescribe(String bonusDescribe) {
		this.bonusDescribe = bonusDescribe;
	}

    public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBonusTypeDesc() {
		return bonusTypeDesc;
	}

	public void setBonusTypeDesc(String bonusTypeDesc) {
		this.bonusTypeDesc = bonusTypeDesc;
	}

	private BigDecimal  minGoodsAmount;
    
    public String getBonusTypeName() {
		return bonusTypeName;
	}

	public void setBonusTypeName(String bonusTypeName) {
		this.bonusTypeName = bonusTypeName;
	}

	public String getBonusCode() {
		return bonusCode;
	}

	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}

	public BigDecimal getBonusMoney() {
		return bonusMoney;
	}

	public void setBonusMoney(BigDecimal bonusMoney) {
		this.bonusMoney = bonusMoney;
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

	public String getBonusName() {
		return bonusName;
	}

	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}

    

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getBonusTypeState() {
		return bonusTypeState;
	}

	public String getCateId() {
		return cateId;
	}

	public String getSubCateId() {
		return subCateId;
	}

	public void setBonusTypeState(Integer bonusTypeState) {
		this.bonusTypeState = bonusTypeState;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public void setSubCateId(String subCateId) {
		this.subCateId = subCateId;
	}

	public String getSupportedGoods() {
		return supportedGoods;
	}

	public void setSupportedGoods(String supportedGoods) {
		this.supportedGoods = supportedGoods;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBonusTypeId() {
        return bonusTypeId;
    }

    public void setBonusTypeId(Long bonusTypeId) {
        this.bonusTypeId = bonusTypeId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getGetPlatform() {
        return getPlatform;
    }

    public void setGetPlatform(Integer getPlatform) {
        this.getPlatform = getPlatform;
    }

    public String getGetChannel() {
        return getChannel;
    }

    public void setGetChannel(String getChannel) {
        this.getChannel = getChannel == null ? null : getChannel.trim();
    }

    public String getBonusNo() {
        return bonusNo;
    }

    public void setBonusNo(String bonusNo) {
        this.bonusNo = bonusNo == null ? null : bonusNo.trim();
    }

	public BigDecimal getMinGoodsAmount() {
		return minGoodsAmount;
	}

	public void setMinGoodsAmount(BigDecimal minGoodsAmount) {
		this.minGoodsAmount = minGoodsAmount;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	public int getPbizType() {
		return pbizType;
	}

	public void setPbizType(int pbizType) {
		this.pbizType = pbizType;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getSupportedMerchants() {
		return supportedMerchants;
	}

	public void setSupportedMerchants(String supportedMerchants) {
		this.supportedMerchants = supportedMerchants;
	}

	public int getCouponMode() {
		return couponMode;
	}

	public void setCouponMode(int couponMode) {
		this.couponMode = couponMode;
	}

	public String getBizTypes() {
		return bizTypes;
	}

	public void setBizTypes(String bizTypes) {
		this.bizTypes = bizTypes;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
}