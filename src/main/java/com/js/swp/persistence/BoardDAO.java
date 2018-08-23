package com.js.swp.persistence;

import java.util.List;

import com.js.swp.domain.Board;
import com.js.swp.domain.Criteria;

public interface BoardDAO {
	
void create(Board board) throws Exception;
Board read(Integer bno) throws Exception;
void update(Board board) throws Exception;
void delete(Integer bno) throws Exception;
List<Board> listAll() throws Exception;
Integer getMaxbno();
List<Board> listPage(int page) throws Exception;
List<Board> listCriteria(Criteria criteria) throws Exception;
int countPaging(Criteria criteria) throws Exception;
}
