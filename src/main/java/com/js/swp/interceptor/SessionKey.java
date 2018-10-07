package com.js.swp.interceptor;

public interface SessionKey // 컨트롤러 연결고리
{
	static final String LOGIN = "loginUser";
	static final String LOGIN_COOKIE = "loginCookie";
	static final String ATTEMPTED = "attemptedLocation";
	
	static final int EXPIRE = 7 * 24 * 60 * 60;	// 7일
	
}
