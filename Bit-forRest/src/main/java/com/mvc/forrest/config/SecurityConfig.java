package com.mvc.forrest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity//해당 파일로 시큐리티를 활성화
@Configuration//IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super 삭제 - 기존 시큐리티가 가지고 있는 기능이 없어짐 (Overriding)
		http.csrf().disable();//csrf 토큰을 사용하지 않겠다.
		http.authorizeHttpRequests()  //인가 요청이 오면
			.antMatchers().authenticated()
			.anyRequest().permitAll();
//		.and()
//		.formLogin()
//		.loginPage("/user/login") //GET
//		.loginProcessingUrl("/user/login") //POST
//		.defaultSuccessUrl("/");
//		.and()
//		.logout()
//		.logoutUrl("/로그아웃요청Url")// 로그인과 로그아웃은 csrf 사용시 사용 안할 때는 로그아웃은 get방식도 가능 POST 요청만 허용
//		.logoutSuccessUrl("로그아웃 성공시 redircet url");
		//1. antMatchers 안에 있는 주소에 접속 할 때는 인증이 필요하다
		//2. 그 외 모든 리퀘스트는 다 승인한다
		//3. 그리고
		//4. 인증이 필요한 페이지 요청시 formLogin 할 것이다
		//5. 로그인 요청을 하면 POST 로 온 것만 Intercept
		//6. 로그인 페이지는 /user/login이고, 1의 주소로 인증 없이 접속하면 자동으로 보내준다
		//7. 제대로 로그인 했다면 / 로 redirect.
	}
}
