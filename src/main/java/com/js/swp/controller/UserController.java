
package com.js.swp.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;
import com.js.swp.interceptor.SessionKey;
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
	public void loginPost(LoginDTO dto, Model model, HttpSession session) throws Exception
	{
		try
		{
			logger.info("loginPost>>>>>>>>>>>>={}", dto);
			User user = service.login(dto);	//user를 받는다.
			
			if(user != null)	//user 안비어있으면 로그인 성공
			{	
				Date expire = new Date(System.currentTimeMillis() + SessionKey.EXPIRE * 1000); //기본단위가 m/s 그래서 1초면 * 1000
				service.keepLogin(user.getUserid(), session.getId(), expire);
				model.addAttribute("user", user);	//모델에다 user박기전에 db에 먼저쓴다.
				//어트리뷰트에 담기 LoginInterceptor에서 get으로 가질 수 있음.
			}
			else
				model.addAttribute("loginResult", "아이디 또는 비밀번호가 일치하지 않습니다.");
		} 
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) //GET으로하면 url에 아디 비번 보임
	public String loginOut(HttpSession session,		//HttpSession 스프링이 나한테 세션 준다.
						HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		logger.info("logout GET...");
		session.removeAttribute(SessionKey.LOGIN);	//되어있는 로그인을 날려버린다.
		session.invalidate();	//세션의 로그인시간, 세션 맵 정보들 날려버림
		
		//getCookie 이름이 같은 것만 리턴해준다. 키밸류쌍 
		Cookie loginCookie = WebUtils.getCookie(request, SessionKey.LOGIN);	
		
		if(loginCookie != null)	 //로그인 쿠키가 있으면
		{
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);	//지금까지
			
			response.addCookie(loginCookie);		//스프링쪽에 쿠키를 담아놓음
			
			User user = (User)session.getAttribute(SessionKey.LOGIN);
			service.keepLogin(user.getUserid(), session.getId(), new Date());
		}
		return "/login";
	}
}
