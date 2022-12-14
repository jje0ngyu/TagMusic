package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	// 고객센터 버튼을 눌렀을 때 나오는 첫 화면 - 고객센터의 홈.
	@GetMapping("/customerService/home")
	public String customerService() {
		return "customerCenter/customerServiceHome";
	}
	
	// 고객센터에서 공지사항을 클릭했을 때 
	@GetMapping("/customerService/notice")
	public String customerServiceNotice() {
		return "customerCenter/customerServiceNotice";
	}
	
	// 고객센터에서 FAQ를 클릭했을 때
	@GetMapping("/customerService/FAQ")
	public String customerServiceFAQ() {
		return "customerCenter/customerServiceFAQ";
	}
	
	// 고객센터에서 1:1문의하기를 클릭했을 때
	// 리스트를 들리고 나와야함
	@GetMapping("/customerService/chat")
	public void customerServiceChat(HttpServletRequest request, HttpServletResponse response, Model model) {
		customerService.findChatList(request, response, model);
		//return "customerCenter/customerServiceChat";
	}
	
	// 1:1 문의 단순 페이지이동 -- CustomerServiceImpl에서 findChatList 사용함
	@GetMapping("/customerService/customerServiceChat")
	public String customerServiceCustomerServiceChat(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "customerCenter/customerServiceChat";
	}
	
	@ResponseBody
	@PostMapping(value="/chat/add", produces="application/json")
	public Map<String, Object> add(HttpServletRequest request) {
		return customerService.addChat(request); /// 인설트의 여부가 여기에 담겨있음. 1이면 true, 0이면 false
	}

	
	
	
	
}
