package com.gdu.tagmusic.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.PaymentService;

@Controller
public class PaymentController {
	
		
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/payment/membership")
	public String payMain() {
		return "payment/membership";
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
	@ResponseBody
	@PostMapping(value="/payment/remainingDay",produces="application/json; charset=UTF-8")
	public Map<String, Object> remainingDay(HttpServletRequest request){
		return paymentService.getRemainindperiod(request);
	}
	
	@ResponseBody
	@PostMapping(value="/payment/recipient",produces="application/json; charset=UTF-8")
	public Map<String, Object> recipientInfo(HttpServletRequest request){
		return paymentService.selectRecipientByEmail(request);
	}
	
	@ResponseBody
	@PostMapping(value="/payment/present/thirtyDay",produces="application/json; charset=UTF-8")
	public Map<String, Object> paymentGiftResult(HttpServletRequest request){
		return paymentService.presentPass(request);
	}
	
	
	@PostMapping("/payment/history")
	public String payLog() {
		return "payment/history";
	}
	
	@ResponseBody
	@PostMapping(value="/payment/buyLogList",produces="application/json; charset=UTF-8")
	public Map<String, Object> paymentBuyLogList(HttpServletRequest request){
		return paymentService.getLogList(request);
	}
	
	@ResponseBody
	@PostMapping(value="/payment/logRemove",produces="application/json; charset=UTF-8")
	public Map<String, Object> removeLog(@RequestParam(value="payLogNo[]") List<String> payLogNo){
		return paymentService.removeLog(payLogNo);
	}
	
	@ResponseBody
	@PostMapping(value="/payment/isPassHave",produces="application/json; charset=UTF-8")
	public Map<String, Object> passHave(HttpServletRequest request){
		return paymentService.isHavePass(request);
	}
	
	@ResponseBody
	@PostMapping(value="/payment/coupon",produces="application/json; charset=UTF-8")
	public Map<String, Object> couponUse(HttpServletRequest request){
		return paymentService.couponUse(request);
	}
}


