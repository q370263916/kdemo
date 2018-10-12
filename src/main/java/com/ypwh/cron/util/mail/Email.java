package com.ypwh.cron.util.mail;

import com.ypwh.framework.common.util.Resources;

/**
* @author 作者 : zhuguangzhu
* @version 创建时间：2018年9月3日 下午12:03:14 
* 类说明
 */
public class Email {

	private String smtpUser = Resources.getString("smtp.userName");
	private String smtpPswd = Resources.getString("smtp.smtpPswd");
	private String from = Resources.getString("smtp.emailFrom");
	private String to;
	private String subject;
	private String content;
	/**
	 * 接口调用成功后多少ms内发送则认为有效，否则队列丢弃0表示永久有效，不丢弃。只有在priority为不为0时有效
	 */
	public long delay = 0;

	/**
	 * 0-5，5个等级。0为实时调用不进入等待队列。
	 */
	public int priority = 0;

	/**
	 * 腾讯邮箱可在服务器端配置用户名及密码
	 * 
	 * @return
	 */
	// public Email() {
	// this(SMTP_TENCENT, null, null);
	// }

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPswd() {
		return smtpPswd;
	}

	public void setSmtpPswd(String smtpPswd) {
		this.smtpPswd = smtpPswd;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
