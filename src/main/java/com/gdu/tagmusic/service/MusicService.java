package com.gdu.tagmusic.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface MusicService {
	
	// [메인페이지]
	
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
	// 3) 인기리스트 게시판 조회
	public void selectPopularMusicList(HttpServletRequest request, Model model); 
	// 4) 장르별 인기리스트 데이터 가져오기
	public Map<String, Object> selectPopularMusicGenre4(HttpServletRequest request);
	
	// # 전체검색
	public void selectSearchMusic(HttpServletRequest request, Model model); 
	
	// # 랭킹
	public Map<String, Object> selectMusicRank();
	
	
	// [유저서비스]
	
	// # 1) 유저 플레이리스트목록요청 
	public void selectUserPlaylist(HttpServletRequest request, Model model); 
	
	public Map<String, Object> selectUserPlaylistMusicCnt(HttpServletRequest request); 
	
	// # 유저 플레이리스트 썸네일 가져오기
	public ResponseEntity<byte[]> selectUserPlaylist_TopMusicThumbnail(HttpServletRequest request);

	
	

}
