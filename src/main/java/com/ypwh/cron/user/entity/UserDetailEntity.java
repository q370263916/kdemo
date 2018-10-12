package com.ypwh.cron.user.entity;


public class UserDetailEntity {
	
   private long userId;
	
    private String nickName;
    
    private String mobile;

  
    private String jpushId;


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getJpushId() {
		return jpushId;
	}


	public void setJpushId(String jpushId) {
		this.jpushId = jpushId;
	}
    
    
}
