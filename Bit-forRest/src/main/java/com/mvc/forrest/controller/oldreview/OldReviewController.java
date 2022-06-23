package com.mvc.forrest.controller.oldreview;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.OldReview;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldreview.OldReviewService;




@Controller
@RequestMapping("/oldReview/*")
public class OldReviewController {
	
	@Autowired
	public OldReviewService oldReviewService;
	
	@Autowired
	public OldService oldService;
	
	@GetMapping("addOldReview")
	public String addOldReview(Model model) throws Exception {
				
		return "/oldReview/addOldReview";
	}
		
	@PostMapping("addOldReview")
	public String addOldReview(@ModelAttribute("oldReview") OldReview oldReview, Model model) throws Exception {

		if("duplication"==null) {
			// 중복이면 돌려보내면서 +errMsg=1 errMsg 1=> swal.fire(이미 리뷰를 남긴 거래건 입니다)
		}
		
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loginuserId = loginUser.getUser().getUserId();
		User user= new User();
		user.setUserId(loginuserId);
		oldReview.setReviewUser(user);
		
		oldReviewService.addOldReview(oldReview);
		oldService.updateOldSold(oldReview.getOld().getOldNo());

		return "redirect:/old/listOld";
	}
	
	@PostMapping("listOldReview")
	public String listOldReview(@ModelAttribute("oldReview") OldReview oldReview, Model model) throws Exception {
		
		oldReviewService.getOldReviewList(oldReview.getReviewedUser().getUserId());
		
		return null;
	}	
	
}