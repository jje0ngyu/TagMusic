package com.gdu.tagmusic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	// 회원 리스트 조회
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
	
	//유저검색
	@ResponseBody
	@GetMapping(value="/admin/user/search", produces="application/json")
	public Map<String, Object> searchUser(HttpServletRequest request, HttpServletResponse response){
		return adminService.searchUser(request, response);
	}
	
	// 자동완성
	@ResponseBody
	@GetMapping(value="/user/autoComplete", produces="application/json")
	public Map<String, Object> autoComplete(HttpServletRequest request){
		return adminService.getAutoCompleteList(request);
	}
	

	//휴면처리용 메소드
	@ResponseBody
	@PostMapping(value="/admin/sleepUser", produces="application/json; charset=UTF-8")
	public Map<String, Object> TranslationSleep(@RequestParam(value="userNo[]") List<String> userNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", userNo);
		return adminService.sleepUser(map);
	}
	
	// 탈퇴처리용 메소드
	@ResponseBody
	@PostMapping(value="/admin/retireUser", produces="application/json; charset=UTF-8")
	public Map<String, Object> TranslationRetire(@RequestParam(value="userNo[]") List<String> userNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", userNo);
		return adminService.removeUser(map);
	}



}
