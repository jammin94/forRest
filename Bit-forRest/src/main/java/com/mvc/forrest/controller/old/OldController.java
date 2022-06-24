package com.mvc.forrest.controller.old;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.mvc.forrest.service.chat.ChatRoomService;
import com.mvc.forrest.service.domain.ChatRoom;
import com.mvc.forrest.service.domain.Img;
import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Page;
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

	@Autowired
	public ChatRoomService chatRoomService;
	
	@Value("5")
	int pageUnit;

	@Value("8")
	int pageSize;

	///////////////////// 회원만 가능//////////////////////////////
	@RequestMapping("listOldAfterLogin")
	public String listOldAfterLogin(@ModelAttribute("search") Search search, Model model, HttpRequest httpRequest)
			throws Exception {

		System.out.println(this.getClass() + "겟리스트로그인");
		System.out.println(search);
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = loginUser.getUser().getUserId();
		
		if(search.getSearchCategory()=="") {
			search.setSearchCategory(null);
		}
		
		if(search.getSearchKeyword()=="") {
			search.setSearchKeyword(null);
		}
		
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		List<Old> list = oldService.getOldListHasUser(search, userId);
		
		Page resultPage = new Page(search.getCurrentPage(), oldService.getTotalCount(search), pageUnit, pageSize);
		
		System.out.println("list:"+list);
		model.addAttribute("loginUserId", userId);
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);

		return "/old/listOld";
	}

	///////////////////// 비회원만 가능//////////////////////////////
	@RequestMapping("listOld")
	public String listOld(@ModelAttribute("search") Search search, Model model) throws Exception {

		System.out.println("리스트");
		
		if(search.getSearchCategory()=="") {
			search.setSearchCategory(null);
		}
		
		if(search.getSearchKeyword()=="") {
			search.setSearchKeyword(null);
		}
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		System.out.println("search: "+ search);
		
		
		
		List<Old> list = oldService.getOldList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), oldService.getTotalCount(search), pageUnit, pageSize);
		
		System.out.println("list"+list);
		
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		model.addAttribute("resultPage", resultPage);

		return "old/listOld";
	}
	
	@RequestMapping("listOldMine")
	public String listOldMine(@ModelAttribute("search") Search search, Model model) throws Exception {
		System.out.println("마인마인");

		List<Old> list = oldService.getOldListMine(search);
		
		model.addAttribute("list", list);
		model.addAttribute("search", search);

		return "/old/listOldMine";
	}
	
	
	/////////////////////비회원, 회원, 어드민 가능//////////////////////////////
	
	@RequestMapping("getOld")
	public String getOld(@RequestParam("oldNo") String oldNo,@ModelAttribute("search") Search search, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		    //조회수 쿠키를 통해 약식 구현 => 현업에서는 사실 조회수 자체를 별 신경 안쓰는 분위기라고 함
		    Cookie oldCookie = null;
		    Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equals("oldView")) {
		                oldCookie = cookie;
		            }
		        }
		    }
	
		    if (oldCookie != null) {
		        if (!oldCookie.getValue().contains("[" + oldNo + "]")) {
		            oldService.updateViewCnt(oldNo);
		            oldCookie.setValue(oldCookie.getValue() + "_[" + oldNo + "]");
		            oldCookie.setPath("/");
		            oldCookie.setMaxAge(60 * 60 * 24);
		            response.addCookie(oldCookie);
		        }
		    } else {
		    	oldService.updateViewCnt(oldNo);
		        Cookie newCookie = new Cookie("postView","[" + oldNo + "]");
		        newCookie.setPath("/");
		        newCookie.setMaxAge(60 * 60 * 24);
		        response.addCookie(newCookie);
		    }
		System.out.println("겟올드");
		

