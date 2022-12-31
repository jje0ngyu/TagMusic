package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.MusicService;

@Controller
public class MusicController {

	@Autowired
	private  MusicService musicService;
	
	// # page : main 페이지 이동
	@GetMapping("/")
	public String index() {
		return "main";
	}

	// [main페이지]
	// # 구현 : 최신리스트바 	===============================================
	// 1) 데이터 가져오기

	@ResponseBody
	@GetMapping(value="/music/list/updated", produces="application/json")
	public Map<String, Object> updatedMusic(HttpServletRequest request) {	
		return musicService.selectUpdatedMusic4(request);
		
	}
	// 2) 썸네일 가져오기
	@ResponseBody
	@GetMapping("/music/thumbnail")
	public ResponseEntity<byte[]> thumbnail (HttpServletRequest request) {
		return musicService.selectThumbnail(request);
	}
	
			
	// # 구현 : 최신리스트 게시판 조회	-----------------------------------------
	@GetMapping("/music/board/updatedMusic")
	public String updatedMusicBoard(HttpServletRequest request, Model model) {
		musicService.selectUpdateMusicList(request, model);
		return "/musicBoard/musicBoard";
	}
	
	
	
	// # 구현 : 인기리스트 바	================================================
	
	@ResponseBody
	@GetMapping(value="/music/list/popular", produces="application/json")
	public Map<String, Object> popularMusic(HttpServletRequest request) {	
		return musicService.selectPopularMusic4(request);
		
	}
	
	// 구현 : 인기리스트 게시판 조회	----------------------------------------
	@GetMapping("/music/board/popularMusic")
	public String popularMusicBoard(HttpServletRequest request, Model model) {
		musicService.selectPopularMusicList(request, model);
		return "/musicBoard/musicBoard";
	}
	
	
	
	// # 구현 : 장르별 인기리스트 바 -------------------------------------------
	@ResponseBody
	@GetMapping(value="/music/list/popular/genre", produces="application/json")
	public Map<String, Object> popularMusicGenre(HttpServletRequest request) {	
		return musicService.selectPopularMusicGenre4(request);
		
	}
	
	// # 구현 : 전체검색
	@GetMapping("/music/main/totalSearch")
	public String totalSearch(HttpServletRequest request, Model model) {
		musicService.selectSearchMusic(request, model);
		return "/musicBoard/musicBoard";
	}

	// # 구현 : 음악랭킹(댓글수 + 좋아요수)
	@ResponseBody
	@GetMapping(value="/music/ranking", produces="application/json")
	public Map<String, Object> musicRank() {	
		return musicService.selectMusicRank();
		
	}
	
	// # 구현 : 음악플레이어	=================================================
	@GetMapping("/music/player")
	public String musicPlayer() {
		return "/layout/musicPlayer";
	}
	
	
	
	
	// [유저서비스]
	
	// 1. 플레이리스트	=========================================================================================================
	
	// 구현 : 플레이리스트 페이지 이동 
	@GetMapping("/music/move/playlist")
	public String movePlaylist(HttpServletRequest request, Model model) {
		return "/musicUserService/playlist";
	}
	
	// 구현 : 플레이리스트 목록조회
	@ResponseBody
	@GetMapping(value="/music/user/playlist", produces="application/json")
	public Map<String, Object> userPlaylist(HttpServletRequest request) {	
		return musicService.selectUserPlaylist(request);
	}
	
	// 구현 : 플레이리스트 최신등록곡 썸네일 가져오기
	@ResponseBody
	 @GetMapping("/music/user/playlistThumbnail") 
	public ResponseEntity<byte[]> playlistThubnail (HttpServletRequest request) { 
		return musicService.selectPlaylistThumbnail(request); 
	}
	
	// 구현: 플레이리스트에 수록된 곡 조회
	@ResponseBody
	@GetMapping(value="/music/user/playlistMusiclist", produces="application/json")
	public Map<String, Object> userPlaylistMusic(HttpServletRequest request) {	
		return musicService.selectUserPlaylistMusicList(request);
	}
	
