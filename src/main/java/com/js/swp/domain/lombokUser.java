package com.js.swp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class lombokUser
{
	private int id;
	private String name;

	public static void main(String args[])	//javac가 컴파일 할때 겟, 셋, 투스트링을 붙여준다.
	{
			lombokUser u1 = new lombokUser(123, "hong");
			System.out.println(u1);
			
			lombokUser u2 = lombokUser.builder().id(123).name("hong2").build();	//한 줄로 간단히 겟셋 가능
			System.out.println(u2);
	}
}
