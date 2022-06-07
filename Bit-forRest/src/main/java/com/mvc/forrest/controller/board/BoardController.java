package com.mvc.forrest.controller.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;


@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	public BoardService boardService;
	
	@GetMapping("getAnnounce?boardNo={boardNo}")
	public String getAnnounce(@PathVariable int boardNo, Model model) throws Exception {	
		System.out.println("Controller GET: getAnnounce ");
		
		model.addAttribute(boardService.getBoard(boardNo));
		
		return "/board/getAnnounce";
	}
	
	@GetMapping("addAnnounce")
	public String addAnnounce() throws Exception {	
		System.out.println("Controller GET: addAnnounce ");
		return "/board/addAnnounce";
	}
	
	@PostMapping("addAnnounce")
	public String addAnnounce(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: addAnnounce ");
		board.setBoardFlag("A"); //Announce setting
		boardService.addBoard(board);
		return "redirect:/board/listAnnounce";
	}
	
	@GetMapping("updateAnnounce?boardNo={boardNo}")
	public String updateAnnounce(@PathVariable int boardNo, Model model) throws Exception {	
		System.out.println("Controller GET: updateAnnounce ");
		model.addAttribute(boardService.getBoard(boardNo));
		
		return "forward:/board/updateAnnounce";
	}
	
	@PostMapping("updateAnnounce")
	public String updateAnnounce(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: updateAnnounce ");
		boardService.updateBoard(board);
		return "redirect:/board/getAnnounce?boardNo="+board.getBoardNo();
	}
	
	//상세페이지에서 삭제
	@GetMapping("deleteAnnounce?boardNo={boardNo}")
	public String deleteAnnounce(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: deleteAnnounce ");
		boardService.deleteBoard(boardNo);
		return "redirect:/board/listAnnounce";
	}	
	//상세페이지에서 고정
	@GetMapping("updateFixAnnounce?boardNo={boardNo}")
	public String updateFixAnnounce(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: updateFixAnnounce ");
		Board board=boardService.getBoard(boardNo);
		boardService.updateFixBoard(board);
		return "redirect:/board/listAnnounce";
	}
	
	
	@PostMapping("deleteAnnounce")
	public String deleteAnnounce(@RequestParam("eachSelector") int[] arr ) throws Exception {	
		System.out.println("Controller POST: deleteAnnounce ");
		for(int boardNo : arr) {
			boardService.deleteBoard(boardNo);
		}
		
		return "redirect:/board/listAnnounce";
				
	}	
		
	
	@PostMapping("updateFixAnnounce")
	public String updateFixAnnounce(@RequestParam("eachSelector") int[] arr ) throws Exception {	
		System.out.println("Controller POST: updateFixAnnounce ");
		for(int boardNo : arr) {
			System.out.println(boardNo);
			boardService.updateFixBoard(boardService.getBoard(boardNo));
		}
		
		return "redirect:/board/listAnnounce";
	}
	
	
	@RequestMapping("listAnnounce")
	public String getlistAnnounce(@ModelAttribute("search") Search search, Model model) throws Exception {	
		System.out.println("Controller GET: getlistAnnounce ");
		
		Board board= new Board();
		board.setBoardFlag("A");//AnnounceList Set
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(10); //장씩
		
		int newStartRowNum=search.getStartRowNum()-1;
		//search에서의 startRowNum이 내 버전이랑 조금 차이가 있다... 
		//그래서 직접넣자 걍
		
		System.out.println("search : "+search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		map.put("newStartRowNum", newStartRowNum);
		
		Page resultPage = new Page(search.getCurrentPage(), boardService.getTotalCount(map), 5, 5);
		System.out.println(resultPage);

		model.addAttribute("list",boardService.getListBoard(map));
		model.addAttribute("resultPage",resultPage);
		model.addAttribute("search",search);

		return "board/listAnnounce";
	}
	
	@GetMapping("addFAQ")
	public String addFAQ() throws Exception {	
		System.out.println("Controller GET: addFAQ ");
		return "redirect:/board/addFAQ";
	}
	
	@PostMapping("addFAQ")
	public String addFAQ(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: addFAQ ");
		board.setBoardFlag("F"); //FAQ setting
		boardService.addBoard(board);
		return "redirect:/board/listFAQ";
	}
	
	@PostMapping("updateFAQ")
	public String updateFAQ(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: updateFAQ ");
		boardService.updateBoard(board);
		return "redirect:/board/listFAQ";
	}
	
	@GetMapping("deleteFAQ/{boardNo}")
	public String deleteFAQ(@PathVariable int boardNo) throws Exception {	
		System.out.println("Controller GET: deleteFAQ ");
		boardService.deleteBoard(boardNo);
		return "redirect:/board/listFAQ";
	}
	
	@RequestMapping("listFAQ")
	public String getlistFAQ(@ModelAttribute("search") Search search, Model model) throws Exception {	
		System.out.println("Controller GET: getlistFAQ ");



		return "board/faq";
	}
	
}
