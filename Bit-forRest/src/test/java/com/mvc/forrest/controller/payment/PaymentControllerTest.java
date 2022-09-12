package com.mvc.forrest.controller.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentControllerTest {
	
	@Value("${iamportApi.api_key}")
	private String api_key;
	
	@Value("${iamportApi.api_secret}")
	private String api_secret;
	
	
		@Test
		void apikey값테스트() {
			
			String real_api_key = "8740787114435303";
			String real_api_secret = "7f0c791b9cc0f3739e67cf5e3116f1d769ae64b9372cd0d16248fa803279c5cdbf74600ab0166d1b";
			
			Assertions.assertThat(api_key).isEqualTo(real_api_key);
			Assertions.assertThat(api_secret).isEqualTo(real_api_secret);
		}
}
