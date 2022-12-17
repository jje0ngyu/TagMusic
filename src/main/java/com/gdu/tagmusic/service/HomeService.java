package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface HomeService {
	
	// # 최신리스트 
	// 1) 최신리스트 바 데이터 가져오기
	public Map<String, Object> selectUpdatedMusic4(HttpServletRequest request);
	// 2) 썸네일 가져오기
	public ResponseEntity<byte[]> selectThumbnail(HttpServletRequest request);
	// 3) 최신리스트 게시판조회
	public void selectUpdateMusicList(HttpServletRequest request, Model model); 
	
	
	// # 인기리스트 
	// 1) 인기리스트 바 데이터 가져오기
	public Map<String, Object> selectPopularMusic4(HttpServletRequest request);
	// 2) 썸네일 가져오기 : 재활용
	
	// # 장르별 인기리스트
	// 1) 장르별 인기리스트 데이터 가져오기
	public Map<String, Object> selectPopularMusicGenre4(HttpServletRequest request);

	
	

}
