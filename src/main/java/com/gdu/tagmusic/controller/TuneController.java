package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.tagmusic.service.TuneService;

@Controller
public class TuneController {
	
	@Autowired
	private TuneService tuneService;
	
	// 음원 등록 - 등록 페이지
	@GetMapping("/tune/write")
	public String write (HttpServletRequest request) {
		return "tune/write";
	}
	
	// 음원 등록 - DB에 등록
	@PostMapping("/tune/upload")
	public String uploadTune (MultipartHttpServletRequest request, HttpServletResponse response) {
		tuneService.addMusic(request, response);
		return "redirect:/tune/detail";
	}
	
	// 음원 상세보기
	@GetMapping("/tune/detail")
	public String detailTune (@RequestParam(value="musicNo", required=false, defaultValue="0") int musicNo, Model model) {
		model.addAttribute("music", tuneService.getMusicByNo(musicNo));
		return "tune/detail";
	}
}
