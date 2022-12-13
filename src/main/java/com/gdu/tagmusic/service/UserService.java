package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	
	// 로그인
	public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	
	
	
	// 회원가입
	public Map<String, Object> isReduceEmail(String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	
	// 회원 정보 수정
	
	
	// 휴면
	public void sleepUserHandle();  // SleepUserScheduler에서 호출
	
	
	// 탈퇴
}
