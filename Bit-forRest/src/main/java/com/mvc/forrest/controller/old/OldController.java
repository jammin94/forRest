package com.mvc.forrest.controller.old;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.HttpRequest;
import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.common.utils.FileUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.OldReview;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldlike.OldLikeService;
import com.mvc.forrest.service.oldreview.OldReviewService;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/old/*")
public class OldController {

	// Field
	@Autowired
	public OldService oldService;

	@Autowired
	public UserService userService;

	@Autowired
	public OldLikeService oldLikeService;

	@Autowired
	public OldReviewService oldReviewService;

	@Autowired
	public FileUtils fileUtils;

	@Value("5")
	int pageUnit;

	@Value("10")
	int pageSize;

	///////////////////////////////////////////////////
	@RequestMapping("listOldAfterLogin")
	public String listOldAfterLogin(@ModelAttribute("search") Search search, Model model, HttpRequest httpRequest) throws Exception {

		System.out.println(this.getClass() + "겟리스트");


			
			LoginUser loginUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId= loginUser.getUser().getUserId();
			List<Old> list= oldService.getOldListHasUser(search, userId);
			model.addAttribute("list", list);
		

		System.out.println(this.getClass() + "포스트리스트");

//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		System.out.println(resultPage);

//		User sessionUser= (User) httpsession.getAttribute("user");
//		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "old/listOld";
	}
	
	
	@RequestMapping("listOld")
	public String listOld(@ModelAttribute("search") Search search, Model model, HttpRequest httpRequest) throws Exception {

		System.out.println(this.getClass() + "겟리스트");
			
			Map<String, Object> map = oldService.getOldList(search);
			model.addAttribute("list", map.get("list"));
		
		System.out.println(this.getClass() + "포스트리스트");

//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		System.out.println(resultPage);

//		User sessionUser= (User) httpsession.getAttribute("user");
//		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "old/listOld";
	}

	@RequestMapping("getOld")
	public String getOld(@ModelAttribute("search") Search search, @RequestParam("oldNo") String oldNo, Model model)
			throws Exception {

		// 디버깅
		System.out.println("getOld Start");
		//복사 붙여넣기 너무 심하게 한다.. list받아오는 거랑 하나 받아오는거랑 method 가 똑같아..
		
		// oldService의 getOld 메서드에 oldNo 인자값을 넣어줌
		Old old = oldService.getOld(oldNo); // 1

//		Map<String, Object> map = oldService.getOldList(search); 이게 왜 들어갔는지 설명해줘

		model.addAttribute("old", old);
//		model.addAttribute("list", map.get("list")); 이게 왜 들어갔는지 설명해줘
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		Old testOld = oldService.getOld(oldNo); // 1을 하고 2를 한 이유는?
		String testUserId = testOld.getUserId();

		User user = userService.getUser(testUserId);
		System.out.println("불러온 유저" + user);

		double oldReview = oldReviewService.getUserRate(testUserId);
		user.setUserRate(oldReview);

		model.addAttribute("oldReview", user);
		return "old/getOld";
	}

	@GetMapping("addOld")
	public String addOld(Model model) throws Exception {
		System.out.println(this.getClass() + " ADD올드 네비게이션");
		return "old/addOld";
	}

	@PostMapping("addOld")

	public String addOld(@ModelAttribute("old") Old old, @RequestParam("uploadFile") List<MultipartFile> uploadFile,

			Model model) throws Exception {

		System.out.println(this.getClass() + " ADD올드 POST");

		String oldNo = FileNameUtils.getRandomString();
		System.out.println(oldNo);
		// add하기 전에 oldNo가 set 되어야 함.
		old.setOldNo(oldNo);

		// flag: old인지 product인지
		fileUtils.uploadFiles(uploadFile, oldNo, "old");

		System.out.println(old);

		oldService.addOld(old);
		model.addAttribute("old", old);

		return "redirect:/old/listOld";

	}

	@GetMapping("updateOld")

	public String updateOld(@RequestParam("oldNo") String oldNo, Model model) throws Exception {
		System.out.println(this.getClass() + "겟수정");
		Old old = oldService.getOld(oldNo);
		oldService.getOld(oldNo);
		model.addAttribute("old", old);
		return "old/updateOld";
	}

	@PostMapping("updateOld")

	public String updateOld(@ModelAttribute("old") Old old, @RequestParam("uploadFile") List<MultipartFile> uploadFile,

			Model model) throws Exception {

		System.out.println(this.getClass() + " UPDATE올드 POST");

		String oldNo = FileNameUtils.getRandomString();
		System.out.println(oldNo);
		// add하기 전에 oldNo가 set 되어야 함.
		old.setOldNo(oldNo);

		// flag: old인지 product인지
		fileUtils.uploadFiles(uploadFile, oldNo, "old");

		System.out.println(old);

		oldService.addOld(old);
		model.addAttribute("old", old);

		return "redirect:/old/listOld";
	}

	@RequestMapping("deleteOld")
	public String deleteOld(@ModelAttribute("oldNo") String oldNo) throws Exception {
		System.out.println("delete");
		
		oldService.deleteOld(oldNo);
		return "redirect:/old/listOld";
	}

	@PostMapping("updateOldState")
	public String updateOldState(@RequestParam("old") Old old, Model model) throws Exception {
		System.out.println(this.getClass() + "포스트상태");
		oldService.updateOld(old);

		return "old/getOld";
	}

	@PostMapping("addOldReport")
	public String addOldReport(@RequestParam("old") Old old) throws Exception {
		System.out.println(this.getClass() + "포스트상태");
		oldService.updateOld(old);

		return "old/getOld";
	}

}