	// 구현 : 플레이리스트명 수정
	@ResponseBody
	@PostMapping(value="/music/user/modifyPlaylistName", produces="application/json")
	public Map<String, Object> modifyPlaylistName(HttpServletRequest request) {	
		return musicService.modifyUserPlaylistMusicList(request);
	}
	
	// 구현 : 플레이리스트 삭제
	@ResponseBody
	@PostMapping(value="/music/user/deletePlaylist", produces="application/json")
	public Map<String, Object> deletePlaylist(HttpServletRequest request) {	
		return musicService.deleteUserPlaylist(request);
	}
	

	// 구현 : 수록곡 삭제
	@ResponseBody
	@PostMapping(value="/music/user/deletePlaylistMusic", produces="application/json")
	public Map<String, Object> deletePlaylistMusic(HttpServletRequest request) {	
		return musicService.deletePlaylistMusic(request);
	}
	
	// 구현 : 플레이리스트에 음악 추가
	@ResponseBody
	@GetMapping(value="/music/user/playlistAddMusic", produces="application/json")
	public Map<String, Object> addPlaylistMusic(HttpServletRequest request) {	
		return musicService.addPlaylistMusic(request);
	}
	
	// 구현: 플레이리스트 생성
	@ResponseBody
	@GetMapping(value="/music/user/createPlaylist", produces="application/json")
	public Map<String, Object> createPlaylist(HttpServletRequest request) {	
		return musicService.createPlaylist(request);
	}
	
	
	// 2. 좋아요 ====================================================================================================================
	
	// 구현 : 좋아요 페이지 이동 
		@GetMapping("/music/move/musicLike")
		public String moveMusicLike(HttpServletRequest request, Model model) {
			return "/musicUserService/musicLike";
		}
		
	// 구현 : 좋아요 목록조회
	@ResponseBody
	@GetMapping(value="/music/user/musicLikeList", produces="application/json")
	public Map<String, Object> userMusicLikeList(HttpServletRequest request) {	
		return musicService.selectMusicLikeList(request);
	}
	
	// 구현 : 좋아요 삭제
	@ResponseBody
	@PostMapping(value="/music/user/deleteUserMusicLike", produces="application/json")
	public Map<String, Object> deleteUserMusicLike(HttpServletRequest request) {	
		return musicService.deleteUserMusicLike(request);
	}
	
	// 구현 : 좋아요 상태 조회
	@ResponseBody
	@GetMapping(value="/music/user/checkMusicLike", produces="application/json")
	public Map<String, Object> checkMusicLike(HttpServletRequest request) {	
		return musicService.checkMusicLike(request);
	}
	
	// 구현 : 좋아요 개수 확인
	@ResponseBody
	@GetMapping(value="/music/user/checkMusicLikeCnt", produces="application/json")
	public Map<String, Object> checkMusicLikeCnt(HttpServletRequest request) {	
		return musicService.checkMusicLikeCnt(request);
	}
	
	// 구현 : 좋아요 선택/해제
		@ResponseBody
		@GetMapping(value="/music/user/toggleMusicLike", produces="application/json")
		public Map<String, Object> toggleMusicLike(HttpServletRequest request) {	
			return musicService.toggleMusicLike(request);
		}
		
	// 3. 최근들은
		
	// 구현 : 최근들은 페이지 이동
	@GetMapping("/music/move/musicLastly")
	public String musicLastly(HttpServletRequest request, Model model) {
		return "/musicUserService/musicLastly";
	}
			
	// 구현 : 최근들은 목록 조회
	@ResponseBody
	@GetMapping(value="/music/user/musicLastlyList", produces="application/json")
	public Map<String, Object> musicLastly(HttpServletRequest request) {
		return musicService.selectMusicLastlyList(request);
	}
		