//		//유저 평점 가져오기
		Old old = oldService.getOld(oldNo);
		System.out.println(oldNo+"올넘");
		String userId = old.getUserId();

		User user = userService.getUser(userId);
		System.out.println("불러온 유저" + user);

		double oldReview = oldReviewService.getUserRate(userId);
		user.setUserRate(oldReview);
					
		//이미지
		List<Img> oldImgList = fileUtils.getOLdImgList(oldNo);
		System.out.println("올드이미지"+oldImgList);
		

		model.addAttribute("old", old);
		model.addAttribute("oldReview", user);
		model.addAttribute("oldImgList", oldImgList);

		
		// getOld 밑에 list 뜨는 것
		List<Old> list= oldService.getOldListCategory(old);
		List<Old> listUser = oldService.getOldListOthers(old);
		
		
		model.addAttribute("list", list);
		model.addAttribute("listUser", listUser);
		
		return "old/getOld";
	}
	
	////////회원만/////////////
	@RequestMapping("getOldLogIn")
	public String getOldLogIn(@RequestParam("oldNo") String oldNo,@ModelAttribute("search") Search search, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		    //조회수 쿠키를 통해 약식 구현 => 현업에서는 사실 조회수 자체를 별 신경 안쓰는 분위기라고 함
		    Cookie oldCookie = null;
		    Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if (cookie.getName().equals("oldView")) {
		                oldCookie = cookie;
		            }
		        }
		    }
	
		    if (oldCookie != null) {
		        if (!oldCookie.getValue().contains("[" + oldNo + "]")) {
		            oldService.updateViewCnt(oldNo);
		            oldCookie.setValue(oldCookie.getValue() + "_[" + oldNo + "]");
		            oldCookie.setPath("/");
		            oldCookie.setMaxAge(60 * 60 * 24);
		            response.addCookie(oldCookie);
		        }
		    } else {
		    	oldService.updateViewCnt(oldNo);
		        Cookie newCookie = new Cookie("postView","[" + oldNo + "]");
		        newCookie.setPath("/");
		        newCookie.setMaxAge(60 * 60 * 24);
		        response.addCookie(newCookie);
		    }
		
		
		
		//oldLike하트
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loginuserId = loginUser.getUser().getUserId();


