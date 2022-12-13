package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	// 로그인
	@GetMapping("/user/login/form")
	public String loginForm(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getHeader("referer"));
		return "user/login";
	}
	@PostMapping("/user/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		userService.login(request, response);
	}
	

	// 회원가입
	@GetMapping("/user/join/agree")
	public String agreeForm() {
		return "user/agree";
	}
	@GetMapping("/user/join/write")
	public String joinWrite(@RequestParam(required=false) String location
			              , @RequestParam(required = false) String promotion
			              , Model model) {
		model.addAttribute("location", location);
		model.addAttribute("promotion", promotion);
		return "user/join";
	}
	// 회원가입 - 이메일 중복체크
	@ResponseBody
	@GetMapping(value="/user/checkReduceEmail", produces="application/json")
	public Map<String, Object> checkReduceEmail(String email){
		return userService.isReduceEmail(email);
	}
	// 회원가입 - 이메일 인증코드
	@ResponseBody
	@GetMapping(value="/user/sendAuthCode", produces="application/json")
	public Map<String, Object> sendAuthCode(String email){
		return userService.sendAuthCode(email);
	}
	// 회원가입
	@PostMapping("/user/join")
	public void join(HttpServletRequest request, HttpServletResponse response) {
		userService.join(request, response);
	}
	
	// 마이페이지
	@GetMapping("/user/mypage")
	public String requiredLogin_mypage() {
		return "user/mypage";
	}
	// 마이페이지 - 개인정보 관리
	@GetMapping("/user/mypage/info")
	public String myinfo() {
		return "user/myinfo";
	}
	
	// 로그아웃
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		userService.logout(request, response);
		return "redirect:/";
	}
	
	// 회원탈퇴
}
