
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
			logger.info("loginPost>>>>>>>>>>>>={}", dto);
			User user = service.login(dto);	//user를 받는다.
			if(user != null) //user 안비어있으면 로그인 성공
				//어트리뷰트에 담기 LoginInterceptor에서 get으로 가질 수 있음.
				model.addAttribute("user", user);	
			else
				model.addAttribute("loginResult", "아이디 또는 비밀번호가 일치하지 않습니다.");
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
