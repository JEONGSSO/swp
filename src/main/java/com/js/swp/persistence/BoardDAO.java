package com.js.swp.persistence;

import java.util.List;

import com.js.swp.domain.Board;

public interface BoardDAO {
	
public void create(Board board) throws Exception;
public Board read(Integer bno) throws Exception;
public void update(Board board) throws Exception;
public void delete(Integer bno) throws Exception;
public List<Board> listAll() throws Exception;
public Integer getMaxbno();

}
