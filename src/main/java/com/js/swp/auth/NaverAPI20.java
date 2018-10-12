package com.js.swp.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverAPI20 extends DefaultApi20 implements SnsUrls
{	
	private NaverAPI20() {	//싱글톤 밖에서 접근 못하게 private
	}
	
	private static class InstanceHolder	
	{
		private static final NaverAPI20 INSTANCE = new NaverAPI20(); //final이라 한 번만 생성됨.
	}
	
	public static NaverAPI20 instance()
	{
		return InstanceHolder.INSTANCE;
	}
	
	@Override
	public String getAccessTokenEndpoint()
	{
		return NAVER_ACCESS_TOKEN;	//토큰 받아올 곳
	}	
	
	@Override
	protected String getAuthorizationBaseUrl()
	{
		return NAVER_AUTH;	//인증
	}
	
}
