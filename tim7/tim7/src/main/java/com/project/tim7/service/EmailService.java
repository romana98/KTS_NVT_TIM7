package com.project.tim7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	@Async
	public void sendVerificationMail(String email, int id) throws MailException, InterruptedException {
	
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Please verify your email address");
		mail.setText("http://localhost:4200/activateAccount?id=" + id + "&email=" + email);
		javaMailSender.send(mail);

	}

}
