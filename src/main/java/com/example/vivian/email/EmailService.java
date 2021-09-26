package com.example.vivian.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

	private final static Logger LOGGER= LoggerFactory.getLogger(EmailSender.class);
	private final JavaMailSender mailSender;
	
	@Override
	@Async
	public void send(String para, String email) {
		
		try {
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");
			
			helper.setText(email,true);
			helper.setTo(para);
			helper.setSubject("Confirma tu cuenta de Food Store");
			helper.setFrom("aldmurguia1@gmail.com");
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			LOGGER.error("fallo al enviar el correo");
			throw new IllegalStateException();
		}
		
	}

	
}
