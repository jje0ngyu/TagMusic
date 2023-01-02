package com.gdu.tagmusic.controller;

import java.util.Map;

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

import com.gdu.tagmusic.domain.MusicCommentDTO;
import com.gdu.tagmusic.service.TuneService;

@Controller
public class TuneController {
	
	@Autowired
	private TuneService tuneService;
	
	// 음원 등록 - 등록 페이지
	@GetMapping("/tune/write")
	public String requiredLogin_write (HttpServletRequest request) {
		return "tune/write";
	}
	
	// 음원 등록 - DB에 등록
	@PostMapping("/tune/upload")
	public String uploadTune (MultipartHttpServletRequest request, HttpServletResponse response, RedirectAttributes redirect) {
		int musicNo = tuneService.addMusic(request, response);
		redirect.addAttribute("musicNo", musicNo);
		return "redirect:/tune/iframe";
	}
	
	// 음원 - iframe
	@GetMapping("/tune/iframe")
	public String musicPlayer(@RequestParam(value="musicNo", required=false, defaultValue="0") int musicNo, HttpServletRequest request, Model model) {
		model.addAttribute("music", tuneService.getMusicByNo(musicNo));
		return "layout/musicPlayer";
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
	public ResponseEntity<Resource> requiredLogin_download(@RequestHeader("User-Agent") String userAgent, @RequestParam("musicNo") int musicNo) {
		return tuneService.download(userAgent, musicNo);
	}
	
	// 음원 상세보기 - 트랙
	@ResponseBody
	@GetMapping("/tune/track")
	public Map<String, Object> getTrack() {
		return tuneService.getTrack();
	}
	// 음원 상세보기 - 셔플 트랙
	@ResponseBody
	@GetMapping("/tune/shuffleTrack")
	public Map<String, Object> getRandomTrack() {
		return tuneService.getRandomTrack();
	}
	
	// 음원 상세보기 - 댓글
	// 댓글 - 카운트
	@ResponseBody
	@GetMapping(value="/comment/getCount", produces="application/json")
	public Map<String, Object> getCount(@RequestParam("musicNo") int musicNo) {
		return tuneService.getCommentCount(musicNo);
	}
	// 댓글 - 리스트
	@ResponseBody
	@GetMapping(value="/comment/list", produces="application/json")
	public Map<String, Object> list(HttpServletRequest request) {
		return tuneService.getCommentList(request);
	}
	// 댓글 - 삽입
	@ResponseBody
	@PostMapping(value="/comment/add", produces="application/json")
	public Map<String, Object> add(MusicCommentDTO comment) {
		return tuneService.addComment(comment);
	}
	// 댓글 - 삭제
	@ResponseBody
	@PostMapping(value="/comment/remove", produces="application/json")
	public Map<String, Object> remove(@RequestParam("commentNo") int commentNo){
		return tuneService.removeComment(commentNo);
	}
}
