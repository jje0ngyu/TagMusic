package com.gdu.tagmusic.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;


public interface BoardService {
	
	public void getBoardList(Model model);
	public void saveBoard(HttpServletRequest request, HttpServletResponse response);
	
	}

