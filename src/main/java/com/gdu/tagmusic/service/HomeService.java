package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface HomeService {
	
	// # 구현 : main 페이지 최신리스트
	// 1) main 페이지 최신리스트 조회
	public Map<String, Object> selectUpdatedMusic4(HttpServletRequest request);
	
	// 2) 썸네일 가져오기
	public ResponseEntity<byte[]> selectThumbnail(HttpServletRequest request);
	
	
	// # 구현 : 전체목록 조회 + 페이지 이동
	
	
	

}
