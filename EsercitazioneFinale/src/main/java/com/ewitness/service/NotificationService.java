package com.ewitness.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.properties")
public class NotificationService {

	@Value("${emailFrom}")
	private String emailFrom;
	@Value("${emailSubject}")
	private String subjet;
	@Value("${emailBody}")
	private String body;

	private JavaMailSender javaMailSender;

	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(String emailTo, String path) throws MailException, MessagingException {
		MimeMessage mime = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mime, true);
			helper.setTo(emailTo);
			helper.setFrom(emailFrom);
			helper.setSubject(subjet);
			helper.setText(body);
			FileSystemResource file = new FileSystemResource(path);
			helper.addAttachment(file.getFilename(), file);
			javaMailSender.send(mime);
		} catch (MessagingException e) {
			throw new MailParseException(e);
		}
	}
}
