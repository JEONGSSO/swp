package com.js.swp;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class BoardDAOTest {

	@Inject
	private BoardDAO dao;

	private static Integer maxbno = 0;
	private static boolean didupdate = false;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Before		//Test 전에 실행
	public void getMax() throws Exception {
			if(maxbno == 0) {
				maxbno = dao.getMaxbno();
				logger.info("Before maxbno={}", maxbno);
			}
	}
	@Test
	public void testCreate() throws Exception {
		Board board = new Board();
		board.setTitle("제목");
		board.setContent("생성");
		board.setWriter("user00.");
		dao.create(board);
	}

	@Test
	public void testRead() throws Exception {
		Board first = (dao.read(maxbno));
		logger.info(first != null ? first.toString() : "Board is empty");
	}

	@Test
	public void testUpdate() throws Exception {
		Board board = new Board();
		board.setBno(maxbno);
		board.setTitle("제목 업데이트");
		board.setContent("업데이트");
		dao.update(board);
		didupdate = true;
	}

	@Test
	public void testDelete() throws Exception {
		if(didupdate = true) {
			dao.delete(maxbno);
			didupdate = false;
		}
	}	
		@Test
		public void testListPage()throws Exception{
			int page = 3;
			
			List<Board> list = dao.listPage(page);
			
			for(Board board : list) {
				logger.info(board.getBno() + " : "+ board.getTitle());
			}
		}
		@Test
			public void testListCriteria() throws Exception{
			Criteria criteria = new Criteria();
			criteria.setPage(1);
			criteria.setPerPageNum(10);
			
			List <Board> list = dao.listCriteria(criteria);
			
			for(Board board : list) {
				logger.info(board.getBno() + " : " + board.getTitle());
			}
		}
}