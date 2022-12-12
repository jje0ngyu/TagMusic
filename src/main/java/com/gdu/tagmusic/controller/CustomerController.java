package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	// 보안 -- 리스트를 들렸다가 나와야될 것 같음
	@GetMapping("/customerService/chat")
	public String customerServiceChat() {
		return "customerCenter/customerServiceChat";
	}
	
	@PostMapping("/chat/add")
	public String add(HttpServletRequest request) {
		customerService.addChat(request);
		return "redirect:/customerCenter/customerServiceChat";
	}

	
	
	
	
}
