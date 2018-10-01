package com.js.swp.service;

import java.util.List;
import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;

public interface BoardService {
	
	void regist(Board board) throws Exception;	// 레지스트 메소드 필욧
	
	Board read(Integer bno) throws Exception;	// 읽기 정수형 BNO
	
	void modify(Board board) throws Exception;	//수정 보드가 딸려(?)나감
	
	void remove(Integer bno) throws Exception;	//지우기 정수형 bno
	
	List<Board> listAll() throws Exception;	//listAll메소드 List는 <Board>를 담고있다.
	
	List<Board> listCriteria(Criteria criteria) throws Exception;
	
	int listCountCriteria(Criteria criteria) throws Exception;

	List<String> getAttach(Integer bno);	//ok
	
}
