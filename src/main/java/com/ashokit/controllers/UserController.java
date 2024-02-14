package com.ashokit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignupForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.repo.UserDetailsRepo;
import com.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserDetailsRepo repo;

	@Autowired
	private UserService service;
	
	
	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";

	}

	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form,Model model) {
		
		String status = service.loginVerify(form);
		
		if(status.contains("success")) {
			//display dashboard
			return "redirect:/dashboard";
		}
		if(status.contains("Your Account Locked")) {
			return "redirect:/loadunlock?email="+form.getEmail();
		}
		else {
		model.addAttribute("errMsg",status);
		return "login"; 
	}
	}

	@GetMapping("/loadsignup")
	public String createAccount(Model model) {

		model.addAttribute("signupDetails", new SignupForm());

		return "signup";

	}

	@PostMapping("/savesignup")
	public String createAccount(@ModelAttribute("signupDetails") SignupForm signupDetails, Model model) {

		String signupResponse = service.userSignup(signupDetails);
		model.addAttribute("msg", signupResponse);
		model.addAttribute("signupDetails", new SignupForm());

		return "signup";

	}

	@GetMapping("/loadunlock")
	// deleting the modelattribute and add requestparam here
	public String unlockpage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlockpage", unlockFormObj);

		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlockpage") UnlockForm form, Model model) {

		if (form.getNewPwd().equals(form.getConfirmPwd())) {
			boolean status = service.unlockAccount(form);

			if (status) {
				model.addAttribute("succMsg", "Your account unlocked");
			} else {
				model.addAttribute("errMsg", "Given Temporary Pwd is incorrect");
			}

		} else {
			model.addAttribute("errMsg", "New Pwd adn Confirm Pwd should be same");
		}

		return "unlock";

	}

	@GetMapping("/forgot")
	public String forgotPassword() {

		return "forgotPwd";

	}

	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email, Model model){
		
		System.out.println(email);
		
		boolean status = service.forgotPassword(email);
		
		if(status) {
			//send success msg
			model.addAttribute("succMsg","Pwd sent to your email");
			
		}else {
			//send error msg
			model.addAttribute("errMsg","Invalid Email");
			
			
		}
				

		return "forgotPwd";

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
