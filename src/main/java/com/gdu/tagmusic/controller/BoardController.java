package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	
	@GetMapping("/board/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);  // model에 request를 저장하기
		boardService.getBoardList(model);          // model만 넘기지만 이미 model에는 request가 들어 있음
		return "board/list";
	}
	
	@GetMapping("/board/write")
	public String write() {
		return "board/write";
	}
	
	// @responseBody는 html에서 ajax시 사용
	@ResponseBody
	@PostMapping("/board/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		boardService.saveBoard(request, response);
	}
	
}