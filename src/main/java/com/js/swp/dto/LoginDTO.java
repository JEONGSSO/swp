package com.js.swp.dto;

import lombok.Data;

@Data
public class LoginDTO 	//Data Transfer Object 전달 vo는 받는 놈
{
	private String userid;
	private String userpw;
}
