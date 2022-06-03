package com.mvc.forrest.controller.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Search;


@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	public BoardService boardService;
	
	//for test
	@GetMapping("test")
	public String home() throws Exception {	
		return "board/testBoard";
	}
	
	@GetMapping("getAnnounce/{boardNo}")
	public String getAnnounce(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: getAnnounce ");
		System.out.println(boardService.getBoard(boardNo));
		return "board/getAnnounce";
	}
	
	@GetMapping("getFAQ/{boardNo}")
	public String getFAQ(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: getFAQ ");
		System.out.println(boardService.getBoard(boardNo));
		return "board/getFAQ";
	}
	
	@GetMapping("test/addAnnounce")
	public String addAnnounce() throws Exception {
		Board board = new Board();
		board.setBoardTitle("요요요요ㅁ");
		board.setBoardDetail("이이이이이ㄴ");
		//board.setCategory("몰라용");
		board.setBoardFlag("A");
		
		boardService.addBoard(board);
		return null;
	}
	
	
	@PostMapping("addAnnounce")
	public String addAnnounce(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: addAnnounce ");
		boardService.addBoard(board);
		return null;
	}
	
	@PostMapping("addFAQ")
	public String addFAQ(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: addFAQ ");
		boardService.addBoard(board);
		return null;
	}
	
	@PostMapping("updateAnnounce")
	public String updateAnnounce(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: updateAnnounce ");
		boardService.updateBoard(board);
		return null;
	}
	
	@PostMapping("updateFAQ")
	public String updateFAQ(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: updateFAQ ");
		boardService.updateBoard(board);
		return null;
	}
	
	
	@GetMapping("deleteAnnounce/{boardNo}")
	public String deleteAnnounce(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: deleteAnnounce ");
		boardService.deleteBoard(boardNo);
		return null;
	}
	
	@GetMapping("deleteFAQ/{boardNo}")
	public String deleteFAQ(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: deleteFAQ ");
		boardService.deleteBoard(boardNo);
		return null;
	}
	
	@GetMapping("updateFixAnnounce/{boardNo}")
	public String updateFixAnnounce(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: updateFixAnnounce ");
		Board board=boardService.getBoard(boardNo);
		boardService.updateFixBoard(board);
		return null;
	}
	
	@PostMapping("listAnnounce")
	public String getlistAnnounce(@ModelAttribute("search") Search search) throws Exception {	
		System.out.println("Controller GET: getlistAnnounce ");
		Board board= new Board();
		board.setBoardFlag("A");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		boardService.getListBoard(map);
		System.out.println(boardService.getListBoard(map));
		return null;
	}
	
	@PostMapping("listFAQ")
	public String getlistFAQ(@ModelAttribute("search") Search search) throws Exception {	
		System.out.println("Controller GET: getlistFAQ ");
		Board board= new Board();
		board.setBoardFlag("F");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		boardService.getListBoard(map);
		System.out.println(boardService.getListBoard(map));
		return null;
	}
	
}
