package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	public void modifyImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response);
	public void modifyArtist(HttpServletRequest request, HttpServletResponse response);
	public void modifyName(HttpServletRequest request, HttpServletResponse response);
	public void modifyMobile(HttpServletRequest request, HttpServletResponse response);
	
	// 휴면
	// 휴면 확인
	public SleepUserDTO getSleepUserByEmail(String email);
	// 휴면 처리
	public void sleepUserHandle();  // SleepUserScheduler에서 호출
	// 휴면에서 정상회원으로 복구
	public void restoreUser(HttpServletRequest request, HttpServletResponse response); 
	
	// 로그아웃
	public void logout(HttpServletRequest request, HttpServletResponse response);
	
	// 탈퇴
	public Map<String, Object> retire(HttpServletRequest request, HttpServletResponse response);
}
