package com.js.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
import com.js.swp.persistence.BoardDAO;
import com.js.swp.persistence.ReplyDAO;

@Service
public class BoardServiceImpl implements BoardService
{
	
	@Inject
	private BoardDAO dao;
	
	private ReplyDAO replyDao;
	
	@Transactional
	@Override
	public void regist(Board board) throws Exception 	//0928
	{	//		dao.createWithAttach(board);	임시로 써본거
		
		dao.create(board);	
		
//		Integer lastid = dao.getLastId();
//		System.out.println("QQQQQQQQQQQQQQQQQ>>" + lastid);
		//여기서도 bno 널
		String[] files = board.getFiles();
		if(files == null) return;	//파일이 널일때 종료
		
		for(String file : files)	//10-01
			dao.addAttach(file);
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
	
	@Transactional
	@Override
	public void remove(Integer bno) throws Exception
	{
		dao.deleteAllAttaches(bno);
		replyDao.deleteAll(bno);
		dao.delete(bno);	//보드는 첨부,댓글 삭제 후 가장 마지막에
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

	@Override
	public List<String> getAttach(Integer bno)
	{
		return dao.getAttach(bno);
	}

	@Override
	public void removeAttach(String fileName)
	{
		dao.removeAttach(fileName);
	}
	
	@Transactional
	@Override
	public void appendAttach(String[] fullNames, Integer bno)
	{	//1003 파일 n개가 저장되는 공간에 @Transactional걸어 다 올라가야만 성공하게끔
		for (String fullName : fullNames)
			dao.appendAttach(fullName, bno);
	}
	
}
