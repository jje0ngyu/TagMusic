package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.tagmusic.domain.SleepUserDTO;
import com.gdu.tagmusic.domain.UserDTO;

public interface UserService {
	
	// 로그인
	public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	public UserDTO getUserBySessionId(Map<String, Object> map);
	
	
	// 회원가입
	public Map<String, Object> isReduceEmail(String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	
	// 회원 정보 수정
	public Map<String, Object> getImage(HttpServletRequest request);
	public void modifyArtist(HttpServletRequest request, HttpServletResponse response);
	public void modifyName(HttpServletRequest request, HttpServletResponse response);
	public void modifyMobile(HttpServletRequest request, HttpServletResponse response);
	
	// 휴면
	public void sleepUserHandle();  // SleepUserScheduler에서 호출
	public SleepUserDTO getSleepUserById(String id);
	
	// 로그아웃
	public void logout(HttpServletRequest request, HttpServletResponse response);
	
	// 탈퇴
	public Map<String, Object> retire(HttpServletRequest request, HttpServletResponse response);
}
