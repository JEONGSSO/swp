package com.js.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.persistence.BoardDAO;

@Service
public class BoardServiceImpl  implements BoardService{

	// dao를 받기 위해 인젝트 사용
	@Inject 
	private BoardDAO dao;
	
	@Override
	public void regist(Board board) throws Exception {
		dao.create(board);	
	}

	@Override
	public Board read(Integer bno) throws Exception {
		return dao.read(bno);
	}

	@Override
	public void modify(Board board) throws Exception {
		dao.update(board);
	}

	@Override
	public void remove(Integer bno) throws Exception {
		dao.delete(bno);
	}

	@Override
	public List<Board> listAll() throws Exception {
		return dao.listAll();
	}
	
	@Override
		public List<Board> listCriteria(Criteria criteria) throws Exception{
		return dao.listCriteria(criteria);
	}
	@Override
	public int listCountCriteria(Criteria criteria) throws Exception{
		return dao.countPaging(criteria);
	}
}
