package com.mvc.forrest.service.kakao;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mvc.forrest.config.auth.LoginUser;
import com.mvc.forrest.service.domain.User;
import com.mvc.forrest.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {
//DefaultOAuth2UserService는 OAuth2로그인 시 로그인한 유저가 DB에 저장되어있는지를 찾는다.
	
    @Autowired 
    private UserService userService;
    @Autowired 
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();    
        
//        if(provider.equals("google")){
//            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//        }
//        else if(provider.equals("naver")){
//            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
//        }
        if(provider.equals("kakao")){	//추가
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
        
        String providerId = oAuth2UserInfo.getProviderId();	
        String username = providerId;  			

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid); 

        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_USER;

        User byUsername = new User();
        
		try {
			byUsername = userService.getUser(username);
	        if(byUsername == null){
	            byUsername.setUserName(username);
	            byUsername.setPassword(password);
	            byUsername.setRole("user");
	            byUsername.setJoinPath("Kakao");

	            userService.addUser(byUsername);
	            
	            return new LoginUser(byUsername, oAuth2UserInfo);
	        }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
        //DB에 없는 사용자라면 회원가입처리


        return new LoginUser(byUsername, oAuth2UserInfo);	
    }
}