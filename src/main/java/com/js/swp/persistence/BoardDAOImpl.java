package com.js.swp.persistence;

	import java.util.List;
	import javax.inject.Inject;
	import org.apache.ibatis.session.SqlSession;
	import org.springframework.stereotype.Repository;
	import com.js.swp.domain.Board;
	
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
		public Integer getMaxbno() {
			return session.selectOne(namespace + ".getMaxbno");
		}
}
