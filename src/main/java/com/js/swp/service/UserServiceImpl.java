package com.js.swp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;
import com.js.swp.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService
{
	@Inject
	private UserDAO dao;

	@Override
	public User login(LoginDTO dto) throws Exception
	{
		return dao.login(dto);
	}
}
