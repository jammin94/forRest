package com.mvc.forrest.controller.user;

import java.sql.Timestamp;
import java.util.Calendar;
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

import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OldReview;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.oldreview.OldReviewService;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.user.UserService;


@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private OldService oldService;
	@Autowired
	private OldReviewService oldReviewService;
	@Autowired
	private RentalService rentalService;
	@Autowired
//	private OldReviewService oldReivewService;
	
	@Value("5")
	int pageUnit;
	@Value("10")
	int pageSize;
	
	public UserController(){
	}
	
	@GetMapping("login")
	public String login() throws Exception{
		
		System.out.println("/user/login : GET");

		return "user/login";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute("user") User user , HttpSession session, Model model ) throws Exception{
		
		System.out.println("/user/login : POST");
		
		User dbUser=userService.getUser(user.getUserId());
		
		System.out.println("입력받은 ID/PW : "+user);
		System.out.println("DB와 일치하는 ID/PW : "+dbUser);
		
		//db에 아이디가 없을 경우
		if(dbUser==null) {
			model.addAttribute("message", "가입되지않은 아이디입니다.");
			return "user/login";
		}
		
		//db에 아이디가 있지만 회원탈퇴
		if(dbUser.getRole()=="leave") {
			model.addAttribute("message", "탈퇴처리된 회원입니다..");
			return "user/login";	
		}
		
		//db에 아이디가 있지만 로그인제한된 유저
		if(dbUser.getRole()=="restrict") {
			model.addAttribute("message", "이용제한된 회원입니다..");
			return "user/login";	
		}
		
		//해당 id와 pwd가 일치할 경우
		if( user.getPassword().equals(dbUser.getPassword())){
			
			//세션에 user 저장
			session.setAttribute("user", dbUser);
//			model.addAttribute("user", dbUser);	
			
			//신규회원 쿠폰발급
			if(user.getJoinDate()==user.getRecentDate()) {
				OwnCoupon oc = new OwnCoupon();
				Coupon coupon = couponService.getCoupon(2);
				Timestamp ts1 = new Timestamp(System.currentTimeMillis());
				Timestamp ts2 = new Timestamp(System.currentTimeMillis());
				
//				Calendar cal1 = Calendar.getInstance();
				Calendar cal2= Calendar.getInstance();
//				cal1.setTime(ts1);
				cal2.setTime(ts2);
				cal2.add(Calendar.DATE,30);
//				ts2.setTime(cal.getTime().getTime());
				oc.setOwnUser(dbUser);
				oc.setOwnCoupon(coupon);
				oc.setOwnCouponCreDate(ts1);
				oc.setOwnCouponDelDate(ts2);
				System.out.println(oc);
				couponService.addOwnCoupon(oc);
			}
			
			userService.updateRecentDate(dbUser);		//최근접속일자 update
				
			return "main/index";
			
		//해당 id와 pwd가 불일치할 경우	
		}else{
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "user/login";
		}
		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session ) throws Exception{
		
		System.out.println("/user/logout : GET");
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@GetMapping("addUser")
	public String addUser() throws Exception{
		
		System.out.println("/user/addUser : GET");
		
		return "user/addUserView";
	}
	
	@RequestMapping("addUser")
	public String addUser( @ModelAttribute("user") User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		
		userService.addUser(user);
				
		return "user/login";
	}
	
	@GetMapping("findId")
	public String findId () throws Exception{

		System.out.println("/user/findId : GET");
		
		return "user/findIdView";
	}
	
	@PostMapping("findId")
	public String findId (@ModelAttribute("user") User user, String sms,
							Model model) throws Exception{
		System.out.println("/user/findId : POST");
		
		// sms 인증필요 보낸 sms와 유저sms가 일치해야함
		User userByPhone = userService.getUserByPhone(user.getPhone());
		User userByName = userService.getUserByName(user.getUserName());
		if(userByName.getUserId().equals(userByPhone.getUserId())){
			user = userByName;
			userByName.getJoinDate().toString().substring(pageUnit, pageSize);
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userJoinDate", user.getJoinDate().toString().substring(0, 10));
			
			return "user/findId";
		}
		 
		return "user/findIdView";
	}
	
	@GetMapping("findPwd")
	public String findPwd() throws Exception{
		
		System.out.println("/user/findPwd : GET");
		
		return "user/findPwd";
	}
	
	@PostMapping("findPwd")
	public String findPwd(@ModelAttribute("user") User user, String sms, 
							HttpSession session, Model model) throws Exception{
		
		System.out.println("/user/findPwd : POST");
		
		// sms인증 필요
		
		User userById = userService.getUser(user.getUserId());
		User userByPhone = userService.getUserByPhone(user.getPhone());
		
		if(userById.getUserId().equals(userByPhone.getUserId())){
			session.setAttribute("user", userById);
			model.addAttribute("user", userById);
			return "user/pwdReset";
		}
		
		return "user/findPwd";
	}	
	
	@GetMapping("pwdReset")						
	public String pwdReset() throws Exception{
		
		System.out.println("/user/pwdReset : GET");
		
		return "user/pwdReset";
	}
	
	@PostMapping("pwdReset")
	public String pwdReset(@RequestParam("password") String password, HttpSession session) throws Exception{
		
		System.out.println("/user/pwdReset : POST");
		
		User sessionUser = (User)session.getAttribute("user");
		sessionUser.setPassword(password);
		userService.updatePassword(sessionUser);
		
		return "main/index";
	}
	
	@RequestMapping("getUserList")
	public String getUserList( @ModelAttribute("search") Search search , Model model ) throws Exception{
		
		System.out.println("/user/getUserList : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=userService.getUserList(search);
		
		System.out.println("# map : "+map);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "user/getUserList";
	}
	
	@RequestMapping("getUser")
	public String getUser( @RequestParam("userId") String userId , Model model,
							HttpSession session, Search search) throws Exception {
		
		System.out.println("/user/getUser : GET");
		
		User dbUser = userService.getUser(userId);
		User sessionUser = (User)session.getAttribute("user");

//		세션에 유저정보가 없을경우 로그인 nav
		if(sessionUser==null) {
			return "user/login";
		}
		
//		세션유저와 조회하고자하는 유저가 동일할 경우 myPage
		if(sessionUser.getUserId().equals(dbUser.getUserId())) {
			int profit = 
					rentalService.getTotalRentalProfit(sessionUser.getUserId());
			
			Map<String , Object> map=couponService.getOwnCouponList(userId);
			
			model.addAttribute("map", map.get("list"));
			model.addAttribute("user", sessionUser);
			model.addAttribute("profit", profit);
			return "user/getMyPage";
		}
		
		List<OldReview> list = oldReviewService.getOldReviewList(userId);
		Map<String, Object> oldList = oldService.getOldList(search);

		//해당 유저에 대한 리뷰를 등록한 사람의 수
		if(list.size()==2){
			model.addAttribute("review1", list.get(0));
			model.addAttribute("oldTitle1", oldService.getOld(list.get(0).getOldNo()).getOldTitle());
			model.addAttribute("nickname1", userService.getUser(list.get(0).getReviewUserId()).getNickname());
			model.addAttribute("review2", list.get(1));
			model.addAttribute("oldTitle2", oldService.getOld(list.get(1).getOldNo()).getOldTitle());
			model.addAttribute("nickname2", userService.getUser(list.get(1).getReviewedUserId()).getNickname());
		}else if(list.size()==1) {
			model.addAttribute("review1", list.get(0));
			model.addAttribute("oldTitle1", oldService.getOld(list.get(0).getOldNo()).getOldTitle());
			model.addAttribute("nickname1", userService.getUser(list.get(0).getReviewUserId()).getNickname());
		}
		
		model.addAttribute("list",list);
		model.addAttribute("user", dbUser);
		model.addAttribute("oldList", oldList.get("list"));

		return "user/getUser";
	}
	
//	@GetMapping("getMyPage")				getUser 통합
//	public String getMyPage( @RequestParam("userId") String userId , Model model ) throws Exception {
//		
//		System.out.println("/user/getMyPage : GET");
//		
//		User user = userService.getUser(userId);
//
//		model.addAttribute("user", user);
//		
//		return "user/getMyPage";
//	}
	
	@GetMapping("deleteUser")
	public String deleteUser()throws Exception {
		
		System.out.println("/user/deleteUser : GET");
		
		return "user/deleteUserView";
	}
	
	@PostMapping("deleteUser")
	public String deleteUser(@RequestParam("password") String password, HttpSession session)throws Exception {
		
		System.out.println("/user/deleteUser : POST");

		User user = (User)session.getAttribute("user");
		
		System.out.println(user);
		if(user.getPassword().equals(password)) {
//			userService.leaverUser(user);
			System.out.println("if문안으로 진입");
		}
		

		return "main/index";
	}
}