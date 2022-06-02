package com.mvc.forrest.controller.old;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.old.OldService;

@Controller
@RequestMapping("/old/*")
public class OldController {

	//Field
	@Autowired
	public OldService oldService;
	
	//public UserService userservice;
	
	//public OldLikeService oldLikeService;
	
	// 중고거래 게시물 상세보기화면으로가는 네비게이터
	//@RequestMapping( value="addOld", method= RequestMethod.GET )
	
	@PostMapping("listOld")/*@ModelAttribute("search") Search search,*/ /*, @RequestParam("oldNo") int oldNo,*/
	public String listOld(@ModelAttribute("old") Old old ,Model model ) throws Exception{
		
		model.addAttribute("old",old);
		return "old/listOld";
	}
	
	
	@GetMapping("getOld/{oldNo}")
	public String getOld() throws Exception {
		
		System.out.println(this.getClass()+ "야호상세");
		return "/old/getOld";	
		
	}
	
	@GetMapping("updateOld/{oldNo}")
	public String updateOld( ) throws Exception{
		System.out.println(this.getClass()+ "야호수정");
		return "old/updateOld";
	} 
	
	@PostMapping("updateOld")
	public String updateOld( @RequestParam("oldNo") int oldNo, HttpServletRequest request, Model model) throws Exception{
		System.out.println(this.getClass()+ "야호수정");
		return "old/getOld";
	} 
	
	
	@PostMapping("deleteOld")
	public String deleteOld(@RequestParam("oldNo") int oldNo, Model model) throws Exception{
		return "old/listOld";
	}
	
	
}
