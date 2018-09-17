package com.js.swp.persistence;

import com.js.swp.domain.MessageVo;

public interface MessageDAO
{
	void create(MessageVo msgvo) throws Exception;
	MessageVo readMessage(Integer mno) throws Exception;
	void updateState(Integer mno) throws Exception;
}
