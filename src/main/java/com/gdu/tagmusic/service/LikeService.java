package com.gdu.tagmusic.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public interface LikeService {
	
	public Map<String, Object> getLikeCheck (HttpServletRequest request);
	public Map<String, Object> markLike (HttpServletRequest request);

}