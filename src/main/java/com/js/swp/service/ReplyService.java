package com.js.swp.service;

import java.util.List;

import com.js.swp.domain.Criteria;
import com.js.swp.domain.ReplyVO;


public interface ReplyService {
	void register(ReplyVO reply) throws Exception;
	
	void modify(ReplyVO reply) throws Exception;
	
	void remove(Integer rno) throws Exception;
	
	List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception;
	
	int getTotalCount(Integer bno) throws Exception;
}
