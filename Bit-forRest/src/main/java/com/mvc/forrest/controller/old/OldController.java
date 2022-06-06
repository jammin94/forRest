package com.mvc.forrest.controller.old;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Board;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldlike.OldLikeService;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/old/*")
public class OldController {

	//Field
	@Autowired
	public OldService oldService;
	
	@Autowired
	public UserService userservice;
	
	@Autowired
	public OldLikeService oldLikeService;
	
	

	
	@Value("5")
	int pageUnit;
	
	@Value("10")
	int pageSize;
	
	///////////////////////////////////////////////////
	
	//리스트 네비게이터//	
	
//	@GetMapping("listOld")
//	public String listOld() throws Exception {
//		
//		System.out.println(this.getClass()+"겟리스트");
//		//return "redirect:/old/listOld";
//		return "old/listOld";	
//	}
//	
	
	//검색시 리스트//
	
	@RequestMapping("listOld")
	public String listOld(@ModelAttribute("search") Search search, Model model) throws Exception{
	
		System.out.println(this.getClass()+ "겟리스트");
		
//		if(search.getCurrentPage() ==0 ){
//			search.setCurrentPage(1);
//		}
//		search.setPageSize(pageSize);
//		
//			
		Map<String, Object> map = oldService.getOldList(search);
		
		System.out.println(this.getClass()+ "포스트리스트");
		
//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		System.out.println(resultPage);
		
		
		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		
		return "old/listOld";
		//return "redirect:/old/listOld?oldNo="+old.getOldNo();
	}

	
	
	
	
//	@GetMapping("getOld/{oldNo}")
//	public String getOld(@PathVariable int oldNo, Model model) throws Exception {
//		
//		System.out.println(this.getClass());
//		
//		model.addAttribute(oldService.getOld(oldNo));
//		System.out.println(oldService.getOld(oldNo));
//		return "old/getOld";
//		//return "forward:/old/getOld";	
//		
//	}
	 
	
	
	@GetMapping("getOld")
	public String getOld( Model model ) throws Exception {
		System.out.println(this.getClass()+ "겟올드");
		return "old/getOld";
	}	
	
	@GetMapping("addOld")
	public String addOld( Model model ) throws Exception {
		System.out.println(this.getClass()+ " ADD올드 네비게이션");
		return "old/addOld";
	}	
	@PostMapping("addOld")
	public String addOld( @RequestParam("old") Old old) throws Exception {
		System.out.println(this.getClass()+ " ADD올드 POST");
		return "old/listOld";
	}	
	@GetMapping("updateOld/{oldNo}")
		
	public String updateOld(@PathVariable int oldNo, Model model ) throws Exception{
		System.out.println(this.getClass()+ "겟수정");
		model.addAttribute(oldService.getOld(oldNo));
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
