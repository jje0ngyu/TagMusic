package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.tagmusic.mapper.BoardMapper;
import com.gdu.tagmusic.util.MyFileUtil;
import com.gdu.tagmusic.util.PageUtil;
import com.gdu.tagmusic.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	private PageUtil pageUtil;
	private MyFileUtil myFileUtil;
	private SecurityUtil securityUtil;
	

	@Override
	public void findAllBoardList(HttpServletRequest request, Model model) {
		
		
	}
	
	@Override
	public void addBoard(HttpServletRequest request, HttpServletResponse response) {
	
	
	}
	
	}
	
	
	

	
	
	
