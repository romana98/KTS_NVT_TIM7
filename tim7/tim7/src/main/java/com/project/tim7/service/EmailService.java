package com.project.tim7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Date;
import java.util.List;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	@Async
	public void sendVerificationMail(String email, int id) throws MailException {
	
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Please verify your email address");
		mail.setText("http://localhost:4200/activateAccount?id=" + id + "&email=" + email);
		javaMailSender.send(mail);

	}

	//cultural offer name
	//newsletter name
	//newsletter description
	@Async
	public void sendNewsletterToSubscribed(List<String> emails, String culturalOfferName, String newsletterName, String newsletterDescription) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		for (String email: emails) {
			//value= value.replaceFirst("^data:image/[^;]*;base64,?", ""); byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(value); helper.addAttachment("MyImageName.jpg", new ByteArrayResource(bytes));
			//"javaj9351@gmail.com"
			helper.setTo(email);
			helper.setFrom(env.getProperty("spring.mail.username"));
			helper.setSubject(culturalOfferName + " - Newsletter");
			helper.setSentDate(new Date());
			helper.setText("<h1>" + newsletterName + "</h1>" + "\n\n" + "<p>" + newsletterDescription + "</p>", true);
			javaMailSender.send(mimeMessage);
		}


	}

}
