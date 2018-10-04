
package com.js.swp.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;
import com.js.swp.service.UserService;

@Controller
public class UserController
{
	@Inject
	private UserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) //GET으로하면 url에 아디 비번 보임
	public void loginGET() throws Exception
	{
		logger.info("login GET...");
	}
	
	@RequestMapping(value = "/loginPost", method = RequestMethod.POST) //GET으로하면 url에 아디 비번 보임
	public void loginPost(LoginDTO dto, Model model) throws Exception
	{
		try
		{
			User user = service.login(dto);
			if(user != null) //로그인 실패
				model.addAttribute("user", user);	//어트리뷰트에 담기 get으로 가져올수있음
			else
				model.addAttribute("loginResult", "Login Fail!!");
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		logger.info("login GET...");
		
	}
}
