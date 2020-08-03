package com.cos.securityex01.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"/",""})
	public @ResponseBody String index() {
		return "인덱스 페이지 입니다";
	}
	
	@PostAuthorize("hasRole('ROLE_MANAGER')") // 끝나고 동작
	//@PreAuthorize("hasRole('ROLE_MANAGER')")  컨트롤러 진입 직전 동작
	@Secured("ROLE_MANAGER") // 롤 확인
	@GetMapping({"/user"})
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println(principal);
		System.out.println(principal.getUser().getRole());
		System.out.println(principal.getAuthorities());
		return "유저 페이지 입니다";
	}
	@GetMapping({"/admin"})
	public @ResponseBody String admin() {
		return "어드민 페이지 입니다";
	}
	@GetMapping({"/login"})
	public String login() {
		return "login";
	}
	@GetMapping({"/join"})
	public String join() {
		return "join";
	}
	
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행"+user.toString());
		
		//
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		//
		
		userRepository.save(user);
		return "redirect:/";
	}
}
