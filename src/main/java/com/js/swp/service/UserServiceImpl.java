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
	{	//User 결과값을 담는놈(반환타입) 오브젝트 LoginDTO 클라이언트에서 올라온 id,pw 전달하는 놈
		return dao.login(dto);
	}
}
