package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AdminService {
	
	// 1:1  문의 목록을 보는 메소드들
	public Map<String, Object> getChatListUsingScroll(HttpServletRequest request, Model model);
	public Map<String, Object> getDetailChat(HttpServletRequest request);
	public Map<String, Object> insertAdminChat(HttpServletRequest request);
	
	// 회원관리하는 메소드들
	public Map<String, Object> getUserList(HttpServletRequest request, Model model);
	
	// 회원 검색 메소드
	public Map<String, Object> searchUser(HttpServletRequest request);

	// 회원 검색 - 자동완성 서비스
	public Map<String, Object> getAutoCompleteList(HttpServletRequest request);
	
	// 휴면계정으로 처리
	public Map<String, Object> sleepUser(Map<String, Object> userNo);
	
	// 탈퇴계정으로 처리
	public Map<String, Object> removeUser(Map<String, Object> userNo);
}


