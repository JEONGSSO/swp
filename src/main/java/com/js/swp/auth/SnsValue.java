package com.js.swp.auth;

import org.apache.commons.lang3.StringUtils;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class SnsValue implements SnsUrls	//1011 로그인
{
	private String service;
	private String clientId;
	private String clientSerctet;
	private String rediectUrl;
	private DefaultApi20 api20Instance;
	private String profileUrl;
	
	public SnsValue(String service, String clientid, String clientserctet, String rediecturl)
	{
		this.service = service;
		this.clientId = clientid;
		this.clientSerctet = clientserctet;
		this.rediectUrl = rediecturl;
		
		if(StringUtils.equalsAnyIgnoreCase(service, "naver"))
		{
			this.api20Instance = NaverAPI20.instnace();
			this.profileUrl = NAVER_PROFILE_URL;
		}
		
		else if (StringUtils.equalsIgnoreCase(service, "google")) {
			this.api20Instance = GoogleApi20.instance();
			this.profileUrl = GOOGLE_PROFILE_URL;
		}
	}
}
