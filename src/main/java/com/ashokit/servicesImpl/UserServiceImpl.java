package com.ashokit.servicesImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignupForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.entity.UserDetails;
import com.ashokit.repo.UserDetailsRepo;
import com.ashokit.service.UserService;
import com.ashokit.util.EmailUtils;
import com.ashokit.util.PasswordUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepo repo;

	@Autowired
	private PasswordUtils password;

	@Autowired
	private EmailUtils mail;
	
	@Autowired
	private HttpSession session;


	@Override
	public String userSignup(SignupForm signup) {

		UserDetails userEmail = repo.findByemail(signup.getEmail());

		if (userEmail != null) {
			return "Choose unique email";
		}

		UserDetails entity = new UserDetails();
		String otp = password.generatePassword();

		BeanUtils.copyProperties(signup, entity);
		entity.setPassword(otp);
		entity.setAccountStatus("Locked");

		/*
		 * entity.setName(signup.getName()); entity.setEmail(signup.getEmail());
		 * entity.setPhoneNo(signup.getPhoneNo()); entity.setPassword(otp);
		 */
		repo.save(entity);

		String to = entity.getEmail();

		String subject = "Unlock Your Account";

		String body = "<h1> Please click on below link to unlock your account</h1>"
				+ "<a href=\"http://localhost:9090/loadunlock?email=" + to + "\">Unlock your account</a>"

				+ "<p>Your temporary password is:<strong>" + otp + "</string></p>";

		String emailResponse = mail.sendEmail(to, subject, body);

		return emailResponse;

	}

	@Override
	public boolean unlockAccount(UnlockForm form) {

		UserDetails entity = repo.findByemail(form.getEmail());

		if (entity != null && entity.getPassword().equals(form.getTempPwd())) {

			entity.setPassword(form.getNewPwd());
			entity.setAccountStatus("Unlocked");
			// repo.updateColumnByEmail(newPwd, newEmail);

			repo.save(entity);

			return true;
		} else {

			return false;
		}
	}

	@Override
	public String loginVerify(LoginForm form) {

		UserDetails entity = repo.findByEmailAndPassword(form.getEmail(), form.getPassword());

		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity.getAccountStatus().equals("Locked")) {
			return "Your Account Locked";
		}
		
		//create session and store user data in session
		
		session.setAttribute("userId",entity.getUserId());
		
		return "success";
	}

	@Override
	public boolean forgotPassword(String email) {
		// check record presence in db with given email

		UserDetails entity = repo.findByemail(email);

		// if record is not avaliable send error msg

		if (entity == null) {
			return false;
		}

		// if record avaliable send pwd to email and send success msg

		String subject = "Recover Password";
		String body = "Your Pwd::" + entity.getPassword();
		String to = email;
		
		mail.sendEmail(to, subject, body);

		return true;
	}

}
