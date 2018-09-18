package com.js.swp.persistence;

import java.util.List;

import com.js.swp.domain.Criteria;
import com.js.swp.domain.ReplyVO;

public interface ReplyDAO {
	
	void create(ReplyVO reply) throws Exception;
	
	void update(ReplyVO reply) throws Exception;
	
	void delete(Integer rno) throws Exception;
	
	List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;

	int getToalCount(Integer bno);

	ReplyVO readRno(Integer rno);	//0914
	
	int getBno (Integer rno) throws Exception;

}
