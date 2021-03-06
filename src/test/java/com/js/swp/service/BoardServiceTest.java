package com.js.swp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.js.swp.domain.Board;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})

public class BoardServiceTest  {//extends BoardServiceImpl

	@Inject
	private BoardService service;
	
	private static Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);
			
	@Ignore
	public void testRead() throws Exception {
		Board board = service.read(2);
		logger.debug(board.toString());
		assertEquals("새로운 글을 넣습니다.", board.getTitle());
	}
	
	@Test
	public void testRegist() throws Exception	//0928
	{	
		Board board = new Board();
		
		board.setTitle("테스트 제목");
		board.setContent("테스트 내용");
		board.setWriter("작성자");
		
		assertNull(board.getFiles());	//하기전에 널인지 체크
		
		service.regist(board);
		
		logger.info("testRegist.board = {}", board.toString());
	}
}
