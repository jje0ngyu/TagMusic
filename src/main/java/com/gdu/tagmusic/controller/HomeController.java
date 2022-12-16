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

	
	// # 구현 : 최신리스트바 	------------------------------------------------
	// 1) 데이터 가져오기

	@ResponseBody
	@GetMapping(value="/music/list/updated", produces="application/json")
	public Map<String, Object> updatedMusicList(HttpServletRequest request) {	
		return homeService.selectUpdatedMusic4(request);
		
	}
	// 2) 썸네일 가져오기
	@ResponseBody
	@GetMapping("/music/thumbnail")
	public ResponseEntity<byte[]> thumbnail (HttpServletRequest request) {
		return homeService.selectThumbnail(request);
	}
	
			
	// # 구현 : 최신리스트 게시판 조회	-----------------------------------------
	@GetMapping("/music/updatedMusicBoard")
	public String updatedMusicBoard(HttpServletRequest request, Model model) {
		homeService.selectUpdateMusicList(request, model);
		return "/board/updatedMusic";
	}
	




}
