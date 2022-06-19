package com.mvc.forrest.dao.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Board;

@Mapper
public interface BoardDAO {
	
	public void addBoard(Board board) throws Exception;
	
	public List<Board> getListBoard(Map<String,Object> map)  throws Exception;
	
	public void updateBoard(Board board)  throws Exception;
	
	public void deleteBoard(int boardNo) throws Exception;
	
	public Board getBoard(int boardNo) throws Exception;
	
	public void updateFixBoard(Board board) throws Exception;
	
	public int getTotalCount(Map<String,Object> map) throws Exception;
	
}
