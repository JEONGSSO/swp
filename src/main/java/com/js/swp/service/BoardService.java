package com.js.swp.service;

import java.util.List;
import com.js.swp.domain.Board;

public interface BoardService {
	
	public void regist(Board board) throws Exception;
	
	public Board read(Integer bno) throws Exception;
	
	public void modify(Board board) throws Exception;
	
	public void remove(Integer bno) throws Exception;
	
	public List<Board> listAll() throws Exception;
}
