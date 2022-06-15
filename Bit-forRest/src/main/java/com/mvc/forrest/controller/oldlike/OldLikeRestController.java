package com.mvc.forrest.controller.oldlike;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.OldLike;
import com.mvc.forrest.service.oldlike.OldLikeService;




@RestController
@RequestMapping("/oldLike/*")
public class OldLikeRestController {
	
	@Autowired
	public OldLikeService oldLikeService;
	

	
	@PostMapping("addOldLike")
	public boolean addOldLike(@RequestBody String oldNo) throws Exception {
		
		Old old = new Old();
		OldLike oldLike = new OldLike();
		System.out.println(oldNo);
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		oldLike.setUserId(userId);
		old.setOldNo(oldNo);
		oldLike.setOld(old);
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		if(!oldLikeService.oldLikeDuplicationCheck(oldLike)) {
		oldLikeService.addOldLike(oldLike);
		return true;
		}
		
		
		return false;
	}
	
	@PostMapping("deleteOldLikeOnList")
	public boolean deleteOldLikeOnList(@RequestBody String oldNo) throws Exception {
		
		Old old = new Old();
		OldLike oldLike = new OldLike();
		System.out.println(oldNo);
		LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId= loginUser.getUser().getUserId();
		oldLike.setUserId(userId);
		old.setOldNo(oldNo);
		oldLike.setOld(old);
		
		
		System.out.println("중고거래 찜 삭제 테스트 중");
		
		if(oldLikeService.oldLikeDuplicationCheck(oldLike)) {
			oldLikeService.deleteOldLikeOnlist(oldLike);
			return true;
		}
		
		
		return false;
	}	
	
	
	@PostMapping("deleteOldLike")
	public String deleteOldLike(@ModelAttribute("oldLike") OldLike oldLike, Model model) throws Exception {
		

		
		oldLikeService.deleteOldLike(oldLike.getOldLikeNo());
		
		System.out.println("중고거래 찜하기 테스트 중");
		
		return null;
	}	
	
}