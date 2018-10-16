package com.js.swp.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.js.mapper.SampleMapper;
import com.js.swp.domain.User;

public class LoginInterceptor extends HandlerInterceptorAdapter implements  SessionKey // 컨트롤러 연결고리
{
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Inject
	private SampleMapper sampleMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception
	{

		logger.info("loginInter pre>>>>>>>>>>>");
		
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
		User user = (User)modelAndView.getModelMap().get("user");
//		logger.info("로그인>>>>>>>>>>>={}", user); 잘 넘어온다
		if(user != null)	//로그인 성공하면
		{
			session.setAttribute(LOGIN, user);	//name , obj 세션에 login 담는다.
			logger.info("로그인>>>>>>>>>>>={}========{}", user.getUid(), user.getLoginip());	//ip 조회 request.getRemoteAddr()
			sampleMapper.updateLogin(user.getUid(), user.getLoginip());	//로그인 정보를 지금 로그인한 유저의 id를 가져와 조회
			
//			session.setAttribute(LOGIN, user);	//세션에 LOGIN이라는 이름으로 user객체를 박(?)는다
			if(StringUtils.isNotEmpty(request.getParameter("useCookie"))) //로그인 기억 체크하면 
			{
				Cookie loginCookie = new Cookie(LOGIN_COOKIE, session.getId());	//쿠키에 담는 것
				loginCookie.setPath("/"); // localhost:8080/
				loginCookie.setMaxAge(EXPIRE);	// 일주일 지속
				
				response.addCookie(loginCookie);		//스프링쪽에 쿠키를 담아 응답이 오면 같이 나감
			}
		}	
		String attenoted = (String)session.getAttribute(ATTEMPTED);	//ATTEMPTED 가져오기
		
		if(StringUtils.isNotEmpty(attenoted))	//attenoted값이 있으면
		{
			response.sendRedirect(attenoted);	//원하는 곳으로 보내준다.
			session.removeAttribute(ATTEMPTED);	//글쓰기로만 갈거 아니니까 비워준다.
		}
		else
			response.sendRedirect("board/listPage");	//로그인 성공하면 리스트 페이지로
			
	}	//헤더 jsp에 userName담음
	
	
//	@Override
//	public void afterCompletion(
//			HttpServletRequest request, HttpServletResponse response, Object handler,
//			Exception ex)
//			throws Exception
//	{
//		System.out.println("afterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//	}
	
}
