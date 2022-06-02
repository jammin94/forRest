package com.mvc.forrest.controller.old;

import java.util.Map;

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
	
	
	@GetMapping("listOld/{oldNo}")
	public String listOld() throws Exception {
		
		System.out.println(this.getClass()+"겟리스트");
		
		return "/old/listOld";	
	}
	
	@PostMapping("listOld")
	public String listOld(@ModelAttribute("map") Map map ) throws Exception{
		System.out.println(this.getClass()+ "포스트리스트");
		oldService.getOldList(null);
		return "old/listOld";
		//return "redirect:/old/listOld?oldNo="+old.getOldNo();
	}
	
	
	@GetMapping("getOld/{oldNo}")
	public String getOld() throws Exception {
		
		System.out.println(this.getClass());
		
		return "/old/getOld";	
		
	}
	
	@GetMapping("getMyOld/{oldNo}")
	public String getMyOld() throws Exception {
		
		System.out.println(this.getClass());
		
		return "/old/getOld";	
		
	}
	
	@GetMapping("updateOld/{oldNo}")
	public String updateOld( ) throws Exception{
		System.out.println(this.getClass()+ "겟수정");
		return "old/updateOld";
	} 
	
	@PostMapping("updateOld")
	public String updateOld( @RequestParam("old") Old old) throws Exception{
		System.out.println(this.getClass()+ "포스트수정");
		oldService.updateOld(old);
		
		return "old/getOld";
	} 
	
	
	@GetMapping("deleteOld/{oldNo}")
	public String deleteOld() throws Exception{
		return "old/listOld";
	}
	
	@PostMapping("updateOldState")
	public String updateOldState( @RequestParam("old") Old old) throws Exception{
		System.out.println(this.getClass()+ "포스트상태");
		oldService.updateOld(old);
		
		return "old/getOld";
	} 
	
	@PostMapping("addOldReport")
	public String addOldReport( @RequestParam("old") Old old) throws Exception{
		System.out.println(this.getClass()+ "포스트상태");
		oldService.updateOld(old);
		
		return "old/getOld";
	} 
	
	
	
}
