package com.mvc.forrest.config.auth;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mvc.forrest.service.domain.User;

import lombok.Data;

@Data
public class LoginUser  implements UserDetails {

	/**
	 로그인 했을 때 정보들
	 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
	 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private String userId;
	private String password;
	private String nickname;
	private String userName;
	private String userAddr;
	private String phone;
	private String role;
	private String joinPath;
	private String userImg;
	private String pushToken;
	private String reportedCount;
	private String reviewedCount;
	private double userRate;
	private int profit;
	private int couponCount;
	private Timestamp leaveApplyDate;
	private Timestamp leaveDate;
	private Timestamp recentDate;
	private Timestamp joinDate;
	
	
	public LoginUser(User user) {
		System.out.println(user);
		this.user=user;
	}
	

	
	
	//getter setter 만들어 줘야 해
	
	public LoginUser() {
	}

	//권한이 한개 이상일 경우가 있기 때문에 Collection으로 리턴함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(new GrantedAuthority() {
			//람다식 공부 해 볼거면 말해줘
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		//id
		return user.getUserId();
	}
	

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 판단 로직 만료면 false 리턴
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김 판단 로직 잠김이면 false 리턴
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호가 만료 검사 만료 됐으면 false
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 검사 휴면 유저면 false
		return true;
	}


	
		
}
