package com.gdu.tagmusic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.tagmusic.service.PaymentService;

@Controller
public class AlarmController {
	
		
	@Autowired
	private PaymentService paymentService;
	
	@ResponseBody
	@PostMapping(value="/alarm/list", produces="application/json; charset=UTF-8")
	public Map<String, Object> paymentResult(HttpServletRequest request){
		return paymentService.alarmList(request);
	}
	
	@ResponseBody
	@PostMapping(value="/alarm/remove", produces="application/json; charset=UTF-8")
	public Map<String, Object> alarmRemove(HttpServletRequest request){
		return paymentService.alarmRemove(request);
	}
}


