package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin/chatUserList")
	public String moveAdminpageChat(Model model) {
		return "admin/chatUserList";
	}
	
	@ResponseBody
	@GetMapping(value="/chat/list/scroll", produces="application/json")
	public Map<String, Object> listScroll(HttpServletRequest request, Model model) {
		return adminService.getChatListUsingScroll(request, model);
	}
	
	@ResponseBody
	@PostMapping(value="/chat/list/detail", produces="application/json")
	public Map<String, Object> chatDetail(HttpServletRequest request) {
		return adminService.getDetailChat(request);
	}
	
	// 관리자페이지로 이동하는 단순 매핑
	@GetMapping("/admin/user/control")
	public String userControl() {
		return "admin/userAdmin";
	}
	
	// (ajax)로 처리할 예정
	@ResponseBody
	@GetMapping(value="/admin/user/list", produces="application/json")
	public Map<String, Object> getUserList(HttpServletRequest request, Model model) {
		return adminService.getUserList(request, model);
		
	}
	
	
	
	@ResponseBody
	@PostMapping(value="/admin/chat/add", produces="application/json")
	public Map<String, Object> adminInsertChat(HttpServletRequest request){
		
		return adminService.insertAdminChat(request);
	}
	



}
