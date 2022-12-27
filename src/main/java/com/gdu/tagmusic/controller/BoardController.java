package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);  
		boardService.getBoardList(model);          
		return "board/list";
	}
	
	@GetMapping("/board/write")
	public String write() {
		return "board/write";
	}
	
	@ResponseBody
	@PostMapping(value="/board/uploadImage", produces="application/json")
	public Map<String, Object> uploadImage(MultipartHttpServletRequest multipartRequest) {
		return boardService.saveSummernoteImage(multipartRequest);
	}
	
	@PostMapping("/board/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		boardService.saveBoard(request, response);
	}
	
	@GetMapping("/board/increse/hit")
	public String increseHit(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo) {
		int result = boardService.increseBoardHit(boardNo);
		if(result > 0) {  
			return "redirect:/board/detail?boardNo=" + boardNo;
		} else {          
			return "redirect:/board/list";
		}
	}
	
	@GetMapping("/board/detail")
	public String detail(@RequestParam(value="boardNo", required=false, defaultValue="0") int boardNo, Model model) {
		model.addAttribute("board", boardService.getBoardByNo(boardNo));
		return "board/detail";
	}
	
	@PostMapping("/board/edit")
	public String edit(int boardNo, Model model) {
		model.addAttribute("board", boardService.getBoardByNo(boardNo));
		return "board/edit";
	}
	
	@PostMapping("/board/modify")
	public void modify(HttpServletRequest request, HttpServletResponse response) {
		boardService.modifyBoard(request, response);
	}
	
	@PostMapping("/board/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		boardService.removeBoard(request, response);
	}
	
	
}