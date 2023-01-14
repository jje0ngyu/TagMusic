package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.domain.MusicCommentDTO;
import com.gdu.tagmusic.domain.MusicDTO;

public interface TuneService {
	
	// 음원 등록
	public int addMusic (MultipartHttpServletRequest request, HttpServletResponse response);
	
	// 음원 상세보기
	public MusicDTO getMusicByNo (int musicNo);
	public ResponseEntity<byte[]> displayMusic(int musicNo);
	public ResponseEntity<byte[]> displayImage(int musicNo);
	
	// 음원 다운로드
	public ResponseEntity<Resource> download(String userAgent, int musicNo);
	
	// 내가 쓴 글 목록
	public Map<String, Object> getMusics(HttpServletRequest request);
	public void removeMusic(int musicNo);
	
	// 트랙
	public Map<String, Object> getTrack();
	public Map<String, Object> getRandomTrack();
	
	// 댓글
	public Map<String, Object> getCommentCount(int musicNo);
	public Map<String, Object> getCommentList(HttpServletRequest request);
	public Map<String, Object> addComment(MusicCommentDTO comment);
	public Map<String, Object> removeComment(int commentNo);
}
