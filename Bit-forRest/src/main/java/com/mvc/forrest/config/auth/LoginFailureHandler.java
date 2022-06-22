package com.mvc.forrest.config.auth;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.thymeleaf.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = "";

        if(exception instanceof UsernameNotFoundException){
            errorMsg = "1"; //아이디가 존재하지 않음
        }else if(exception instanceof BadCredentialsException){
            errorMsg = "2"; // 아이디 또는 비밀번호가 잘못 입력됨
        }else if(exception instanceof LockedException){
            errorMsg = "3"; // 신고누적으로 계정이 정지상태임
        }else if(exception instanceof DisabledException) {
        	errorMsg = "7"; // 회원탈퇴로 계정이 비활성화상태
        }

        if(!StringUtils.isEmpty(errorMsg)){
            request.setAttribute("errorMsg", errorMsg);
        }

        response.sendRedirect("/user/login?errorMsg=" + URLEncoder.encode(errorMsg, "UTF-8"));	// redirect 처리
//        request.getRequestDispatcher("/user/login").forward(request, response);				// forward 처리
    }
}
