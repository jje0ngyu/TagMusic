package com.gdu.tagmusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.tagmusic.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin/chatUserList")
	public String moveAdminpageChat(Model model) {
		model.addAttribute("chatList",adminService.userChatList());
		return "admin/chatUserList";
	}
	
	


}
