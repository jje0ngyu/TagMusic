package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.MusicDTO;

public interface TuneService {
	
	// 음원 등록
	public void addMusic (MultipartHttpServletRequest request, HttpServletResponse response);
	
	// 음원 상세보기
	public MusicDTO getMusicByNo (int musicNo);
}
