package com.ypwh.cron.msg.bean;

import java.util.Date;

public class UserEntity {
    private long userId;
    
    private String account;

    private String password;

    private int accountType;

    private Date registerTime;

    private int registSource;
    
    private Date lastLogin;
    
    private int status;
    
    private int isMerchant;

    private int source;
    private int unionId;
    private int onlyRegister;
    
    /**禁止操作结束时间*/
    private Date forbidTime;
    
    /**注册频道*/
    private String registChannel;
    
    private String registProvince;
    private String registCity;
    private String registLat;
    private String  registLng;
    
    
    private int userRole;

	private int authRole;

	private int authFlag;
	
	private int userFlag;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getRegistSource() {
		return registSource;
	}

	public void setRegistSource(int registSource) {
		this.registSource = registSource;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(int isMerchant) {
		this.isMerchant = isMerchant ;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source ;
	}

	public int getUnionId() {
		return unionId;
	}

	public int getOnlyRegister() {
		return onlyRegister;
	}

	public void setUnionId(int unionId) {
		this.unionId = unionId;
	}

	public void setOnlyRegister(int onlyRegister) {
		this.onlyRegister = onlyRegister;
	}

	public Date getForbidTime() {
		return forbidTime;
	}

	public void setForbidTime(Date forbidTime) {
		this.forbidTime = forbidTime;
	}

	public String getRegistChannel() {
		return registChannel;
	}

	public void setRegistChannel(String registChannel) {
		this.registChannel = registChannel;
	}

	public String getRegistProvince() {
		return registProvince;
	}

	public void setRegistProvince(String registProvince) {
		this.registProvince = registProvince;
	}

	public String getRegistCity() {
		return registCity;
	}

	public void setRegistCity(String registCity) {
		this.registCity = registCity;
	}

	public String getRegistLat() {
		return registLat;
	}

	public void setRegistLat(String registLat) {
		this.registLat = registLat;
	}

	public String getRegistLng() {
		return registLng;
	}

	public void setRegistLng(String registLng) {
		this.registLng = registLng;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public int getAuthRole() {
		return authRole;
	}

	public void setAuthRole(int authRole) {
		this.authRole = authRole;
	}

	public int getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(int authFlag) {
		this.authFlag = authFlag;
	}

	public int getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(int userFlag) {
		this.userFlag = userFlag;
	}
	
}