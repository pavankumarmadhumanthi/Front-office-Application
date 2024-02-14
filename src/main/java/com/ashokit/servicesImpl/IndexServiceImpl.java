package com.ashokit.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ashokit.binding.ContactEmail;
import com.ashokit.service.IndexService;
@Service
public class IndexServiceImpl implements IndexService{
	
	@Autowired
	private JavaMailSender mailsender;
	
	@Value("${spring.mail.username}")
	private String from;

	@Override
	public boolean sendEmail(ContactEmail email) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setSubject("Enquiry");
		simpleMailMessage.setText(email.getName()+"\n"+email.getPhone()+"\n"+email.getText());
		simpleMailMessage.setTo("pavankumarmadhumanthi@gmail.com");
		mailsender.send(simpleMailMessage);
		return true;
	}

}
