package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	private  HomeService homeService;
	
	// # main 페이지 이동
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	// # main화면 최신리스트 조회
	// - ajax처리 : @responsebody, produces 속성으로 json처리
	@ResponseBody
	@GetMapping(value="/music/list/updated", produces="application/json")
	public Map<String, Object> updatedMusicList(HttpServletRequest request) {	
		return homeService.selectUpdatedMusic4(request);
		
	}
			
	
	// # 전체음악리스트 조회, 페이지 이동
	
}
