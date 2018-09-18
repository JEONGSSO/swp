package com.js.swp.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.js.swp.domain.MessageVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})

public class MessageServiceTest extends MessageServiceImpl {

	@Inject
	private MessageService service;
	
	private static Logger logger = LoggerFactory.getLogger(MessageServiceTest.class);
			
	@Test
	public void testReadMessage() throws Exception
	{
		MessageVo messageVo = service.readMessage("user1", 1);
		logger.info(" 메세지Vo >>> " + messageVo) ;
	}
	
//	@Test
	public void testWriteMessage() throws Exception {
		MessageVo messageVo = new MessageVo();		//Vo
		messageVo.setSender("user1");
		messageVo.setTargetid("user1");
		messageVo.setMessage("user_name1");
		logger.info(" 메세지 >>> " + messageVo) ;
		service.addMessage(messageVo);
//		assertEquals("새로운 글을 넣습니다.", board.getTitle());
	}
}
