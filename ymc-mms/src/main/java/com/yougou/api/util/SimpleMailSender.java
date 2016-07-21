package com.yougou.api.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SimpleMailSender {

	private static Logger LOGGER = Logger.getLogger(SimpleMailSender.class);

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            正文
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMail(String[] receivers, String subject, String content) throws Exception {
		return sendMail(receivers, subject, content, false);
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            正文
	 * @param isAuth
	 *            是否认证
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMail(String[] receivers, String subject, String content, boolean isAuth) throws Exception {
		return sendMail(receivers, subject, content, null, isAuth);
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            正文
	 * @param attachments
	 *            附件
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMail(String[] receivers, String subject, String content, String[] attachments) throws Exception {
		return sendMail(receivers, subject, content, attachments, false);
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            正文
	 * @param attachments
	 *            附件
	 * @param isAuth
	 *            是否认证
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMail(String[] receivers, String subject, String content, String[] attachments, boolean isAuth) throws Exception {

		boolean hasAttachment = false;

		// 简单验证传入数据
		if (ArrayUtils.isEmpty(receivers)) {
			throw new IllegalArgumentException("收信人不能为空!");
		} else {
			for (String receiver : receivers) {
				if (receiver.indexOf("@") == -1) {
					throw new IllegalArgumentException("收信人地址错误:" + receiver);
				}
			}
		}
		if (StringUtils.isBlank(content)) {
			throw new IllegalArgumentException("邮件内容不能为空!");
		}
		if (hasAttachment = ArrayUtils.isNotEmpty(attachments)) {
			for (String attachment : attachments) {
				File file = new File(attachment);
				if (!file.exists()) {
					throw new IllegalArgumentException("附件" + attachment + "不存在!");
				} else if (!file.isFile()) {
					throw new IllegalArgumentException("附件" + attachment + "不是文件!");
				}
			}
		}

		// 邮件服务器地址
		final String smtpHost = SystemConfigHolder.getConfigValueByKey("com.systemmgmt.email.smtp.host");
		// 邮件服务器用户名
		final String user = SystemConfigHolder.getConfigValueByKey("com.systemmgmt.email.user");
		// 邮件服务器密码
		final String password = SystemConfigHolder.getConfigValueByKey("com.systemmgmt.email.password");
		// 邮件发送者
		final String sender = SystemConfigHolder.getConfigValueByKey("com.systemmgmt.email.sender");

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.auth", Boolean.toString(isAuth));

		// 进行邮件服务器用户认证
		Session session = Session.getDefaultInstance(properties, !isAuth ? null : new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		session.setDebug(false);

		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(sender));
			InternetAddress[] internetAddresses = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				internetAddresses[i] = new InternetAddress(receivers[i]);
			}

			mimeMessage.addRecipients(Message.RecipientType.TO, internetAddresses);
			mimeMessage.setSubject(subject);
			mimeMessage.addHeader("designer", "carol");

			if (hasAttachment) {
				MimeBodyPart contentMimeBodyPart = new MimeBodyPart();
				contentMimeBodyPart.setText(content);

				Multipart multipart = new MimeMultipart();
				for (int i = 0; i < attachments.length; i++) {
					MimeBodyPart attachmentMimeBodyPart = new MimeBodyPart();
					FileDataSource fileDataSource = new FileDataSource(attachments[i]);
					attachmentMimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
					attachmentMimeBodyPart.setFileName(fileDataSource.getName());
					multipart.addBodyPart(attachmentMimeBodyPart);
				}

				mimeMessage.setContent(multipart);
			} else {
				mimeMessage.setText(content);
			}

			mimeMessage.setSentDate(new Date());

			if (isAuth) {
				mimeMessage.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(smtpHost, user, password);
				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
				transport.close();
			} else {
				Transport.send(mimeMessage);
			}
		} catch (Exception ex) {
			LOGGER.error(ex);
			return false;
		}

		return true;
	}
}
