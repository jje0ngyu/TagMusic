package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	// 유저면 로그인페이지이동, 비회원이면 화면 보여줌. 리스트는ajax로 해서 뽑을거임
	@GetMapping("/customerService/chat")
	public void customerServiceChat(HttpServletRequest request, HttpServletResponse response) {
		customerService.divideUser(request, response);
		//return "customerCenter/customerServiceChat";
	}
	
	// 1:1 문의 페이지이동 --  페이지 이동과 동시에 리스트 뽑아옴
	@GetMapping("/customerService/customerServiceChat")
	public String customerServiceCustomerServiceChat(HttpServletRequest request, Model model) {
		model.addAttribute("chatUserNo", customerService.getChatUserNo(request));
		return "customerCenter/customerServiceChat";
	}
	
	@ResponseBody
	@PostMapping(value="/chat/add", produces="application/json")
	public Map<String, Object> chatadd(HttpServletRequest request) {
		
		
		
		return customerService.addChat(request); /// 인설트의 여부가 여기에 담겨있음. 1이면 true, 0이면 false
	}
	
//	// 로그인한 userNo가 문의한 게시글만 뽑는 매핑값
//	@ResponseBody
//	@PostMapping(value="/chat/list", produces="application/json")
//	public Map<String, Object> chatlist(@RequestParam("userNo") int userNo){
//		// 자신이 문의한 글만 볼 수 있게 userNo를 파라미터로 넘겨줌
//		return customerService.getChatListUserNo(userNo);
//		
//	}

	
	
	
	
}
