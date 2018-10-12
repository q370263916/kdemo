package com.ypwh.cron.msg.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TaskActivityInvite {
//	id	bigint
	private Long id;
//	activity_code	varchar
	private String activityCode;
//	user_id	bigint
	private Long userId;
//	account	varchar
	private String account;
//	invite_account	varchar
	private String inviteAccount;
//	invite_state	tinyint
	private Integer inviteState;
//	invite_time	datetime
	private Date inviteTime;
//	last_time	datetime
	private Date lastTime;
//	title	varchar
	private String title;
//	content	varchar
	private String content;
//	flag	int
	private Integer flag;
//	user_name	varchar
	private String UserName;
//	user_img	varchar
	private String userImg;
//	member_id	varchar
	private String memberId;
//	member_count	int
	private Integer memberCount;
//	hot	int
	private Integer hot;
//	rich	decimal
	private BigDecimal rich;
//	sale	decimal
	private BigDecimal sale;

	private long invitedUserId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getInviteAccount() {
		return inviteAccount;
	}
	public void setInviteAccount(String inviteAccount) {
		this.inviteAccount = inviteAccount;
	}
	public Integer getInviteState() {
		return inviteState;
	}
	public void setInviteState(Integer inviteState) {
		this.inviteState = inviteState;
	}
	public Date getInviteTime() {
		return inviteTime;
	}
	public void setInviteTime(Date inviteTime) {
		this.inviteTime = inviteTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public BigDecimal getRich() {
		return rich;
	}
	public void setRich(BigDecimal rich) {
		this.rich = rich;
	}
	public BigDecimal getSale() {
		return sale;
	}
	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public long getInvitedUserId() {
		return invitedUserId;
	}

	public void setInvitedUserId(long invitedUserId) {
		this.invitedUserId = invitedUserId;
	}
}
