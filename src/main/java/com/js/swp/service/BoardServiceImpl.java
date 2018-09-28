package com.js.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService
{
	
	// dao를 받기 위해 인젝트 사용
	@Inject
	private BoardDAO dao;
	
	@Transactional
	@Override
	public void regist(Board board) throws Exception 	//0928
	{
		dao.create(board);
		
		String[] files = board.getFiles();
		
		if(files == null) return ;	//파일이 널일때 종료
		
			for(String file : files)
			{
//				dao.addAttach(file);
			}
	}
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public Board read(Integer bno) throws Exception
	{
		dao.plusViewcnt(bno);
		return dao.read(bno);
	}
	
	@Override
	public void modify(Board board) throws Exception
	{
		dao.update(board);
	}
	
	@Override
	public void remove(Integer bno) throws Exception
	{
		dao.delete(bno);
	}
	
	@Override
	public List<Board> listAll() throws Exception
	{
		return dao.listAll();
	}
	
	@Override
	public List<Board> listCriteria(Criteria criteria) throws Exception
	{
		return dao.listCriteria(criteria);
	}
	
	@Override
	public int listCountCriteria(Criteria criteria) throws Exception
	{
		return dao.countPaging(criteria);
	}
}
