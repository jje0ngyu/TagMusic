package com.gdu.tagmusic.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.tagmusic.service.PaymentService;

@Controller
public class PaymentController {
	
		
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment/buy")
	public String payMain() {
		return "payment/buy";
	}
}


