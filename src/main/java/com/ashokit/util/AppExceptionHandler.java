package com.ashokit.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class AppExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String globalExceptionHandling(Exception e) {
		
		String str="Something went Wrong"+e.getMessage();
		
		return str;
	}

}
