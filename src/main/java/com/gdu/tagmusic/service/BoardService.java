package com.gdu.tagmusic.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;


public interface BoardService {
	
	public void findAllBoardList(HttpServletRequest request, Model model);
	public void addBoard(HttpServletRequest request, HttpServletResponse response);
	
}

