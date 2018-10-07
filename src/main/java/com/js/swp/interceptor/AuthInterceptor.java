package com.js.swp.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.js.swp.controller.BoardController;
import com.js.swp.domain.User;
import com.js.swp.service.UserService;

public class AuthInterceptor extends HandlerInterceptorAdapter implements  SessionKey // 컨트롤러 연결고리
{
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Inject
	private UserService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception	//login안되어있으면 실행되는거라 pre만 사용
	{	//servlet 걸기
		logger.info("AuthInter preeee>>>");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) == null)	//obj라 not equal null 해줘야한다.
		{	//request 브라우저 요청온거에서 LOGIN_COOKIE이름으로 가져옴
			Cookie loginCookie = WebUtils.getCookie(request, SessionKey.LOGIN_COOKIE); 
				
			if(loginCookie != null)
			{
				User loginedUser = service.checkLoginBefore(loginCookie.getValue());
					if(loginedUser != null)
					{
						session.setAttribute(LOGIN, loginedUser);
						return true;
					}
			}
			
			String uri = request.getRequestURI();
			String httpMethod = request.getMethod();	//겟이 아닐때만 로그인 필요받기
			if(StringUtils.contains(uri, "/replies/") && !StringUtils.equalsIgnoreCase(httpMethod, "GET")){
				{
					response.sendError(401, "로그인이 필요합니다.");
					return false;
				}
			}
			
			saveAttemptedLocation(request, session);
			
			response.sendRedirect("/login");	//인터셉터 걸리면 여기로 보낸다.
		}
		return true;
	}

	private void saveAttemptedLocation(HttpServletRequest request, HttpSession session) {
		String uri = request.getRequestURI();	//만약 로그인 안했는데 글쓰기 누르면 /board/register
		String query = request.getQueryString();	//iistPage?=2321dadsa
		if(StringUtils.isNotEmpty(query)) //쿼리가 있을때는
			uri += "?" + query; //?를 붙인다.
		
		session.setAttribute(ATTEMPTED, uri);	//세션에다가 저장 나중에 불러쓰기위해
	}

}
