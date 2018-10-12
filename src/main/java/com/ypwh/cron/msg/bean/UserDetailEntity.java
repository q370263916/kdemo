package com.ypwh.cron.msg.bean;

import com.ypwh.framework.common.util.Check;
import com.ypwh.framework.common.util.Resources;
import com.ypwh.framework.common.util.StringUtil;

import java.util.Date;

public class UserDetailEntity {
	private long userId;
	
	private String headImg;
	
	private String realName;

    private String nickName;
    
    private String mobile;

    private Date birth;

    private int sex;

    private String deviceToken;

    private int deviceType;

    private String wxOpenId;

    private String jpushId;
    
    private String easemobId;
    
    private String easemobPwd;
    
    private int creditScore;
    /**
     * 签名
     */
    private String signature;
    private int isMerchant;
    
    private String titles;
    
    private int group;
    
    /**
	 * 空间背景图
	 */
	private String zoneBackground;
	
	private String tradeRate;
	
	/**
	 * 目前用于判断卖家的销售等级
	 */
	private int creditLevel;
	
	/**用户身份*/
	private int identity;
	
	/**
	 * 工作时间
	 */
	private String workTime;
	
	/**
	 * 用户总积分
	 */
	private int totalPoint;
	
	/**买家成长值*/
	private int buyerPoint;
	
	//商品质量积分
	private int merchantGoodsQualityPoint;
	//商品质量积分
	private int merchantGoodsConventionPoint;
	//个人诚信积分
	private int merchantGoodsFaithPoint;
	//社交活跃分
	private int merchantGoodsSocialPoint;
	//账户信息积分
	private int merchantGoodsAccountPoint;
	//芝麻信用积分
	private int zhimaScore;
	//签到天数
	private int signDays;
	//最后签到天数
	private Date signLastDate;
	
	private String headImgUrl;

	private String license;

	private int  authFlag;


	public String getheadImgUrl() {
		if(Check.NuNString(headImg)) {
			return Resources.getString("head.default.img");
		}
		return  StringUtil.getPicUrl(this.headImg);
	}
	

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}


	public int getSignDays() {
		return signDays;
	}

	public void setSignDays(int signDays) {
		this.signDays = signDays;
	}

	public Date getSignLastDate() {
		return signLastDate;
	}

	public void setSignLastDate(Date signLastDate) {
		this.signLastDate = signLastDate;
	}

	public int getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(int zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public int getMerchantGoodsQualityPoint() {
		return merchantGoodsQualityPoint;
	}

	public void setMerchantGoodsQualityPoint(int merchantGoodsQualityPoint) {
		this.merchantGoodsQualityPoint = merchantGoodsQualityPoint;
	}

	public int getMerchantGoodsConventionPoint() {
		return merchantGoodsConventionPoint;
	}

	public void setMerchantGoodsConventionPoint(int merchantGoodsConventionPoint) {
		this.merchantGoodsConventionPoint = merchantGoodsConventionPoint;
	}

	public int getMerchantGoodsFaithPoint() {
		return merchantGoodsFaithPoint;
	}

	public void setMerchantGoodsFaithPoint(int merchantGoodsFaithPoint) {
		this.merchantGoodsFaithPoint = merchantGoodsFaithPoint;
	}

	public int getMerchantGoodsSocialPoint() {
		return merchantGoodsSocialPoint;
	}

	public void setMerchantGoodsSocialPoint(int merchantGoodsSocialPoint) {
		this.merchantGoodsSocialPoint = merchantGoodsSocialPoint;
	}

	public int getMerchantGoodsAccountPoint() {
		return merchantGoodsAccountPoint;
	}

	public void setMerchantGoodsAccountPoint(int merchantGoodsAccountPoint) {
		this.merchantGoodsAccountPoint = merchantGoodsAccountPoint;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getJpushId() {
		return jpushId;
	}

	public void setJpushId(String jpushId) {
		this.jpushId = jpushId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getEasemobId() {
		return easemobId;
	}

	public void setEasemobId(String easemobId) {
		this.easemobId = easemobId == null ? null : easemobId.trim();
	}

	public String getEasemobPwd() {
		return easemobPwd;
	}

	public void setEasemobPwd(String easemobPwd) {
		this.easemobPwd = easemobPwd == null ? null : easemobPwd.trim();
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore ;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature== null ? null : signature.trim();
	}

	public int getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(int isMerchant) {
		this.isMerchant = isMerchant;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getZoneBackground() {
		return zoneBackground;
	}

	public void setZoneBackground(String zoneBackground) {
		this.zoneBackground = zoneBackground;
	}

	public String getTradeRate() {
		return tradeRate;
	}

	public void setTradeRate(float tradeRate) {
		this.tradeRate = tradeRate+"";
	}

	public int getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(int creditLevel) {
		this.creditLevel = creditLevel;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public void setTradeRate(String tradeRate) {
		this.tradeRate = tradeRate;
	}

	public int getBuyerPoint() {
		return buyerPoint;
	}

	public void setBuyerPoint(int buyerPoint) {
		this.buyerPoint = buyerPoint;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public int getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(int authFlag) {
		this.authFlag = authFlag;
	}
}