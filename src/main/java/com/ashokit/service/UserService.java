package com.ashokit.service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignupForm;
import com.ashokit.binding.UnlockForm;

public interface UserService {
	

	public String userSignup (SignupForm signup);
	
	public boolean unlockAccount (UnlockForm unblock);
	
	public String  loginVerify (LoginForm login);
	
	public boolean  forgotPassword (String email);
	
	
	
	
	
	
}
