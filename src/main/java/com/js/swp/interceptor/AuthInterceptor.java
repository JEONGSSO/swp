package com.js.swp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter implements  SessionKey // 컨트롤러 연결고리
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exceptiond
	{	//servlet 걸기

		System.out.println("LoginInter preeee>>>");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null)	//obj라 not equal null 해줘야한다.
			session.removeAttribute(LOGIN);	//로그인한 객체가 있으면 지운다.
		
		return true;
	}

}
