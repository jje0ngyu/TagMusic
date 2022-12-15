package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.tagmusic.domain.ChatDTO;



public interface CustomerService {
	public Map<String, Object> addChat(HttpServletRequest request);
	public void divideUser(HttpServletRequest request, HttpServletResponse response);
	public ChatDTO getChatUserNo(HttpServletRequest request);
	
}
