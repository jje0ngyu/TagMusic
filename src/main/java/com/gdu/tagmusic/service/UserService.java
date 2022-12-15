package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UserService {
	
	// 로그인
	public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	
	
	
	// 회원가입
	public Map<String, Object> isReduceEmail(String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	
	// 회원 정보 수정
	public void modifyArtist(HttpServletRequest request, HttpServletResponse response);
	
	// 휴면
	public void sleepUserHandle();  // SleepUserScheduler에서 호출
	
	
	// 로그아웃
	public void logout(HttpServletRequest request, HttpServletResponse response);
	
	// 탈퇴
}
