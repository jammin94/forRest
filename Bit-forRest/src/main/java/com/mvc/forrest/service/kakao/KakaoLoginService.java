package com.mvc.forrest.service.kakao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class KakaoLoginService {
		
	public KakaoLoginService() {
		System.out.println(this.getClass());
	}
	
	public String getAccessToken(String authorization_code) throws Exception{
		
		System.out.println("### getAccessToken Start ###");

		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		URL url = new URL(reqURL);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=authorization_code");
		
		sb.append("&client_id=ab70541f4fbb493e0fd22d7f73cd2940");
		sb.append("&redirect_uri=http://localhost:8080/snsLogin/kakaoLogin");
        
		sb.append("&code=" + authorization_code);
		bw.write(sb.toString());
		bw.flush();
		
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);
		///////////////////////////////여기까지 request///////////////////////////////////////
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
		String result = "";
		
		while ((line = br.readLine()) != null) {
			result += line;
		}
		System.out.println("response body : " + result);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);
		access_Token = element.getAsJsonObject().get("access_token").getAsString();
        refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
        
	return access_Token;
}

public Map<String, Object> getUserInfo(String access_Token) throws Exception{
	System.out.println("### getUserInfo Start ###");
	Map<String, Object> userInfo = new HashMap<String, Object>();
	String reqURL = "https://kapi.kakao.com/v2/user/me";
	
	URL url = new URL(reqURL);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("GET");

	// 요청에 필요한 Header에 포함될 내용
	conn.setRequestProperty("Authorization", "Bearer " + access_Token);

	int responseCode = conn.getResponseCode();
	System.out.println("responseCode : " + responseCode);

	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

	String line = "";
	String result = "";

	while ((line = br.readLine()) != null) {
		result += line;
	}
	System.out.println("response body : " + result);

	JsonParser parser = new JsonParser();
	JsonElement element = parser.parse(result);
 		System.out.println("element : "+element);
	
	String kakaoEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").toString().replace("\"", "");
	String kakaoImg = element.getAsJsonObject().get("properties").getAsJsonObject().get("thumbnail_image").toString().replace("\"", "");
	
	userInfo.put("kakaoEmail", kakaoEmail);
	userInfo.put("kakaoImg", kakaoImg);
	
	System.out.println("### getUserInfo End ###");

	return userInfo;
}
	
	
	
}
