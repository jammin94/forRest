package com.mvc.forrest.controller.payment;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	
	private IamportClient client;
	
	
	public PaymentController(@Value("${iamportApi.api_key}") String api_key, @Value("${iamportApi.api_secret}") String api_secret){
		this.client = new IamportClient(api_key, api_secret);
	}


	@GetMapping("/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException{	
		
		return client.paymentByImpUid(imp_uid);
		
		
}
	
	@PostMapping("/cancel")
	public IamportResponse<Payment> cancelPaymentByImpUid(@RequestBody String imp_uid) throws IamportResponseException, IOException {
		
		log.info("imp_uid={}", imp_uid);
		
		return client.cancelPaymentByImpUid(new CancelData(imp_uid, true));
	}
	
	
	

}