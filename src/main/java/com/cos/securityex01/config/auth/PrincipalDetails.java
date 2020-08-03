package com.cos.securityex01.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.securityex01.model.User;

import lombok.Data;

//만드는 목적 : UserDetails를 return하기 위한 목적
//세션에 User가 아니라 이 클래스를 담을 예정 

//Authentication 객체에 저장할 수 있는 유일한 타입
//Authentication가 세션에 담기고
//Secuirty-Context-Holder안에 SecurityContext가 키값
@Data
public class PrincipalDetails implements UserDetails{

	//composition 하면 끝
	private User user;
	
	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		//만료되었는지 물어봄 최종접속시간을 보고 true, false로 나눔
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//계정 Lock날렸는지 물어봄 비밀번호 x번 틀렸을때 false
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//비밀번호 오래 변경안해서 만료되었는지
		return true;
	}

	@Override
	public boolean isEnabled() {
		//계정 활성화 되었는지
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//권한
		return null;
	}
}
