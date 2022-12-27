package com.gdu.tagmusic.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public interface UploadService {
	
	public void getUploadList(Model model);

	public void save(MultipartHttpServletRequest multipartRequest, HttpServletResponse response);
	public void getUploadByNo(int uploadNo, Model model);
	public ResponseEntity<byte[]> display(int attachNo);
	public ResponseEntity<Resource> download(String userAgent, int attachNo);
	public ResponseEntity<Resource> downloadAll(String userAgent, int uploadNo);
	public void modifyUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response);
	public void removeAttachByAttachNo(int attachNo);
	public void removeUpload(HttpServletRequest multipartRequest, HttpServletResponse response);
	public int increseHit(int uploadNo);
}