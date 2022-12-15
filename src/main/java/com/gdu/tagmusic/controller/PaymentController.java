package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.PaymentService;

@Controller
public class PaymentController {
	
		
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment/buy")
	public String payMain() {
		return "payment/buy";
	}
	
	@ResponseBody
	@PostMapping(value="/pass/list", produces="application/json; charset=UTF-8")
	public Map<String, Object> passList(){
		return paymentService.getPassList();
	}
	
	@ResponseBody
	@PostMapping(value="/payment/thirtyDay", produces="application/json; charset=UTF-8")
	public Map<String, Object> paymentResult(HttpServletRequest request){
		return paymentService.buyPass(request); 
	}
}


