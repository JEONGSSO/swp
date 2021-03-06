package com.js.swp.persistence;

import java.util.List;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;

public interface BoardDAO {
	
	void create(Board board) throws Exception;
	
	void createWithAttach(Board board) throws Exception;
	
	Board read(Integer bno) throws Exception;
	
	void update(Board board) throws Exception;
	
	void delete(Integer bno) throws Exception;
	
	List<Board> listAll() throws Exception;
	
	Integer getMaxbno();
	
	Integer getLastId();		//1002
	
	List<Board> listPage(int page) throws Exception;
	
	List<Board> listCriteria(Criteria criteria) throws Exception;
	
	int countPaging(Criteria criteria) throws Exception;
	
	void updateReplycnt(Integer bno, int amt) throws Exception;
	
	void plusViewcnt(Integer bno) throws Exception;
	
	void addAttach(String fullName);	//ok
	
	List<String> getAttach(Integer bno);	//ok

	void removeAttach(String fileName);

	void appendAttach(String fullName, Integer bno);

	void deleteAllAttaches(Integer bno);
}
