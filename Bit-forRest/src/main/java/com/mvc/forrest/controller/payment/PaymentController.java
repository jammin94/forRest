package com.mvc.forrest.controller.payment;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public IamportResponse<Payment> paymentByImpUid(@RequestParam("imp_uid") String imp_uid) throws IamportResponseException, IOException{	
		System.out.println("갓잇");
		return api.paymentByImpUid(imp_uid);
		
		
}
	
	@RequestMapping("json/cancleIamport")
	public IamportResponse<Payment> cancelPaymentByImpUid(@RequestParam("imp_uid") String imp_uid) throws IamportResponseException, IOException {
		
		System.out.println("cancleIamport Start");
		System.out.println("imp_uid:"+ imp_uid);
		
		
		CancelData cancelData = new CancelData(imp_uid, true);

		return api.cancelPaymentByImpUid(cancelData);
	}
	
	
	

}