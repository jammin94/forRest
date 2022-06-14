package com.mvc.forrest.controller.oldlike;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.oldlike.OldLikeService;




@Controller
@RequestMapping("/oldLike/*")
public class OldLikeController {
	
	@Autowired
	public OldLikeService oldLikeService;
	
	@RequestMapping("addOldLikeTest")
	public String addOldLikeTest(@ModelAttribute("oldLike") OldLike oldLike, Model model) throws Exception {
		
		Old old = new Old();
		oldLike.setUserId("admin");
		old.setOldNo("1");
		oldLike.setOld(old);
		
		oldLikeService.addOldLike(oldLike);
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		return null;
	}
	
	@PostMapping("addOldLike")
	public String addOldLike(@ModelAttribute("oldLike") OldLike oldLike, Model model) throws Exception {
		
		Old old = new Old();
		oldLike.setUserId("admin");
		old.setOldNo("1");
		oldLike.setOld(old);
		
		oldLikeService.addOldLike(oldLike);
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		return null;
	}
	
	@PostMapping("deleteOldLike")
	public String deleteOldLike(@ModelAttribute("oldLike") OldLike oldLike, Model model) throws Exception {
		

		
		oldLikeService.deleteOldLike(oldLike.getOldLikeNo());
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		return null;
	}	
	
}