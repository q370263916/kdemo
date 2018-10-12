package com.ypwh.cron.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	private String accountID;
	private String password;

	public MyAuthenticator(String accountID, String password) {
	   super();
	   this.accountID = accountID;
	   this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
	   return new PasswordAuthentication(accountID, password);
	}

}
