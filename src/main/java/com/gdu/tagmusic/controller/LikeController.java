package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.tagmusic.service.LikeService;

@RestController
public class LikeController {

	@Autowired
	private LikeService likeService;
	
	@GetMapping(value="/like/check", produces="application/json")
	public Map<String, Object> check(HttpServletRequest request) {
		return likeService.getLikeCheck(request);
	}
	
	@GetMapping(value="/like/mark", produces="application/json")
	public Map<String, Object> mark(HttpServletRequest request) {
		return likeService.markLike(request);
	}
	
}
