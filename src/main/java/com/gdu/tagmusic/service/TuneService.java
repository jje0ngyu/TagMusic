package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.MusicDTO;

public interface TuneService {
	
	// 음원 등록
	public int addMusic (MultipartHttpServletRequest request, HttpServletResponse response);
	
	// 음원 상세보기
	public MusicDTO getMusicByNo (int musicNo);
	public ResponseEntity<byte[]> display(int musicNo);
	public ResponseEntity<byte[]> displayImage(int musicNo);
	
	// 음원 다운로드
	public ResponseEntity<Resource> download(String userAgent, int musicNo);
}
