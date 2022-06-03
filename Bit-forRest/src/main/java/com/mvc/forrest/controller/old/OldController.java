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
import com.mvc.forrest.service.domain.OldLike;
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
	
	@RequestMapping("oldTest")
	public String OldTest(@ModelAttribute("old") Old old, Model model) throws Exception {
		
		old.setUserId(getOld());
		old.setOldTitle("야전침대");
		
		return null;
		
	}
	@GetMapping("listOld")
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
	
	
	@GetMapping("getOld")
	public String getOld() throws Exception {
		
		System.out.println(this.getClass());
		
		return "/old/getOld";	
		
	}
	 
	
	@GetMapping("updateOld)"
			+ "")
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
	
	
	@PostMapping("deleteOld")
	public String deleteOld(@ModelAttribute("old") Old old, Model model) throws Exception{
		oldService.deleteOld(old.getOldNo());
		
		System.out.println("중고거래 찜하기 테스트 중");
		return "old/listOld";
	}
	
	@PostMapping("updateOldState")
	public String updateOldState( @RequestParam("old") Old old,Model model) throws Exception{
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
