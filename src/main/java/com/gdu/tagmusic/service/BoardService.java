package com.gdu.tagmusic.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.BoardDTO;


public interface BoardService {
	
	public void getBoardList(Model model);
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest);
	public void saveBoard(HttpServletRequest request, HttpServletResponse response);
	public int increseBoardHit(int boardNo);
	public BoardDTO getBoardByNo(int boardNo);
	public void modifyBoard(HttpServletRequest request, HttpServletResponse response);
	public void removeBoard(HttpServletRequest request, HttpServletResponse response);
	
}

