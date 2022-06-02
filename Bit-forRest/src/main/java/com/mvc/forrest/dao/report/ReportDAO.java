package com.mvc.forrest.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mvc.forrest.service.domain.Report;
import com.mvc.forrest.service.domain.Search;
import com.mvc.forrest.service.domain.Storage;


@Mapper
public interface ReportDAO {
	
	
	void addReport(Report report) throws Exception;
	
	Report getReport(int reportNo) throws Exception;
	
	List<Report> getReportList() throws Exception;
	
	void updateReportCode(Report report) throws Exception;
	
	int getReportedNo(String userId) throws Exception;
}
