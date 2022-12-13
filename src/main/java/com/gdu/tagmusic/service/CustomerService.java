package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;



public interface CustomerService {
	public int addChat(HttpServletRequest request);
	public void findChatList(HttpServletRequest request, HttpServletResponse response, Model model);
	
}
