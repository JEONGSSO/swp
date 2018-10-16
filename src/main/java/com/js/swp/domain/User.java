package com.js.swp.domain;

import java.util.Date;

//import lombok.Builder;
import lombok.Data;

@Data	//javac가 컴파일 할때 겟, 셋, 투스트링을 붙여준다.
//@Builder
public class User
{
	private String uid;
	private String upw;
	private String uname;
	private Integer point;
	
	private String email;
	private String googleid;
	private String naverid;
	private String nickname;
	
	private String loginip;
	private Date lastLogin;
}
