package com.js.swp.service;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;

public interface UserService
{
	User login(LoginDTO dto) throws Exception;
}
