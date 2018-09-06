package com.js.swp.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.js.swp.domain.Criteria;
import com.js.swp.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{
	
	@Inject
	SqlSession session;
	
	private static final String NS = "replyMapper";									
	private static final String CREATE = NS + ".create";								//매퍼에 ID가 create인 쿼리를 담는다 
	private static final String UPDATE = NS + ".update";								//매퍼에 ID가 update인 쿼리를 담는다 
	private static final String DELETE = NS + ".delete";										//매퍼에 ID가 delete인 쿼리를 담는다 
	private static final String LISTPAGE = NS + ".listPage";					
	private static final String GETTOTALCOUNT = NS + ".getTotalCount";	//매퍼에 ID가 getTotalCount인 쿼리를 담는다 
	
	
	@Override
	public void create(ReplyVO reply) throws Exception {
		session.insert(CREATE, reply);
		
	}

	@Override
	public void update(ReplyVO reply) throws Exception {
		session.update(UPDATE, reply);
		
	}

	@Override
	public void delete(Integer rno) throws Exception {
		session.delete(DELETE, rno);
		
	}

	@Override
	public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception {
		
		Map<String, Object>paramMap = new HashMap<>();
		
		paramMap.put("bno", bno);
		paramMap.put("cri", cri);
		
		return session.selectList(LISTPAGE, paramMap);
	}

	@Override
	public int getToalCount(Integer bno) {
		return session.selectOne(GETTOTALCOUNT, bno);	//GETTOTALCOUNT는 매퍼 쿼리문, BNO는 값을 가져와, 서비스에 넘겨준다.
	}

}
