package com.mvc.forrest.controller.user;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.old.OldService;
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
		
		System.out.println("/user/logon : GET");

		return "user/login";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute("user") User user , HttpSession session, Model model ) throws Exception{
		
		System.out.println("/user/login : POST");
		
		User dbUser=userService.getUser(user.getUserId());
		
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
			session.setAttribute("user", dbUser);		//세션에 user 저장
			
			//신규회원 쿠폰발급
			if(user.getJoinDate()==user.getRecentDate()) {
				OwnCoupon oc = new OwnCoupon();
				Coupon coupon = couponService.getCoupon(2);
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				Timestamp ts2 = null;
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(ts);
				cal.add(Calendar.DATE,30);
				ts2.setTime(cal.getTime().getTime());
				oc.setOwnuser(dbUser);
				oc.setOwncoupon(coupon);
				oc.setOwnCouponCreDate(ts);
				oc.setOwnCouponDelDate(ts2);
				
				couponService.addOwnCoupon(oc);
			}
			
			userService.updateRecentDate(dbUser);		//최근접속일자 update
				
			return "index";								//나중에 정확한 경로로 수정
			
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
	public String findId (String userName, String phone, String sms) throws Exception{
		System.out.println("/user/findId : POST");
		
		// sms 인증필요 보낸 sms와 유저sms가 일치해야함
		
		User userByPhone = userService.getUserByPhone(phone);
		User userByName = userService.getUserByName(userName);
		if(userByName.getUserId() == userByPhone.getUserId()){
			return "user/findId";
		}
		 
		return "user/findId";
	}
	
	@GetMapping("findPwd")
	public String findPwd() throws Exception{
		
		System.out.println("/user/findPwd : GET");
		
		return "user/findPwd";
	}
	
	@PostMapping("findPwd")
	public String findPwd(String userId, String phone, String sms, HttpSession session) throws Exception{
		
		System.out.println("/user/findPwd : POST");
		
		// sms인증 필요
		
		User user = userService.getUser(userId);
		User userByPhone = userService.getUserByPhone(phone);
		
		if(user.getUserId() == userByPhone.getUserId()){
			session.setAttribute("user", user);
			
			return "user/pwdReset";
		}
		
		return "user/pwdReset";
	}	
	
//	@GetMapping("pwdReset")								//필요없음
//	public String pwdReset() throws Exception{
//		
//		System.out.println("/user/pwdReset : GET");
//		
//		return null;
//	}
	
	@PostMapping("pwdReset")
	public String pwdReset(@ModelAttribute User user, HttpSession session, Model model) throws Exception{
		
		System.out.println("/user/pwdReset : POST");
		
		User dbUser = userService.getUser(user.getUserId());
		dbUser.setPassword(user.getPassword());
		userService.updatePassword(dbUser);
		session.setAttribute("user", dbUser);
		
		model.addAttribute("user", dbUser);
		
		
		return "main/index";
	}
	
	@RequestMapping("listUser")
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "user/getUserList";
	}
	
	@GetMapping("getUser")
	public String getUser( @RequestParam("userId") String userId , Model model ) throws Exception {
		
		System.out.println("/user/getUser : GET");
		
		User user = userService.getUser(userId);

		model.addAttribute("user", user);
		
		return "user/getUser";
	}
	
	@GetMapping("getMyPage")
	public String getMyPage( @RequestParam("userId") String userId , Model model ) throws Exception {
		
		System.out.println("/user/getMyPage : GET");
		
		User user = userService.getUser(userId);

		model.addAttribute("user", user);
		
		return "user/getMyPage";
	}
	
	@GetMapping("deleteUser")
	public String deleteUser()throws Exception {
		
		System.out.println("/user/deleteUser : GET");
		
		return "user/deleteUserView";
	}
	
	@PostMapping("deleteUser")
	public String deleteUser(String userId)throws Exception {
		
		System.out.println("/user/deleteUser : POST");

		// #############	need logic // 인자수정		################

		return null;
	}
}