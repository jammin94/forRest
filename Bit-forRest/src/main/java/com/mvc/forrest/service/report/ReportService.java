package com.mvc.forrest.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.report.ReportDAO;

import com.mvc.forrest.service.domain.Report;

@Service
public class ReportService {
	
	@Autowired
	private ReportDAO reportDAO;
	
	
	public void addReport(Report report) throws Exception{
		System.out.println("addReport 실행 됨");
		reportDAO.addReport(report);
	}
	
	public Report getReport(int reportNo) throws Exception{
		System.out.println("getReport 실행 됨");
		return reportDAO.getReport(reportNo);
	}

	public List<Report> getReportList() throws Exception{
		System.out.println("getReportList 실행 됨");
		return reportDAO.getReportList();
	}	
	
	public void updateReportCode(Report report) throws Exception{
		System.out.println("updateReportCode 실행 됨");
		reportDAO.updateReportCode(report);
	}		
	
	public int getReportedNo(String userId) throws Exception{
		System.out.println("getReportedNo 실행 됨");
		return reportDAO.getReportedNo(userId);
	}			

}
