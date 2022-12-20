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

import com.gdu.tagmusic.service.UploadService;

@Controller
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	
	@GetMapping("/upload/list")
	public String list(Model model) {
		model.addAttribute("uploadList", uploadService.getUploadList());
		return "upload/list";
	}
	
	@GetMapping("/upload/write")
	public String write() {
		return "upload/write";
	}
	
	@PostMapping("/upload/add")
	public void add(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		uploadService.save(multipartRequest, response);
	}
	
	@GetMapping("/upload/detail")
	public String detail(@RequestParam(value="uploadNo", required=false, defaultValue="0") int uploadNo, Model model) {
		uploadService.getUploadByNo(uploadNo, model);
		return "upload/detail";
	}
	
	@ResponseBody
	@GetMapping("/upload/display")
	public ResponseEntity<byte[]> display(@RequestParam int attachNo){
		return uploadService.display(attachNo);
	}
	
	@ResponseBody
	@GetMapping("/upload/download")
	public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent, @RequestParam("attachNo") int attachNo) {
		return uploadService.download(userAgent, attachNo);
	}
	
	@ResponseBody
	@GetMapping("/upload/downloadAll")
	public ResponseEntity<Resource> downloadAll(@RequestHeader("User-Agent") String userAgent, @RequestParam("uploadNo") int uploadNo) {
		return uploadService.downloadAll(userAgent, uploadNo);
	}
	
	@PostMapping("/upload/edit")
	public String edit(@RequestParam("uploadNo") int uploadNo, Model model) {
		uploadService.getUploadByNo(uploadNo, model);
		return "upload/edit";
	}
	
	@PostMapping("/upload/modify")
	public void modify(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		uploadService.modifyUpload(multipartRequest, response);
	}
	
	@GetMapping("/upload/attach/remove")
	public String uploadAttachRemove(@RequestParam("uploadNo") int uploadNo, @RequestParam("attachNo") int attachNo) {
		uploadService.removeAttachByAttachNo(attachNo);
		return "redirect:/upload/detail?uploadNo=" + uploadNo;
	}
	
	@PostMapping("/upload/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		uploadService.removeUpload(request, response);
	}
	
}