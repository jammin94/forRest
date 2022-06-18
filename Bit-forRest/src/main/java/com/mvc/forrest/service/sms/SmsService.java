package com.mvc.forrest.service.sms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class SmsService{

	public String makeSignature(String timestamp)throws Exception {
		String space = " ";					
		String newLine = "\n";					
		String method = "GET";					
		String url = "/sms/v2/services/ncp:sms:kr:285705455384:forrest/messages";
		String accessKey = "085PuDipEFvtOTVMVArr";
		String secretKey = "vUJHxXeEopCIB6s0t3OBDv7IynbShlcCklTjWtq3";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
		
		System.out.println("encodeBase64String : "+encodeBase64String);
	  return encodeBase64String;
	}
	
	public void makeMassage(String signature, String timestamp, String phone) throws Exception{
		
		String reqURL = "https://sens.apigw.ntruss.com/sms/v2/"
				+ "services/ncp:sms:kr:285705455384:forrest"
				+ "/messages";	
		
		JsonObject bodyJson = new JsonObject();
		JsonObject toJson = new JsonObject();
		JsonArray toArr = new JsonArray();
		
		bodyJson.addProperty("type", "SMS");
		bodyJson.addProperty("from", "01033294534");
		bodyJson.addProperty("content", "인증번호 : ");
		bodyJson.add("messages", toArr);
		
		toJson.addProperty("subject", "[forREST]");
		toJson.addProperty("to", phone);
		toArr.add(toJson);
		
		String body = bodyJson.toString();
		
		URL url = new URL(reqURL);
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
		conn.setRequestProperty("x-ncp-iam-access-key", "085PuDipEFvtOTVMVArr");
		conn.setRequestProperty("x-ncp-apigw-signature-v2", signature);
		conn.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(body.getBytes());
		wr.flush();
		wr.close();
		
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);
		
////////////////////////////////여기까지 request//////////////////////////////////
		
		BufferedReader br;
		String line = "";
		String result = "";
		
		
		 if(responseCode==202) { // 정상 호출
             br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         } else {  // 에러 발생
             br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
             while ((line = br.readLine()) != null) {
     			result += line;
     		}
     		System.out.println("response body : " + result);

         }

         String inputLine;
         StringBuffer response = new StringBuffer();
         while ((inputLine = br.readLine()) != null) {
             response.append(inputLine);
         }
         br.close();
          

		

	}
	
}
