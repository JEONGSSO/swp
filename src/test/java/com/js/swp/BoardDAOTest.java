package com.js.swp;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.js.swp.domain.Board;
import com.js.swp.persistence.BoardDAO;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
	
	public class BoardDAOTest {

	@Inject
	private BoardDAO dao;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Test
	public void testCreate() throws Exception {
		Board board = new Board();
		board.setTitle("새로운 글을 넣습니다.");
		board.setContent("새로운 글을 넣습니다.");
		board.setWriter("user00.");
		dao.create(board);
	}
	
	@Test
	public void testRead() throws Exception {
		Board first = (dao.read(1));
		logger.info(first!=null ? first.toString() : "Board is empty");
	}
	
	@Test
	public void testUpdate() throws Exception {
		Board board = new Board();
		board.setBno(1);
		board.setTitle("수정.");
		board.setContent("수정 테슨트.");
		dao.update(board);
	}
	
	@Test
	public void testDelete() throws Exception{
		
		dao.delete(1);
	}
	
}
