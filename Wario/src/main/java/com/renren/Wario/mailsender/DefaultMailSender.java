/**
 *    Copyright 2014 Renren.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.renren.Wario.mailsender;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefaultMailSender implements IMailSender {

	Logger logger = LogManager.getLogger(DefaultMailSender.class.getName());

	private MimeMessage mimeMessage;
	private Session session;
	private Properties props;
	private Multipart multipart;

	private final String smtp = "";
	private final String username = "";
	private final String password = "";

	@Override
	public void sendMail(String address, String message) {
		System.err.println(address + ":" + message);
		String from = username;
		String to = address;
		String copyto = "";
		String subject = "报警邮件";
		String content = message;

		try {
			sendAndCc(from, to, copyto, subject, content);
		} catch (AddressException e) {
			logger.error("Address:" + address + " Message:" + message
					+ " send failed!\n" + e.toString());
		} catch (MessagingException e) {
			logger.error("Address:" + address + " Message:" + message
					+ " send failed!\n" + e.toString());
		}
	}

	public DefaultMailSender() {
		props = System.getProperties();
		session = Session.getDefaultInstance(props, null);
		mimeMessage = new MimeMessage(session);
		multipart = new MimeMultipart();
	}

	private void sendOut() throws MessagingException {
		mimeMessage.setContent(multipart);
		mimeMessage.saveChanges();
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		transport.connect((String) props.get("mail.smtp.host"), username,
				password);
		transport.sendMessage(mimeMessage,
				mimeMessage.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	private void sendAndCc(String from, String to, String copyto,
			String subject, String content) throws AddressException,
			MessagingException {
		props.put("mail.smtp.host", smtp);
		props.put("mail.smtp.auth", "true");
		mimeMessage.setSubject(subject);
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent("" + content, "text/html;charset=GBK");
		multipart.addBodyPart(bodyPart);
		mimeMessage.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
		mimeMessage.setRecipients(Message.RecipientType.CC,
				(Address[]) InternetAddress.parse(copyto));
		mimeMessage.setFrom(new InternetAddress(from));
		sendOut();
	}
}
