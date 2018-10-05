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

	private static final String NS = "UserMapper";	//ok 1005 이거 경로를 그냥 매퍼이름 적으면 된다
	private static final String LOGIN  = NS + ".login"; //ok
	
	@Override
	public User login(LoginDTO dto) throws Exception
	{	//User 결과값을 담는놈(반환타입) 오브젝트 LoginDTO 클라이언트에서 올라온 id,pw 전달하는 놈
//		System.out.println(">>>>>>>>>>>"+dto); //잘옴
		return session.selectOne(LOGIN, dto);
	}
	
}
