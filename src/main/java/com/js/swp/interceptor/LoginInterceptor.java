package com.js.swp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter implements  SessionKey // 컨트롤러 연결고리
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception
	{

		System.out.println("LoginInter preeee>>>");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null)	//obj라 not equal null 해줘야한다.
			session.removeAttribute(LOGIN);	//로그인한 객체가 있으면 지운다.
			
		
		return true;
	}

	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();	//세션에다가 쿠키 구울(?)꺼
		
		//맵이니까 get, key값은 user로 받을꺼 컨트롤러에서 넣은 값
		Object user = modelAndView.getModelMap().get("user");
		System.out.println("afterrrrrrrr");
		if(user != null)	//로그인 성공하면
			session.setAttribute(LOGIN, user);	//세션에 박힘
	}
	
	
//	@Override
//	public void afterCompletion(
//			HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception ex)
//			throws Exception
//	{
//		System.out.println("afterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//	}
	
}
