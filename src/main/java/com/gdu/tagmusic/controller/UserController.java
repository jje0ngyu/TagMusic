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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.UserDTO;
import com.gdu.tagmusic.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	
	// 로그인
	@GetMapping("/user/login/form")
	public String loginForm(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getHeader("referer"));
		model.addAttribute("apiURL", userService.getNaverLoginApiURL(request));	 // 네이버 로그인
		return "user/login";
	}
	@PostMapping("/user/login")
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
	@PostMapping("/user/info/getImage")
	public Map<String, Object> getImage(HttpServletRequest request){
		return userService.getImage(request);
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
