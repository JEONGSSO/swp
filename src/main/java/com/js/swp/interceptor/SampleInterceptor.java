package com.js.swp.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.js.swp.domain.Board;

public class SampleInterceptor extends HandlerInterceptorAdapter // 컨트롤러 연결고리
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("preeeeeeeeeeeeeeee" + request.getRequestURL());
		System.out.println("preeeeeeeeeeeeeeee" + handler);
		
		return true;
	}

	@SuppressWarnings("unchecked")	//warnings(노란줄) 처리 안함
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		System.out.println("afterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		@SuppressWarnings("rawtypes")
		List<Board> list = (List)modelAndView.getModel().get("list");
		System.out.println("list.size" + list.size());
	}
	
	
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex)
			throws Exception
	{
		System.out.println("afterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
	}
	
}
