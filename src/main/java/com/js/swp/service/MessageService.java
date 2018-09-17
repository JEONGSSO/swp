package com.js.swp.service;

import com.js.swp.domain.MessageVo;

public interface MessageService
{
	public void addMessage(MessageVo msgvo) throws Exception;
	public MessageVo readMessage(String uid, Integer mno) throws Exception;
}
