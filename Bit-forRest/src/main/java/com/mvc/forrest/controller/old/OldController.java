package com.mvc.forrest.controller.old;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.forrest.common.utils.FileUtils;
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

	@RequestMapping("listOld")
	public String listOld(@ModelAttribute("search") Search search, Model model) throws Exception {
//		public String listOld(@ModelAttribute("search") Search search, Model model,HttpSession httpsession) throws Exception{

		System.out.println(this.getClass() + "겟리스트");

//		if(search.getCurrentPage() ==0 ){
//			search.setCurrentPage(1);
//		}
//		search.setPageSize(pageSize);
//		
//			
		Map<String, Object> map = oldService.getOldList(search);

		System.out.println(this.getClass() + "포스트리스트");

//		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		System.out.println(resultPage);

//		User sessionUser= (User) httpsession.getAttribute("user");
		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "old/listOld";
		// return "redirect:/old/listOld?oldNo="+old.getOldNo();
	}

	@RequestMapping("getOld")
	public String getOld(@ModelAttribute("search") Search search, @RequestParam("oldNo") int oldNo, Model model)
			throws Exception {

		// 디버깅
		System.out.println("getOld Start");

		// oldService의 getOld 메서드에 oldNo 인자값을 넣어줌
		Old old = oldService.getOld(oldNo);

		Map<String, Object> map = oldService.getOldList(search);

		model.addAttribute("old", old);
		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		Old testOld = oldService.getOld(oldNo);
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

		int oldNo = 33;

		oldService.addOld(old);
		fileUtils.uploadFiles(uploadFile, oldNo, "old");
		old.setOldNo(oldNo);

		System.out.println(old);

		model.addAttribute("old", old);

		return "redirect:/old/listOld";

	}

	@GetMapping("updateOld")

	public String updateOld(@RequestParam("oldNo") int oldNo, Model model) throws Exception {
		System.out.println(this.getClass() + "겟수정");

		oldService.getOld(oldNo);
	
		return "old/updateOld";
	}

	@PostMapping("updateOld")
	public String updateOld(@RequestParam("old") Old old) throws Exception {
		System.out.println(this.getClass() + "포스트수정");
		oldService.updateOld(old);
	
		return "redirect:/old/listOld";
	}

	@PostMapping("deleteOld")
	public String deleteOld(@ModelAttribute("old") Old old, Model model) throws Exception {
		oldService.deleteOld(old.getOldNo());

		System.out.println("중고거래 찜하기 테스트 중");
		return "old/listOld";
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
