package com.mvc.forrest.controller.report;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.service.domain.Report;
import com.mvc.forrest.service.report.ReportService;

@RestController
@RequestMapping("/report/*")
public class ReportRestController {
	
	@Autowired
	public ReportService reportService;
	
	@PostMapping("json/addReport")
	public boolean addReport(@ModelAttribute("report") Report report, Model model) throws Exception {
		 System.out.println("POST : json/addReport");
		 System.out.println(report);
		 
		 if(reportService.isReportFirst(report)==null) {
			 //없으면
			 reportService.addReport(report);
			 return true;
		 }else {
			 //있으면
			 return false;
		 }

	}
	
	
}