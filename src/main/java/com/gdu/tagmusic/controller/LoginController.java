package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	// 로그인
	@GetMapping("/login/form")
	public String loginForm(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getHeader("referer"));
		model.addAttribute("apiURL", userService.getNaverLoginApiURL(request));	 // 네이버 로그인
		return "login/login";
	}
	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		userService.login(request, response);
	}
	
	// 로그인 - 네이버 간편로그인
	@GetMapping("/user/naver/login")
	public String naverLogin(HttpServletRequest request, Model model) {
		
		String access_token = userService.getNaverLoginToken(request);
		UserDTO profile = userService.getNaverLoginProfile(access_token);  // 네이버로그인에서 받아온 프로필 정보
		UserDTO naverUser = userService.getNaverUserById(profile.getEmail()); // 이미 네이버로그인으로 가입한 회원이라면 DB에 정보가 있음
		
		// 네이버로그인으로 가입하려는 회원 : 간편가입페이지로 이동
		if(naverUser == null) {
			model.addAttribute("profile", profile);
			return "user/naver_join";
		}
		// 네이버로그인으로 이미 가입한 회원 : 로그인 처리
		else {
			userService.naverLogin(request, naverUser);
			return "redirect:/";
		}
		
	}
	@PostMapping("/user/naver/join")
	public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
		userService.naverJoin(request, response);
	}
	
	// 로그인 - 정보찾기
	@GetMapping("user/loginHelp")
	public String loginHelp() {
		return "user/loginHelp";
	}
	// 로그인 - 아이디 찾기
	@ResponseBody
	@PostMapping(value="/user/findEmail", produces="application/json")  // 아이디 찾기
	public Map<String, Object> findEmail(@RequestBody Map<String, Object> map) {
		return userService.findUser(map);
	}
	// 로그인 - 비밀번호 찾기
	@ResponseBody
	@PostMapping(value="/user/findPw", produces="application/json")  // 비밀번호 찾기
	public Map<String, Object> findPw(@RequestBody Map<String, Object> map) {
		return userService.findUser(map);
	}
	// 로그인 - 임시 비밀번호 발송
	@ResponseBody
	@PostMapping(value="/user/sendTemporaryPassword", produces="application/json")  // 이메일로 임시비번 전송
	public  Map<String, Object> memberSendEmailTemporaryPassword(UserDTO user) {
		return userService.sendTemporaryPassword(user);
	}
}
