package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	private  HomeService homeService;
	
	// # page : main 페이지 이동
	@GetMapping("/")
	public String index() {
		return "index";
	}

	
	// # 구현 : 최신리스트바 	===============================================
	// 1) 데이터 가져오기

	@ResponseBody
	@GetMapping(value="/music/list/updated", produces="application/json")
	public Map<String, Object> updatedMusic(HttpServletRequest request) {	
		return homeService.selectUpdatedMusic4(request);
		
	}
	// 2) 썸네일 가져오기
	@ResponseBody
	@GetMapping("/music/thumbnail")
	public ResponseEntity<byte[]> thumbnail (HttpServletRequest request) {
		return homeService.selectThumbnail(request);
	}
	
			
	// # 구현 : 최신리스트 게시판 조회	-----------------------------------------
	@GetMapping("/music/board/updatedMusic")
	public String updatedMusicBoard(HttpServletRequest request, Model model) {
		homeService.selectUpdateMusicList(request, model);
		return "/musicBoard/latestMusic";
	}
	
	
	
	// # 구현 : 인기리스트 바	================================================
	
	@ResponseBody
	@GetMapping(value="/music/list/popular", produces="application/json")
	public Map<String, Object> popularMusic(HttpServletRequest request) {	
		return homeService.selectPopularMusic4(request);
		
	}
	
	// 구현 : 인기리스트 게시판 조회	----------------------------------------
	@GetMapping("/music/board/popularMusic")
	public String popularMusicBoard(HttpServletRequest request, Model model) {
		homeService.selectPopularMusicList(request, model);
		return "/musicBoard/popularMusic";
	}
	
	
	
	// # 구현 : 장르별 인기리스트 바 -------------------------------------------
	@ResponseBody
	@GetMapping(value="/music/list/popular/genre", produces="application/json")
	public Map<String, Object> popularMusicGenre(HttpServletRequest request) {	
		return homeService.selectPopularMusicGenre4(request);
		
	}
	
	// # 구현 : 전체검색
	@GetMapping("/music/main/totalSearch")
	public String totalSearch(HttpServletRequest request, Model model) {
		homeService.selectSearchMusic(request, model);
		return "/musicBoard/searchMusic";
	}

	// # 구현 : 음악랭킹(댓글수 + 좋아요수)
	@ResponseBody
	@GetMapping(value="/music/ranking", produces="application/json")
	public Map<String, Object> musicRank() {	
		return homeService.selectMusicRank();
		
	}
	
	// # 구현 : 음악플레이어	=================================================
	@GetMapping("/music/player")
	public String musicPlayer() {
		return "/layout/musicPlayer";
	}
	
	// # 구현 : 플레이리스트 목록페이지 이동	===================================== 
	@GetMapping("/music/user/playlistPage")
	public String movePlaylist_listPage() {
		return "/userMusicService/playlistPage";
	}
	
	// # 구현 : 플레이리스트 생성 페이지
	@GetMapping("/music/user/playlist/add")
	public String CreatePlaylistPage() {
		return "/userMusicService/createPlaylist";
	}
	
}
