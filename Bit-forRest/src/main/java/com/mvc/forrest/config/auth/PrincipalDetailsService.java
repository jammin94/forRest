package com.mvc.forrest.config.auth;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mvc.forrest.dao.user.UserDAO;
import com.mvc.forrest.service.domain.User;

import lombok.RequiredArgsConstructor;



@Service// IoC
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private final UserDAO userDAO;
	
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);
	
	
	//return이 잘 되면 자동으로 세션을 만든다
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		//System.out.println("실행확인"+username);
		
		
		//로그인에 관한 로직 작성
		//return null; //return type 확인 할 것
		
			try {
				User user = userDAO.getUser(userId);
				System.out.println(user);
				return new LoginUser(user);
			} catch (Exception e) {
				return null;
			}

	}


	
}
