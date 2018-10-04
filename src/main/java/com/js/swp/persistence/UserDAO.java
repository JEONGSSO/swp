package com.js.swp.persistence;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;

public interface UserDAO
{
	User login(LoginDTO dto) throws Exception;
}
