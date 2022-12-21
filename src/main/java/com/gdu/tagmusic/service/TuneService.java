package com.gdu.tagmusic.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface TuneService {

	public void addMusic(MultipartHttpServletRequest request, HttpServletResponse response);
}
