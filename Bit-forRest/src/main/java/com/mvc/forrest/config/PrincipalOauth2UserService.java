package com.mvc.forrest.config;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.config.auth.userInfo.GoogleUserInfo;
import com.mvc.forrest.config.auth.userInfo.KakaoUserInfo;
import com.mvc.forrest.config.auth.userInfo.NaverUserInfo;
import com.mvc.forrest.config.auth.userInfo.OAuth2UserInfo;
import com.mvc.forrest.service.domain.Role;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired 
    private UserService userService;
    @Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	System.out.println("시큐리티 테스트 1단계");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();    
        
        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("kakao")){	//추가
        	System.out.println(oAuth2User.getAttributes());
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        System.out.println("시큐리티 테스트 2단계");

        String providerId = oAuth2UserInfo.getProviderId();	
        String username = provider+"_"+providerId;  			// 사용자가 입력한 적은 없지만 만들어준다

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다

        String email = oAuth2User.getAttribute("email");
        Role role = Role.user;
        
        User byUsername = null;
		try {
			byUsername = userService.getUser(email);
	        if(byUsername == null){
	            byUsername = User.oauth2Register()
	                    .username(username).password(password).email(email).role(role)
	                    .provider(provider).providerId(providerId)
	                    .build();
	            userService.addUser(byUsername);
	            System.out.println("시큐리티 테스트 3단계");
	        }
		} catch (Exception e) {

		}
		httpSession.setAttribute("login_info", oAuth2UserInfo);
        //DB에 없는 사용자라면 회원가입처리
		System.out.println("시큐리티 테스트 4단계");
		System.out.println(byUsername);
		System.out.println(oAuth2User.getAttributes());
		byUsername.setUserId(byUsername.getEmail());
		System.out.println(byUsername);
        return new LoginUser(byUsername, oAuth2User.getAttributes());
    }
}