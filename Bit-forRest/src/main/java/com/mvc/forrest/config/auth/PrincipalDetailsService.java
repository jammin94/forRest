package com.mvc.forrest.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;


@Service// IoC
public class PrincipalDetailsService implements UserDetailsService {

	
	//return이 잘 되면 자동으로 세션을 만든다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println("실행확인"+username);
		
		
		//로그인에 관한 로직 작성
		//return null; //return type 확인 할 것
		return new LoginUser();
	}

}
