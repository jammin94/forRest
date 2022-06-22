package com.mvc.forrest.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileUtils;
import com.mvc.forrest.service.board.BoardService;
import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Img;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;


@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	public BoardService boardService;
	
	@Autowired
	public FileUtils fileUtils;
	
	//getAnnounce navi
	//비회원, 회원, 관리자
	@GetMapping("getAnnounce")	
	public String getAnnounce(@RequestParam("boardNo") int boardNo, Model model) throws Exception {	
		System.out.println("Controller GET: getAnnounce ");
		
		model.addAttribute("board", boardService.getBoard(boardNo));
		model.addAttribute("imageList", fileUtils.getAnnounceImgList(Integer.toString(boardNo)));
		
		return "/board/getAnnounce";
	}
	
	//addAnnounce navi
	//관리자
	@GetMapping("addAnnounce")
	public String addAnnounce() throws Exception {	
		System.out.println("Controller GET: addAnnounce ");
		return "/board/addAnnounce";
	}
	
	//addAnnounce 실행
	//관리자
	@PostMapping("addAnnounce")
	public String addAnnounce(@ModelAttribute("board") Board board, @RequestParam List<MultipartFile> uploadFiles) throws Exception {	
		System.out.println("Controller POST: addAnnounce ");
		System.out.println(board);
		board.setBoardFlag("A"); //Announce setting
		boardService.addBoard(board);
		
		String prodImg = 	fileUtils.uploadFiles(uploadFiles, Integer.toString(board.getBoardNo()), "announce");
		
		return "redirect:/board/listAnnounce";
	}
	
	//updateAnnounce navi
	//관리자
	@GetMapping("updateAnnounce")
	public String updateAnnounce(@RequestParam("boardNo") int boardNo, Model model) throws Exception {	
		System.out.println("Controller GET: updateAnnounce ");
		System.out.println("시스템으로 주는 데이터 : "+boardService.getBoard(boardNo));
		model.addAttribute(boardService.getBoard(boardNo));
	
		return "/board/updateAnnounce";
	}
	
	//updateAnnounce 실행
	//관리자
	@PostMapping("updateAnnounce")
	public String updateAnnounce(@ModelAttribute("board") Board board) throws Exception {	
		System.out.println("Controller POST: updateAnnounce ");
		System.out.println("시스템으로 받은 데이터 : "+board);
		boardService.updateBoard(board);
		
		return "redirect:/board/getAnnounce?boardNo="+board.getBoardNo();
	}
	
	//상세페이지에서 삭제
	//관리자
	@GetMapping("deleteAnnounce")
	public String deleteAnnounce(@RequestParam("boardNo") int boardNo) throws Exception {	
		System.out.println("Controller GET: deleteAnnounce ");
		boardService.deleteBoard(boardNo);
		return "redirect:/board/listAnnounce";
	}	
	//상세페이지에서 고정
	//관리자
	@GetMapping("updateFixAnnounce")
	public String updateFixAnnounce(@RequestParam("boardNo") int boardNo) throws Exception {	
		System.out.println("Controller GET: updateFixAnnounce ");
		Board board=boardService.getBoard(boardNo);
		boardService.updateFixBoard(board);
		return "redirect:/board/listAnnounce";
	}
	
	//listAnnounce에서 여러개 삭제
	//관리자
	@PostMapping("deleteAnnounce")
	public String deleteAnnounce(@RequestParam("eachSelector") int[] arr ) throws Exception {	
		System.out.println("Controller POST: deleteAnnounce ");
		for(int boardNo : arr) {
			boardService.deleteBoard(boardNo);
		}
		return "redirect:/board/listAnnounce";		
	}	
		
	//listAnnounce에서 여러개 고정
	//관리자
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
	//회원, 비회원, 관리자
	//public String getlistAnnounce(@ModelAttribute("search") Search search, Model model, HttpSession session) throws Exception {	
	public String getlistAnnounce(@ModelAttribute("search") Search search, Model model) throws Exception {	
		System.out.println("Controller GET: getlistAnnounce ");
		
		Board board= new Board();
		board.setBoardFlag("A");//AnnounceList Set
		
		int pageSize=10; //n장씩
		int pageUnit=5;
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); 
		
		System.out.println("search : "+search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		
		Page resultPage = new Page(search.getCurrentPage(), boardService.getTotalCount(map), pageUnit, pageSize);
		System.out.println(resultPage);

		model.addAttribute("list", boardService.getListBoard(map));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "board/listAnnounce";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	
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
		
		Board board= new Board();
		board.setBoardFlag("F");
		
		int pageSize=10; //n장씩
		int pageUnit=5;
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize); 
		
		int newStartRowNum=(search.getCurrentPage()-1)*pageSize;
		//search에서의 startRowNum이 내 버전이랑 조금 차이가 있다... 
		//그래서 직접넣자 걍
		
		System.out.println("newStartRowNum : "+newStartRowNum);
		System.out.println("search : "+search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("search", search);
		map.put("newStartRowNum", newStartRowNum);
		
		Page resultPage = new Page(search.getCurrentPage(), boardService.getTotalCount(map), pageUnit, pageSize);
		System.out.println(resultPage);

		model.addAttribute("list", boardService.getListBoard(map));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		


		return "board/faq";
	}
	
}
