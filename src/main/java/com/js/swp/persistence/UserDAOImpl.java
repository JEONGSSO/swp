package com.js.swp.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.js.swp.domain.User;
import com.js.swp.dto.LoginDTO;

@Repository
public class UserDAOImpl implements UserDAO
{
	@Inject
	private SqlSession session;

	private static final String NS = "com.js.swp.persistence.UserMapper";
	private static final String LOGIN  = NS + ".login";
	
	@Override
	public User login(LoginDTO dto) throws Exception
	{
		return session.selectOne(LOGIN, dto);
	}
	
}
