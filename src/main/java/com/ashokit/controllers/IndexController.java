package com.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ashokit.binding.ContactEmail;
import com.ashokit.servicesImpl.IndexServiceImpl;

@Controller
@RequestMapping
public class IndexController {
	
	@Autowired
	private IndexServiceImpl indexservice;
	
	@GetMapping("/")
	public String homePage() {
		
		return "index";
	}
	@GetMapping("/contactus")
	public String loadContactEmail(Model model) {
		model.addAttribute("contactemail", new ContactEmail());
		return "contactus";
	}
	
	@PostMapping("/contactus")
	public String sendContactEmail(ContactEmail email,Model model) {
		
		indexservice.sendEmail(email);
		
		model.addAttribute("contactemail",new ContactEmail());
		
		return "contactus";
	}
}
