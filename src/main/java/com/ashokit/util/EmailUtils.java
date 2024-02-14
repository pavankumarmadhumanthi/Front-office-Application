package com.ashokit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	//
	private JavaMailSender mailSender;

	public String sendEmail(String to, String subject, String body) {

		try {

			MimeMessage mimeMsg = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
			helper.setTo(to);
			helper.setSubject(subject);
			// "Link:",
			helper.setText(body, true);

			mailSender.send(mimeMsg);
			return " Account is created,password sent to mail successfully";

		} catch (Exception e) {
			e.printStackTrace();
			return "your email is invalid";
		}

	}

}
