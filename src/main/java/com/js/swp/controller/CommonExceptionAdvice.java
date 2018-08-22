package com.js.swp.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class) 
	private ModelAndView common(Exception ex) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/board/error_common");
		mav.addObject("exception", ex);
		return mav;
	}
}