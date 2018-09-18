package com.js.swp.persistence;

	import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
	import org.apache.ibatis.session.SqlSession;
	import org.springframework.stereotype.Repository;
	import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;
	
	@Repository
	public class BoardDAOImpl implements BoardDAO
	{
		@Inject
		private SqlSession session;
		private static String namespace="com.js.swp.mapper.BoardMapper";
		
		@Override
		public void create (Board b) throws Exception
		{
			session.insert(namespace+".create", b);
		}
		
		public Board read (Integer bno) throws Exception
		{
			return session.selectOne(namespace+".read", bno);
		}
		
		@Override
		public void update (Board b) throws Exception
		{
			session.update(namespace+".update", b);
		}
		
		public void delete (Integer bno) throws Exception
		{
			session.selectOne(namespace+".delete", bno);
		}
		
		@Override
		public List <Board> listAll() throws Exception
		{
			return session.selectList(namespace+".listAll");
		}
		
		@Override
		public List<Board>listPage(int page) throws Exception {
			if (page <= 0) {
				page = 1;
			}
			page = (page - 1) * 10;
			return session.selectList(namespace+".listPage", page);
		}
		
		@Override
		public List<Board>listCriteria(Criteria criteria) throws Exception {
			return session.selectList(namespace+".listCriteria", criteria);
		}

		@Override
		public Integer getMaxbno() {
			return session.selectOne(namespace + ".getMaxbno");
		}

		@Override
		public int countPaging(Criteria criteria) throws Exception {
			return session.selectOne(namespace+".countPaging", criteria);
		}
		
//0918@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		@Override
		public void updateReplycnt(Integer bno, int amt) throws Exception
		{
//			System.out.println(">>>>>>>>>>>>>>>>>" + bno);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bno", bno);
			paramMap.put("amt", amt);
			session.update(namespace+".updateReplycnt", paramMap);
		}

		@Override
		public void plusViewcnt(Integer bno) throws Exception
		{
			session.update(namespace+".plusViewcnt", bno);
		}
}
