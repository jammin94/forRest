package com.mvc.forrest.service.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.board.BoardDAO;
import com.mvc.forrest.service.domain.Board;



@Service
public class BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	
	public void addBoard(Board board) throws Exception{
		System.out.println("addBoard 실행 됨");
		boardDAO.addBoard(board);
	}
	
	public List<Board> getListBoard(Map<String,Object> map) throws Exception{
		System.out.println("getListBoard 실행 됨");
		return boardDAO.getListBoard(map);
	}
	
	public void updateBoard(Board board)  throws Exception{
		System.out.println("updateBoard 실행 됨");
		boardDAO.updateBoard(board);
	}
	
	public void deleteBoard(int boardNo) throws Exception{
		System.out.println("deleteBoard 실행 됨");
		boardDAO.deleteBoard(boardNo);
	}
	
	public Board getBoard(int boardNo) throws Exception{
		System.out.println("getBoard 실행 됨");
		return boardDAO.getBoard(boardNo);
	}
	
	public void updateFixBoard(Board board) throws Exception{
		System.out.println("updateFixBoard 실행 됨");
		boardDAO.updateFixBoard(board);
	}
	
	public int getTotalCount(Map<String,Object> map) throws Exception{
		return boardDAO.getTotalCount(map);
	}

}
