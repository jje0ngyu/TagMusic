package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String uploadTune (MultipartHttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {
		int musicNo = tuneService.addMusic(request, response);
		redirect.addAttribute("musicNo", musicNo);
		return "redirect:/tune/detail";
	}
	
	// 음원 상세보기 - 페이지 이동
	@GetMapping("/tune/detail")
	public String detailTune (@RequestParam(value="musicNo", required=false, defaultValue="0") int musicNo, Model model) {
		model.addAttribute("music", tuneService.getMusicByNo(musicNo));
		return "tune/detail";
	}
	
	// 음원 상세보기 - 음원 정보 호출
	@ResponseBody
	@GetMapping("/tune/display/music")
	public ResponseEntity<byte[]> display(@RequestParam int musicNo){
		return tuneService.displayMusic(musicNo);
	}
	// 음원 상세보기 - 앨범 이미지 정보 호출
	@ResponseBody
	@GetMapping("/tune/display/image")
	public ResponseEntity<byte[]> displayImage(@RequestParam int musicNo){
		return tuneService.displayImage(musicNo);
	}
	
	// 음원 상세보기 - 다운로드
	@ResponseBody
	@GetMapping("/tune/download")
	public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent, @RequestParam("musicNo") int musicNo) {
		return tuneService.download(userAgent, musicNo);
	}
	
	// 음원 - iframe
	@GetMapping("/tune/iframe")
	public String musicPlayer(@RequestParam(value="musicNo", required=false, defaultValue="0") int musicNo, HttpServletRequest request, Model model) {
		System.out.println("iframe.musicNo: " + musicNo);
		/*
		if(musicNo == 0) {
			Optional<String> opt = Optional.ofNullable(request.getSession().getAttribute("musicNo"));
			musicNo = Integer.parseInt();
		}
		*/
		model.addAttribute("music", tuneService.getMapByMusicNo(musicNo));
		System.out.println(model);
		return "/layout/musicPlayer";
	}
	
}
