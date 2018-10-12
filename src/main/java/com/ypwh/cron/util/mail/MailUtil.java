package com.ypwh.cron.util.mail;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.google.common.collect.Lists;
import com.ypwh.framework.common.util.Resources;

public class MailUtil {

	public static boolean send(String content, String subject, String... tos) {
		boolean result = true;
		try {
			List<Email> mailList = createMail(content, subject, tos);
			for (Email mail : mailList) {
				result = result && send(mail);
			}

		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public static boolean send(Email mail) {
		JavaMailSender sender = createXSMailSender(mail);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(mail.getFrom());
			helper.setTo(mail.getTo());

			helper.setSubject(mail.getSubject());
			helper.setText(mail.getContent(), true);
			sender.send(helper.getMimeMessage());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static JavaMailSender createXSMailSender(Email mail) {
		org.springframework.mail.javamail.JavaMailSenderImpl jms = new org.springframework.mail.javamail.JavaMailSenderImpl();
		jms.setDefaultEncoding(Resources.getString("smtp.encoding"));
		jms.setHost(Resources.getString("smtp.host"));
		jms.setPort(Integer.valueOf(Resources.getString("smtp.port")));
		jms.setUsername(mail.getSmtpUser());
		jms.setPassword(mail.getSmtpPswd());
		try {
			Properties p = new Properties();
			p.put("mail.transport.protocol", Resources.getString("mail.transport.protocol"));
			p.put("mail.smtp.host", Resources.getString("smtp.host"));
			p.put("mail.smtp.port", Resources.getString("smtp.port"));
			p.put("mail.smtp.auth", Resources.getString("mail.smtp.auth"));

			MyAuthenticator auth = new MyAuthenticator(mail.getSmtpUser(), mail.getSmtpPswd());
			// 根据邮件会话属性和密码验证器构造一个发送邮件的session
			Session mailSession = Session.getInstance(p, auth);
			// 根据session创建一个邮件消息
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(mail.getFrom(), new String(mail.getFrom())));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
			msg.setSentDate(new Date());
			msg.setSubject(mail.getSubject());
			msg.setText(mail.getContent());
			jms.setJavaMailProperties(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jms;
	}

	private static List<Email> createMail(String content, String subject, String... tos) {
		List<Email> emailList = Lists.newArrayListWithCapacity(tos.length);
		Email mail = null;
		for (String to : tos) {
			mail = new Email();
			mail.setSubject(subject);
			mail.setContent(content);
			mail.setTo(to);
			emailList.add(mail);
		}
		return emailList;
	}
}
