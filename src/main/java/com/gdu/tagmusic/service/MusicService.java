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

	// # 유저 플레이리스트

	// 1. 유저 플레이리스트 목록조회
	public Map<String, Object> selectUserPlaylist(HttpServletRequest request);
	
	// 2. 유저 플레이리스트 썸네일 
	public ResponseEntity<byte[]> selectPlaylistThumbnail(HttpServletRequest request);
	
	// 2. 플레이리스트 수록곡 조회
	public Map<String, Object> selectUserPlaylistMusicList(HttpServletRequest request);
	
	// 3. 플레이리스트명 수정
	public Map<String, Object> modifyUserPlaylistMusicList(HttpServletRequest request);
	
	// 4. 플레이리스트 삭제
	public Map<String, Object> deleteUserPlaylist(HttpServletRequest request);
	
	// 5. 수록곡 삭제
	public Map<String, Object> deletePlaylistMusic(HttpServletRequest request);
	
	// 6. 수록곡 추가
	public Map<String, Object> addPlaylistMusic(HttpServletRequest request);
	
	// 7. 플레이리스트 생성
	public Map<String, Object> createPlaylist(HttpServletRequest request);
	
	// # 유저 좋아요 
	
	// 1. 유저 좋아요 목록 조회
	public Map<String, Object> selectMusicLikeList(HttpServletRequest request);

	// 2. 유저 좋아요 목록에서 삭제
	public Map<String, Object> deleteUserMusicLike(HttpServletRequest request);
	
	// 3. 유저 좋아요 상태 조회
	public Map<String, Object> checkMusicLike(HttpServletRequest request);
	
	// 4. 좋아요 개수 조회
	public Map<String, Object> checkMusicLikeCnt(HttpServletRequest request);
	
	// 5. 좋아요 선택/해제
	public Map<String, Object> toggleMusicLike(HttpServletRequest request);

	// # 최근들은
	// 1) 최근들은 목록 조회
	public Map<String, Object> selectMusicLastlyList(HttpServletRequest request);
	
	// 2) 최근들은, 많이들은 목록 삭제
	public Map<String, Object> deleteUserMusicLog(HttpServletRequest request);

	// # 많이들은
	// 1) 많이들은 목록 조회
	public Map<String, Object> selectMusicManyList(HttpServletRequest request);
		
	// 2) 최근들은, 많이들은 전체 삭제
	public Map<String, Object> deleteALLUserMusicLog(HttpServletRequest request);

}
