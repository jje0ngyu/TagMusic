package com.gdu.tagmusic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

	
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
	@GetMapping("/customerService/chat")
	public String customerServiceChat() {
		return "customerCenter/customerServiceChat";
	}
	
	
	
	
}
