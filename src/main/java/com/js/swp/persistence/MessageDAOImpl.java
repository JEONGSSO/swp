package com.js.swp.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.js.swp.domain.MessageVo;

@Repository
public class MessageDAOImpl implements MessageDAO
{
	@Inject
	SqlSession session;
	
	private static final String NS = "messageMapper";
	private static final String CREATE = NS + ".create";	
	private static final String READ_MESSAGE = NS + ".readMessage";	
	private static final String UPDATE_STATE = NS + ".updateState";
	
	
	@Override
	public void create(MessageVo message) throws Exception
	{
		session.insert(CREATE, message);
	}

	@Override
	public MessageVo readMessage(Integer mno) throws Exception
	{
		return session.selectOne(READ_MESSAGE, mno);
	}

	@Override
	public void updateState(Integer mno) throws Exception
	{
		session.update(UPDATE_STATE, mno);
	}
}