	// 구현 : 최근들은 삭제
	@ResponseBody
	@PostMapping(value="/music/user/deleteMusicLog", produces="application/json")
	public Map<String, Object> deleteMusicLog(HttpServletRequest request) {	
		return musicService.deleteUserMusicLog(request);
	}
		
	// 4. 많이들은
	// 구현 : 많이들은 페이지 이동
	@GetMapping("/music/move/musicMany")
	public String musicMany(HttpServletRequest request, Model model) {
		return "/musicUserService/musicMany";
	}
			
	// 구현 : 많이들은 목록 조회
	@ResponseBody
	@GetMapping(value="/music/user/musicManyList", produces="application/json")
	public Map<String, Object> musicMany(HttpServletRequest request) {
		return musicService.selectMusicManyList(request);
	}

	
	// 구현 : 많이들은 전체 삭제
	@ResponseBody
	@PostMapping(value="/music/user/deleteAllUserMusicLog", produces="application/json")
	public Map<String, Object> deleteAllUserMusicLog(HttpServletRequest request) {	
		return musicService.deleteALLUserMusicLog(request);
	}


	
	
	
	

	
	/*
	 * // 기능 : 플레이리스트에 최신 추가된 게시글의 썸네일 가져오기
	 * 
	 * @ResponseBody
	 * 
	 * @GetMapping("/music/user/playlistThumbnail") public ResponseEntity<byte[]>
	 * playlistThubnail (HttpServletRequest request) { return
	 * musicService.selectPlaylistThumbnail(request); }
	 */
	// # 구현 : 플레이리스트 생성 페이지 열기
	
	/*
	 * @GetMapping("/music/move/playlistAdd") public String
	 * moveCreatePlaylist(HttpServletRequest request, Model model) {
	 * musicService.getUserName(request, model); return
	 * "/musicUserService/createPlaylistPage"; }
	 */
	
	// # 구현 : 플레이리스트 생성 : 팝업창 종료와 
	
	/*
	 * @GetMapping("/music/user/playlistAdd") public String
	 * addPlaylist(HttpServletRequest request) { musicService.addPlaylist(request);
	 * return "/musicUserService/createPlaylistPage"; }
	 */
	/*
	 * // 구현 : 플레이리스트 수정 페이지 열기
	 * 
	 * @GetMapping("/music/move/playlistModify") public String
	 * moveUpdatePlaylist(HttpServletRequest request, Model model) {
	 * musicService.getUserNameAndPlaylist(request, model); return
	 * "/musicUserService/updatePlaylistPage"; }
	 * 
	 * // 구현 : 플레이리스트명 수정
	 * 
	 * @PostMapping("/music/user/playlistModify") public String
	 * modifyPlaylist(HttpServletRequest request) {
	 * musicService.modifyPlaylistName(request); return
	 * "/musicUserService/updatePlaylistPage"; }
	 * 
	 * // 구현 : 플레이리스트 삭제
	 * 
	 * @ResponseBody
	 * 
	 * @PostMapping(value="/music/user/playlistDelete", produces="application/json")
	 * public Map<String, Object> deletePlaylist(HttpServletRequest request) {
	 * return musicService.deleteUserPlaylist(request); }
	 * 
	 */
	
	// 구현 : 유저 플레이리스트 목록조회
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping(value="/music/user/playlist", produces="application/json") public
	 * Map<String, Object> userPlaylist(HttpServletRequest request) { return
	 * musicService.selectUserPlaylist(request); }
	 */
	
	// # 기능 : 플레이리스트별 음악개수 조회
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping(value="/music/user/playlistMusicCnt",
	 * produces="application/json") public Map<String, Object>
	 * userPlaylistCnt(HttpServletRequest request) { return
	 * musicService.selectUserPlaylistMusicCnt(request); }
	 */
	
	
	
	// # 기능 : 플레이리스트 썸네일 불러오기
	
	


	
	
	
	
	
	
	
	
}
