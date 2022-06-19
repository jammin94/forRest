package com.mvc.forrest.dao.report;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Report;



@Mapper
public interface ReportDAO {
	
	
	void addReport(Report report) throws Exception;
	
	Report getReport(int reportNo) throws Exception;
	
	List<Report> getReportList() throws Exception;
	
	void updateReportCode(Report report) throws Exception;
	
	int getReportedNo(String userId) throws Exception;
}
