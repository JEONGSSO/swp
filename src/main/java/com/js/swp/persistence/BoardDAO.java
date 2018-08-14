package com.js.swp.persistence;

import java.util.List;

import com.js.swp.domain.Board;

public interface BoardDAO {
	
public void create(Board b) throws Exception;
public Board read(Integer bno) throws Exception;
public void update(Board b) throws Exception;
public void delete(Integer bno) throws Exception;
public List<Board> listAll() throws Exception;

}
