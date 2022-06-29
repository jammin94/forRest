package com.mvc.forrest.controller.coupon;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.common.utils.FileNameUtils;
import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.coupon.CouponService;
import com.mvc.forrest.service.domain.Coupon;
import com.mvc.forrest.service.domain.OwnCoupon;
import com.mvc.forrest.service.domain.Page;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

@Controller
@RequestMapping("/coupon/*")
public class CouponController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private UserService userService;

	public FileNameUtils fileNameUtils ;
	
	@Value("5")
	int pageUnit;
	@Value("10")
	int pageSize;
	
	public CouponController() {
	}
	
	@RequestMapping("manageCoupon")
	public String manageCoupon(@ModelAttribute("search") Search search , Model model,
								HttpSession session) throws Exception{
	
		System.out.println("/coupon/manageCoupon : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=couponService.getCouponList(search);
			
			System.out.println("# map : "+map);
			
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
				
		return "/coupon/manageCoupon";
	}
		
	@PostMapping("addCoupon")
	public String addCoupon(@ModelAttribute("coupon")Coupon coupon ) throws Exception {
	
		System.out.println("/coupon/addCoupon : POST");
		
		coupon.setCouponNo(fileNameUtils.getRandomString());
		
		couponService.addCoupon(coupon);
		
		return "redirect:/coupon/manageCoupon";
	}
	
	@PostMapping("updateCoupon")
	public String updateCoupon(@ModelAttribute("coupon")Coupon coupon) throws Exception {
		
		System.out.println("/coupon/updateCoupon : POST");
		
		couponService.updateCoupon(coupon);
		
		return "redirect:/coupon/manageCoupon";
	}
	
	@RequestMapping("ownCouponList")
	public String ownCouponList( Model model, HttpSession session, Search search) throws Exception{
		
		System.out.println("/coupon/ownCouponList : GET");
		
		LoginUser sessionUser= 
				(LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String , Object> map=couponService.getOwnCouponList(sessionUser.getUser().getUserId());
		
		model.addAttribute("map", map.get("list"));
		
		System.out.println(map.get("list"));
		
		return "/coupon/ownCouponList";
	}
	
	@PostMapping("deleteCoupon")
	public String deleteCoupon(@RequestParam String couponNo) throws Exception{
		
		System.out.println("/coupon/deleteCoupon : POST");
		
		System.out.println("요청받은 쿠폰번호 : "+couponNo);
		
		couponService.deleteCoupon(couponNo);

		return "redirect:/coupon/manageCoupon";
	}
		
	@GetMapping("addOwnCoupon")
	public String addOwnCoupon(@RequestParam String couponNo, Model model,
								@RequestParam String boardNo) throws Exception{
		
		System.out.println("coupon/addOwnCoupon : GET");
		
		LoginUser sessionUser= (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUser(sessionUser.getUser().getUserId());
		Coupon coupon = couponService.getCoupon(couponNo);
		OwnCoupon oc = new OwnCoupon();
		
		Calendar cal = Calendar.getInstance();
		Timestamp t1 = new Timestamp(System.currentTimeMillis());
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		
		cal.setTime(t1);
		cal.add(Calendar.DATE, 30);
		t2.setTime(cal.getTime().getTime());
		
		oc.setOwnCoupon(coupon);
		oc.setOwnUser(user);
		oc.setOwnCouponCreDate(t1);
		oc.setOwnCouponDelDate(t2);
		
		List<OwnCoupon> ocList =  couponService.checkOwnCoupon(oc);
		
		if(ocList.size()>0) {
//			쿠폰이 있는경우
			model.addAttribute("checkOC", "1");
	        return "redirect:/board/getAnnounce?boardNo="+boardNo;
		}else {
//			쿠폰이 없는경우
			couponService.addOwnCoupon(oc);	
			model.addAttribute("checkOC", "0");
		

		///////////////////////////////////////////////////////////////////////////////
		
		user = userService.getUser(user.getUserId());
		
		SecurityContextHolder.clearContext();
		
		LoginUser loginUser = new LoginUser(user);
		System.out.println("  #updateUserDetails : "+loginUser);
		
		Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
				loginUser, null, loginUser.getAuthorities());
		System.out.println("  #newAuthentication : "+newAuthentication);
		
		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		
		System.out.println("   리로드 끝");
		////////////////////////////////////////////////////////////////////////////////
		
        return "redirect:/board/getAnnounce?boardNo="+boardNo;
		}
	}
	
}
