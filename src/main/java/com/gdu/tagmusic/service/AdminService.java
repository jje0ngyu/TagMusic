package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AdminService {
	public Map<String, Object> getChatListUsingScroll(HttpServletRequest request, Model model);
	public Map<String, Object> getDetailChat(HttpServletRequest request);
	public Map<String, Object> insertAdminChat(HttpServletRequest request);
	

}
