package com.mvc.forrest.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;

@Controller
@RequestMapping("/board/*")
public class BoardRestController {
	@Autowired
	public BoardService boardService;
	
	@RequestMapping(value="json/getBoard/{boardNo}", method=RequestMethod.GET)
	public Board addAnnounce(@PathVariable int boardNo) throws Exception {
		
		System.out.println("board/json/getBoard/ : GET");
		return boardService.getBoard(boardNo);
	}
}
