package com.js.swp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.js.swp.domain.MessageVo;
import com.js.swp.persistence.MessageDAO;
import com.js.swp.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService
{
	@Inject
	MessageDAO msgdao;
	
	@Inject
	PointDAO pointdao;

	private static final int READ_POINT = 5;
	
	@Override
	public void addMessage(MessageVo msgvo) throws Exception
	{
		msgdao.create(msgvo);
	}

	@Override
	public MessageVo readMessage(String uid, Integer mno) throws Exception
	{
		msgdao.updateState(mno);
		pointdao.updatePoint(uid, READ_POINT);
		return msgdao.readMessage(mno);
	}
}
