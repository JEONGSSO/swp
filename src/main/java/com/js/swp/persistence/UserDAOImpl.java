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

	private static final String NS = "com.js.swp.mapper.UserMapper";
	private static final String LOGIN  = NS + ".login";
	
	@Override
	public User login(LoginDTO dto) throws Exception
	{	//User 결과값을 담는놈(반환타입) 오브젝트 LoginDTO 클라이언트에서 올라온 id,pw 전달하는 놈
		return session.selectOne(LOGIN, dto);
	}
	
}
