package com.js.swp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.js.swp.domain.Criteria;
import com.js.swp.domain.ReplyVO;
import com.js.swp.persistence.BoardDAO;
import com.js.swp.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	ReplyDAO replyDAO;
	
	@Inject
	BoardDAO boardDAO;
	
	@Override
	public void register(ReplyVO reply) throws Exception {			//addreply
		replyDAO.create(reply);										//서비스에서 준 값을 dao에 넘긴다.
		
	}

	@Override
	public void modify(ReplyVO reply) throws Exception {
		replyDAO.update(reply);
		
	}

	@Override
	public void remove(Integer rno) throws Exception {
		replyDAO.delete(rno);
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		return replyDAO.listPage(bno, cri);
	}

	@Override
	public int getTotalCount(Integer bno) throws Exception {
		return replyDAO.getToalCount(bno);		// DAO에 getTotalCount 실행해 리턴해온 값을 서비스에 리턴해준다.
	}

	@Override
	public ReplyVO readRno(Integer rno) throws Exception {	//인트로 받는거 아닌지????????????? 매개변수확인하래
		return replyDAO.readRno(rno);	//0914
	}
	
	@Transactional
	@Override
	public void addReply(ReplyVO replyvo) throws Exception
	{
		replyDAO.create(replyvo);
		boardDAO.updateReplycnt(replyvo.getBno(), 1);
	}
	
	@Override
	public void modifyReply(ReplyVO replyvo) throws Exception
	{
		replyDAO.update(replyvo);
	}
	
	@Transactional
	@Override
	public void removeReply(Integer rno) throws Exception
	{
		int bno = replyDAO.getBno(rno);
		replyDAO.delete(rno);
		boardDAO.updateReplycnt(bno, -1);
	}
	
}
