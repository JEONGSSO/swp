package com.js.mapper;

import java.util.Map;

public class SampleProvider {
	private static final String SELECT = "select * from User ";
	
	public static String searchUser(Map<String, Object>params){
		if(params.get("searchCol").equals("uid")) {
			return SELECT + "where uid = #{searchStr}";
			
		} else if (params.get("searchCol").equals("uname")) {
			return SELECT + "where uname like concat('%', #{searchStr}, '%')";
			//concat '%', #{searchStr}, '%'을 합쳐준다
		} else {
			return SELECT;
		}
	}
}
