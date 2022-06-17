package com.mvc.forrest.controller.report;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.forrest.service.domain.Old;
import com.mvc.forrest.service.domain.Product;
import com.mvc.forrest.service.domain.Rental;
import com.mvc.forrest.service.domain.RentalReview;
import com.mvc.forrest.service.domain.Report;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.firebase.FCMService;
import com.mvc.forrest.service.old.OldService;
import com.mvc.forrest.service.product.ProductService;
import com.mvc.forrest.service.rental.RentalService;
import com.mvc.forrest.service.rentalreview.RentalReviewService;
import com.mvc.forrest.service.report.ReportService;
import com.mvc.forrest.service.user.UserService;




@Controller
@RequestMapping("/report/*")
public class ReportController {

	@Autowired
	public ReportService reportService;
	
	@Autowired
	public OldService oldService;
	

	
	@GetMapping("addReport")
	public String addReport(@RequestParam("oldNo") int oldNo,Model model, HttpSession session, @ModelAttribute("report") Report report ) throws Exception {
		User loginUser =(User)session.getAttribute("user");
		System.out.println(loginUser);
		String id= loginUser.getUserId();
		 return "common/firebase";
	}
	

	
	@PostMapping("addReport")
	public String addReport(@ModelAttribute("report") Report report, @ModelAttribute("user") User user, Model model ) throws Exception {
		 
		 reportService.addReport(report);
		
		 return "report/addReport";
	}
	
	@GetMapping("getReport")
	public String getReport(@RequestParam("reportNo") int reportNo, Model model ) throws Exception {
		System.out.println("getReport 실행됨");
		Report report = reportService.getReport(reportNo);
		
		System.out.println(reportService.getReport(reportNo));
		
		Old old= oldService.getOld(report.getReportOldNo());
		model.addAttribute("old", old);
		return "report/getReport";
	}	
	
	@PostMapping("updateReportCode")
	public String updateReportCode(@ModelAttribute("report") Report report, Model model ) throws Exception {
		
		reportService.updateReportCode(report);
		
		return "report/addReport";
	}
	
	@RequestMapping("listReport")
	public String listReport(Model model) throws Exception {
		
		reportService.getReportList();
		model.addAttribute("reportList", reportService.getReportList());
		return "report/listReport";
	}		
	
	
}