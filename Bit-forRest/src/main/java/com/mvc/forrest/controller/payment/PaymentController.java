package com.mvc.forrest.controller.payment;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.forrest.service.storage.StorageService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@RestController
@RequestMapping("/payment/*")
public class PaymentController {
	
	
	private IamportClient api;
	
	public PaymentController(){
		this.api = new IamportClient("8740787114435303","7f0c791b9cc0f3739e67cf5e3116f1d769ae64b9372cd0d16248fa803279c5cdbf74600ab0166d1b");
	}


	
	//보관 메인화면 단순 네비게이션
	//비회원도 접근가능
	@RequestMapping("json/verifyIamport")
	public IamportResponse<Payment> paymentByImpUid(Model model, Locale locale, HttpSession session, @RequestParam("imp_uid") String imp_uid) throws IamportResponseException, IOException{	
		System.out.println("갓잇");
		return api.paymentByImpUid(imp_uid);
		
		
}
	
//	public IamportResponse<Payment> cancelPaymentByImpUid(CancelData cancelData) throws IamportResponseException, IOException {
//		
//
//		return api.cancelPaymentByImpUid(cancelData);
//	}
	
	
	

}