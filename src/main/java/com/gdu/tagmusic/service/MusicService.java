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
	
	// 2. 플레이리스트 수록곡 조회
	public Map<String, Object> selectUserPlaylistMusicList(HttpServletRequest request);
	
	// 3. 플레이리스트명 수정
	public Map<String, Object> modifyUserPlaylistMusicList(HttpServletRequest request);
	
	// 4. 플레이리스트 삭제
	public Map<String, Object> deleteUserPlaylist(HttpServletRequest request);
	
	// 5. 수록곡 삭제
	public Map<String, Object> deletePlaylistMusic(HttpServletRequest request);
	
	
	

	/*
	 * // 1. 유저의 listNo 가져오기 public void selectUserPlaylist(HttpServletRequest
	 * request, Model model);
	 * 
	 * // 2. 유저 플레이리스트 썸네일 public ResponseEntity<byte[]>
	 * selectPlaylistThumbnail(HttpServletRequest request);
	 * 
	 * 
	 * // 3. 유저 이름얻기 public void getUserName(HttpServletRequest request, Model
	 * model);
	 * 
	 * 
	 * // 4. 유저 플레이리스트 수정창 열기 public void getUserNameAndPlaylist(HttpServletRequest
	 * request, Model model);
	 * 
	 * // 5. 유저 플레이리스트명 수정 public void modifyPlaylistName(HttpServletRequest
	 * request);
	 * 
	 * // 6. 플레이리스트 삭제 public Map<String, Object>
	 * deleteUserPlaylist(HttpServletRequest request);
	 */
	/*
	 * // 4. 플레이리스트 추가 public void addPlaylist(HttpServletRequest request);
	 * 
	 */

	/*
	 * // 1) 유저 플레이리스트 목록조회 public Map<String, Object>
	 * selectUserPlaylist(HttpServletRequest request);
	 * 
	 * // 2) 유저 플레이리스트별 수록곡 개수 public Map<String, Object>
	 * selectUserPlaylistMusicCnt(HttpServletRequest request);
	 * 
	 * // 3) 유저 플레이리스트 썸네일 가져오기 public ResponseEntity<byte[]>
	 * selectUserPlaylist_TopMusicThumbnail(HttpServletRequest request);
	 */

}
