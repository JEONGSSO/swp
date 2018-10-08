package com.js.swp.persistence;

import java.util.Date;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;

public interface UserDAO
{
	User login(LoginDTO dto) throws Exception;

	void keepLogin(String uid, String sessionid, Date expire);

	User checkLoginBefore(String loginCookie);
}
