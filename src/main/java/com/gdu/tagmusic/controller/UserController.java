package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;


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

	
	// 마이페이지 - 프로필 사진 불러오기 
	@ResponseBody
	@GetMapping("/user/info/getImage")
	public ResponseEntity<byte[]> getImage(@RequestParam String email){
		System.out.println("이미지 불러오기");
		return userService.getImage(email);
	}
	
	// 마이페이지 - 개인정보 관리
	@GetMapping("/user/mypage/info")
	public String requiredLogin_myinfo() {
		return "user/myinfo";
	}
	
	// 마이페이지 - 프로필 사진 수정
	@ResponseBody
	@PostMapping("/user/info/modifyImage")
	public void modifyImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		userService.modifyImage(multipartRequest, response);
	}

	// 마이페이지 - 개인정보 수정
	@ResponseBody
	@PostMapping("/user/info/modifyArtist")
	public void modifyArtist(HttpServletRequest request, HttpServletResponse response) {
		userService.modifyArtist(request, response);
	}
	// 마이페이지 - 실명 수정
	@PostMapping("/user/info/modifyName")
	public void modifyName(HttpServletRequest request, HttpServletResponse response) {
		userService.modifyName(request, response);
	}
	// 마이페이지 - 비밀번호 확인
	@ResponseBody
	@PostMapping("/user/info/checkPw")
	public Map<String, Object> checkPw(HttpServletRequest request, HttpServletResponse response) {
		return userService.checkPw(request, response);
	}
	// 마이페이지 - 비밀번호 수정
	@ResponseBody
	@PostMapping("/user/info/modifyPw")
	public void modifyPw(HttpServletRequest request, HttpServletResponse response) {
		userService.modifyPw(request, response);
	}
	// 마이페이지 - 휴대폰 수정
	@PostMapping("/user/info/modifyMobile")
	public void modifyMobile(HttpServletRequest request, HttpServletResponse response) {
		userService.modifyMobile(request, response);
	}
	
	// 로그아웃
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		userService.logout(request, response);
		return "redirect:/";
	}
	
	// 휴면 - 화면 이동
	@GetMapping("/user/sleep/display")
	public String sleepDisplay() {
		return "user/sleep";
	}
	
	// 휴면 - 정상회원으로 복구
	@PostMapping("/user/restore")
	public void restore(HttpServletRequest request, HttpServletResponse response) {
		userService.restoreUser(request, response);
	}
	
	// 회원탈퇴 - 비밀번호 확인 후 탈퇴
	@ResponseBody
	@PostMapping("/user/retire/checkPw")
	public Map<String, Object> requiredLogin_retire(HttpServletRequest request, HttpServletResponse response) {
		return userService.retire(request, response);
	}
	// 회원탈퇴 - 탈퇴 인사창
	@GetMapping("/user/retire/goodbye")
	public String retireMsg() {
		return "user/retire";
	}
	
	
}
