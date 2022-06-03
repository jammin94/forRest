package com.mvc.forrest;




import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mvc.forrest.service.domain.Report;
import com.mvc.forrest.service.report.ReportService;



//@RunWith(SpringJUnit4ClassRunner.class)


@SpringBootTest 
public class ReportServiceTest {

	@Autowired
	private ReportService reportService;
	
	@Test
	public void testGetReport() throws Exception {
		
		reportService.getReport(1);
		Report report = reportService.getReport(1);
		
		assertEquals("admin",report.getReportUser());
	}
	
}