//		//유저 평점 가져오기
		Old old = oldService.getOldLogIn(loginuserId, oldNo);
		System.out.println(oldNo+"올넘");
		String userId = old.getUserId();

		User user = userService.getUser(userId);
		System.out.println("불러온 유저" + user);

		double oldReview = oldReviewService.getUserRate(userId);
		user.setUserRate(oldReview);
					
		//이미지
		List<Img> oldImgList = fileUtils.getOLdImgList(oldNo);
	
		List<ChatRoom> chatList= chatRoomService.getListChatRoom(oldNo);
		model.addAttribute("chatList", chatList);
		model.addAttribute("loginUserId", userId);
		model.addAttribute("old", old);
		model.addAttribute("oldReview", user);
		model.addAttribute("oldImgList", oldImgList);
		System.out.println("채팅리스트"+chatList);
		
		// getOld 밑에 list 뜨는 것
		List<Old> list= oldService.getOldListCategory(old);
		List<Old> listUser = oldService.getOldListOthers(old);
		
		
		model.addAttribute("list", list);
		model.addAttribute("listUser", listUser);
		
		
	
		System.out.println(old);
		return "old/getOld";
	}


	// principal : 인증된 사용자 정보 , Authentication : principal 관리 , SecurityContext:
	// Authentication 관리
	// Authentication 자체는 인증된 정보로, SecurityContextHolder 가 가지고 있는 값을 통해 인증 여부를 확인할 수
	// 있다.
	// =>Authentication.isAuthenticated();
	// threadLocal : 한 쓰레드 내에서 사용하는 공용 저장소
	// thread : 프로세스 내에서 실제로 작업을 수행하는 주체. 하나의 프로세스를 구성하는 쓰레드들은 프로세스에 할당된 메모리, 자원을
	// 공유한다.
	// Security는 session보다 깊은 곳에 정보 저장하므로 session에서 userId 받아올 수 없다.
	
	
	/////////////////////회원, 어드민 가능////////////////////////////
	@GetMapping("addOld")
	public String addOld(HttpSession session,  Model model) throws Exception {

		System.out.println(this.getClass() + " ADD올드 네비게이션");

		LoginUser loginUser = (LoginUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		String userId = loginUser.getUser().getUserId();
		
		//add화면에 userId 보여주기 위함
		model.addAttribute("userId", userId);
		
		return "/old/addOld";

	}

/////////////////////회원, 어드민 가능//////////////////////////////	

	@PostMapping("addOld")

	public String addOld(@ModelAttribute("old") Old old, @RequestParam("uploadFile") List<MultipartFile> uploadFile, Model model) throws Exception {

		System.out.println(this.getClass() + " ADD올드 POST");
		
		System.out.println(model.getAttribute("files"));
		System.err.println(model);
		String oldNo = FileNameUtils.getRandomString();
		// add하기 전에 oldNo가 set 되어야 함.
		old.setOldNo(oldNo);

		// flag: old인지 product인지
		String mainImg = 	fileUtils.uploadFiles(uploadFile, oldNo, "old");
	
		old.setOldImg(mainImg);

		oldService.addOld(old);
			
		model.addAttribute("old", old);
		return "redirect:/old/listOld";

	}

/////////////////////회원, 어드민 가능//////////////////////////////
	@GetMapping("updateOld")

	public String updateOld(@RequestParam("oldNo") String oldNo, HttpSession session, Model model) throws Exception {
		
		System.out.println(this.getClass() + "겟수정");

		LoginUser loginUser = (LoginUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		String userId = loginUser.getUser().getUserId();

		
		model.addAttribute(oldService.getOld(oldNo));
		model.addAttribute("oldNo", oldNo);
	
		return "/old/updateOld";
	}

/////////////////////회원, 어드민 가능//////////////////////////////	
	@PostMapping("updateOld")
	public String updateOld(@ModelAttribute("old") Old old, @RequestParam("uploadFile") List<MultipartFile> uploadFile,
			Model model, @RequestParam("oldNo") String oldNo) throws Exception {

		System.out.println(this.getClass() + " UPDATE올드 POST");
		System.out.println("올드" + old);
		System.out.println(oldNo);

		old.setOldNo(oldNo);

		// flag: old인지 product인지
		fileUtils.uploadFiles(uploadFile, oldNo, "old");

		oldService.updateOld(old);

		model.addAttribute("올드", old);

		return "redirect:/old/listOld";
	}

/////////////////////회원, 어드민 가능//////////////////////////////	
	@RequestMapping("deleteOld")
	public String deleteOld(@ModelAttribute("oldNo") String oldNo) throws Exception {
		System.out.println("delete");

		oldService.deleteOld(oldNo);
		return "redirect:/old/listOld";
	}

/////////////////////회원, 어드민 가능//////////////////////////////	
	@PostMapping("updateOldState")
	public String updateOldState(@RequestParam("old") Old old, Model model) throws Exception {
		System.out.println(this.getClass() + "포스트상태");
		oldService.updateOld(old);

		return "old/getOld";
	}

/////////////////////회원, 어드민 가능//////////////////////////////	
	@PostMapping("addOldReport")
	public String addOldReport(@RequestParam("old") Old old) throws Exception {
		System.out.println(this.getClass() + "포스트상태");
		oldService.updateOld(old);

		return "old/getOld";
	}
	
/////////////////////회원, 어드민 가능//////////////////////////////	
@RequestMapping("updateOldDate")
public String updateOldDate(@ModelAttribute("oldNo") String oldNo) throws Exception {
System.out.println("데이트 업데이트");

oldService.updateOldDate(oldNo);
return "redirect:/old/listOld";
}

@RequestMapping("updateOldOnSale")
public String updateOldOnSale(@ModelAttribute("oldNo") String oldNo) throws Exception {
System.out.println("데이트 업데이트");

oldService.updateOldOnSale(oldNo);
return "redirect:/old/listOld";
}


@RequestMapping("updateOldSold")
public String updateOldSold(@ModelAttribute("oldNo") String oldNo) throws Exception {
System.out.println("스테이트 업데이트");

oldService.updateOldSold(oldNo);
return "redirect:/old/listOld";
}
	
}
