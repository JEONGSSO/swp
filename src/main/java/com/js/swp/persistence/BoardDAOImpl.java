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
//		private static final String UPDATE_CNT = namespace + ".updateCnt";
		
		@Override
		public void create (Board board) throws Exception
		{
			session.insert(namespace+".create", board);
		}
		
		@Override
		public void createWithAttach(Board board) throws Exception
		{
			String[] files = board.getFiles();
			if(files == null) return;	//파일이 널일때 종료
			
			Integer lastid = session.selectOne(namespace + ".getLastId");
			System.out.println("QQQQQQQQQQQQQQQQQ>>" + lastid);
			
			for(String file : files)	//10-01
				session.insert(namespace+".addAttach", file);			
		}
		
		public Board read (Integer bno) throws Exception
		{
			return session.selectOne(namespace+".read", bno);
		}
		
		@Override
		public void update (Board board) throws Exception
		{
			session.update(namespace+".update", board);
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
			System.out.println(">>>>>>>>>>>>>>>>>" + bno);
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("bno", bno);
			paramMap.put("amt", amt);
			session.update(namespace+".updateReplycnt", paramMap);
		}

		@Override
		public void plusViewcnt(Integer bno)
		{
			session.update(namespace+".plusViewcnt", bno);
		}
		
//0928@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		@Override
		public void addAttach(String fullName)
		{
//			System.out.println("daoimpl>>>>" + file);	// 풀네임 잘 넘어옴
			session.insert(namespace+".addAttach", fullName);
		}
		
//1001@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		@Override
		public List<String> getAttach(Integer bno)
		{
			return session.selectList(namespace+".getAttach", bno);
		}

		@Override
		public Integer getLastId() {
			return session.selectOne(namespace + ".getLastId");
		}
		
//1002 파일 삭제@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		@Override
		public void removeAttach(String fileName)
		{
			session.delete(namespace+".delAttach", fileName);
		}
		
		@Override
		public void appendAttach(String fullName, Integer bno)
		{
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("bno", bno);
			paramMap.put("fullName", fullName);
			session.insert(namespace+".appendAttach", paramMap);
		}

		@Override
		public void deleteAllAttaches(Integer bno)
		{
			session.delete(namespace+".deleteAllAttaches", bno);
		}


		
		
}
